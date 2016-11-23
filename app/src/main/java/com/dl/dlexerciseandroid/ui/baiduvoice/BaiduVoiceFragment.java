package com.dl.dlexerciseandroid.ui.baiduvoice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.ui.intent.ReceiveIntentActivity;

/**
 * Created by logicmelody on 2016/4/26.
 */
public class BaiduVoiceFragment extends Fragment {

    public static final String TAG = BaiduVoiceFragment.class.getName();

    private Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_baidu_voice, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
    }

    private void findViews() {

    }

    private void setupViews() {

    }
}
