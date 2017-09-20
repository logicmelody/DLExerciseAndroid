package com.dl.dlexerciseandroid.ui.guide;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utility.utils.PreferenceUtils;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by logicmelody on 2016/6/5.
 */
public class GuideFragment extends Fragment {

    public static final String TAG = GuideFragment.class.getName();

    private Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
    }

    private void findViews() {

    }
}
