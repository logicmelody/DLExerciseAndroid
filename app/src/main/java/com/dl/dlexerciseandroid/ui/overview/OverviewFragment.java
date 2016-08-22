package com.dl.dlexerciseandroid.ui.overview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utility.gtm.ContainerHolderSingleton;
import com.dl.dlexerciseandroid.utility.utils.GtmUtils;
import com.dl.dlexerciseandroid.utility.utils.NdkUtils;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by logicmelody on 2016/3/30.
 */
public class OverviewFragment extends Fragment {

    // 以後Fragment的tag name都用此class的name來命名比較方便
    // e.g. MusicPlayerFragment
    public static final String TAG = OverviewFragment.class.getName();

    private Context mContext;

    private TagManager mTagManager;

    private TextView mGtmText;
    private TextView mNdkText;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        mTagManager = TagManager.getInstance(mContext);
        mTagManager.setVerboseLoggingEnabled(true);

        findViews();
        setupNdkText();
        loadTextFromGtm();
    }

    private void findViews() {
        mGtmText = (TextView) getView().findViewById(R.id.text_view_overview_gtm_text);
        mNdkText = (TextView) getView().findViewById(R.id.text_view_overview_ndk_string);
    }

    private void setupNdkText() {
        mNdkText.setText(NdkUtils.getMsgFromJni());
    }

    private void loadTextFromGtm() {
        if (ContainerHolderSingleton.getContainerHolder() != null) {
            // Container每12小時才會更新一次，所以我們這邊手動更新，但是這個method因為不是同步的，所以不會馬上return
            // 而且因為網路流量的考量，這個method呼叫過一次之後，要等15分鐘才可以在呼叫
            ContainerHolderSingleton.getContainerHolder().refresh();
        }

        // 第一個參數是在GTM interface設定的container id
        // 第二個參數是我們下載下來default的file
        PendingResult<ContainerHolder> pending =
                mTagManager.loadContainerPreferNonDefault(GtmUtils.CONTAINER_ID, R.raw.gtm_dlexercise);

        // The onResult method will be called as soon as one of the following happens:
        //     1. A saved container is loaded(只要有從網路上load過container，就會存在在app中)
        //     2. If there is no saved container, a network container is loaded
        //     3. The 2-second timeout occurs
        pending.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            public void onResult(ContainerHolder containerHolder) {
                ContainerHolderSingleton.setContainerHolder(containerHolder);
                Container container = ContainerHolderSingleton.getContainerHolder().getContainer();

                if (!containerHolder.getStatus().isSuccess()) {
                    Log.e("danny", "Failure loading container");
                    return;
                }

                mGtmText.setText(container.getString(GtmUtils.Key.OVERVIEW_TEXT));
                mGtmText.setTextColor(Color.parseColor(container.getString(GtmUtils.Key.OVERVIEW_TEXT_COLOR)));
            }
        }, 2000, TimeUnit.MILLISECONDS);
    }
}