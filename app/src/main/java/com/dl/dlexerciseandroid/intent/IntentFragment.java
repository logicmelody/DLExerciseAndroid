package com.dl.dlexerciseandroid.intent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2016/4/26.
 */
public class IntentFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "com.dl.dlexerciseandroid.IntentFragment";

    private Context mContext;

    private Button mFireComponentIntentButton;
    private Button mFireActionIntentButton;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intent, container, false);
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
        mFireComponentIntentButton = (Button) getView().findViewById(R.id.button_intent_fire_component_intent);
        mFireActionIntentButton = (Button) getView().findViewById(R.id.button_intent_fire_action_intent);
    }

    private void setupViews() {
        mFireComponentIntentButton.setOnClickListener(this);
        mFireActionIntentButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_intent_fire_action_intent:
                fireActionIntent();
                break;

            case R.id.button_intent_fire_component_intent:
                fireComponentIntent();
                break;
        }
    }

    private void fireActionIntent() {
        // 只有設定action，讓系統尋找最適合的Activity開啟
        Intent intent = new Intent(ReceiveIntentActivity.ACTION_RECEIVE_INTENT);
        startActivity(intent);
    }

    private void fireComponentIntent() {
        Intent intent = new Intent();

        // Intent除了可以設定action, category, data交由系統自己決定要用哪個Activity開啟之外，
        // 也可以經由setClassName()這種設定package name跟class name的方式，明確指定此intent要由哪一個component開啟
        intent.setClassName("com.dl.dlexerciseandroid", "com.dl.dlexerciseandroid.intent.ReceiveIntentActivity");
        intent.setAction(ReceiveIntentActivity.ACTION_RECEIVE_INTENT);

        startActivity(intent);
    }
}
