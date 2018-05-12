package com.dl.dlexerciseandroid.features.ndk;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.data.model.NdkData;
import com.dl.dlexerciseandroid.utils.NdkUtils;

/**
 * Created by logicmelody on 2017/7/1.
 */

public class NdkFragment extends Fragment {

    public static final String TAG = NdkFragment.class.getName();

    private Context mContext;

    private TextView mNdkDataText;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ndk, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupNdkDataText();
    }

    private void findViews() {
        mNdkDataText = (TextView) getView().findViewById(R.id.text_view_ndk_ndk_data);
    }

    private void setupNdkDataText() {
        NdkData ndkData = new NdkData(Integer.MAX_VALUE, "This is NdkData");

        // 這邊call native library，如果把proguard打開，會發生找不到method的問題
        mNdkDataText.setText(String.valueOf(NdkUtils.getNdkDataIdFromNative(ndkData)));
    }
}
