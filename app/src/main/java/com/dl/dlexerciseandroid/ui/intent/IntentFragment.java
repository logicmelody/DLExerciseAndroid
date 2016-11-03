package com.dl.dlexerciseandroid.ui.intent;

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

/**
 * Created by logicmelody on 2016/4/26.
 */
public class IntentFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = IntentFragment.class.getName();

    private Context mContext;

    private Button mFireComponentIntentButton;
    private Button mFireActionIntentButton;
    private Button mFireTextShareIntentButton;


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
        mFireTextShareIntentButton = (Button) getView().findViewById(R.id.button_intent_fire_text_share_intent);
    }

    private void setupViews() {
        mFireComponentIntentButton.setOnClickListener(this);
        mFireActionIntentButton.setOnClickListener(this);
        mFireTextShareIntentButton.setOnClickListener(this);
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

            case R.id.button_intent_fire_text_share_intent:
                fireTextShareIntent();
                break;
        }
    }

    private void fireActionIntent() {
        // 只有設定action，讓系統尋找最適合的Activity開啟
        Intent intent = new Intent(ReceiveIntentActivity.ACTION_RECEIVE_INTENT);

        // category通常不用設定，系統會指定預設的category：android.intent.category.DEFAULT

        startActivity(intent);
    }

    private void fireComponentIntent() {
        Intent intent = new Intent();

        Log.d("danny", "fireComponentIntent() package name = " + mContext.getPackageName());
        Log.d("danny", "fireComponentIntent() class name = " + ReceiveIntentActivity.class.getName());

        // Intent除了可以設定action, category, data交由系統自己決定要用哪個Activity開啟之外，
        // 也可以經由setClassName()這種設定package name跟class name的方式，明確指定此intent要由哪一個component開啟
        // setClassName("com.dl.dlexerciseandroid", "ReceiveIntentActivity")
        intent.setClassName(mContext.getPackageName(), ReceiveIntentActivity.class.getName());
        intent.setAction(ReceiveIntentActivity.ACTION_RECEIVE_INTENT);
        intent.putExtra("EXTRA_STRING", "This is a string");
        intent.putExtra("EXTRA_INT", 100);

        startActivity(intent);
    }

    private void fireTextShareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);

        // 如果要share一段text給別的app，e.g. Email, Google Keep, Message
        // 要將文字塞到Android定義的EXTRA_TEXT中
        // EXTRA_SUBJECT: 標題
        // EXTRA_TEXT: 文字內容
        intent.putExtra(Intent.EXTRA_SUBJECT, "筆記這個是標題");
        intent.putExtra(Intent.EXTRA_TEXT, "筆記今天是晴天");
        intent.setType("text/plain");

        startActivity(intent);
    }
}
