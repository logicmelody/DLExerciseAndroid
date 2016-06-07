package com.dl.dlexerciseandroid.firebase;

import android.content.Context;
import android.content.SharedPreferences;
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
public class FirebaseFragment extends Fragment {

    public static final String TAG = FirebaseFragment.class.getName();

    private Context mContext;

    private Switch mTestTopicSwitch;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_firebase, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupTestTopicSwitch();
    }

    private void findViews() {
        mTestTopicSwitch = (Switch) getView().findViewById(R.id.switch_firebase_subscribe_topic_test);
    }

    private void setupTestTopicSwitch() {
        // PreferenceManager.getDefaultSharedPreferences(mContext)可以拿到此app中預設的shared preference檔案，
        // 一般其實用這種作法即可
        mTestTopicSwitch.setChecked(PreferenceManager.getDefaultSharedPreferences(mContext).
                getBoolean(PreferenceUtils.PREFERENCE_SUBSCRIBE_TEST_TOPIC, false));

        mTestTopicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseMessaging.getInstance().subscribeToTopic("test");
                    Log.d("danny", "Firebase subscribe test");

                } else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("test");
                    Log.d("danny", "Firebase unsubscribe test");
                }

                PreferenceManager.getDefaultSharedPreferences(mContext).edit().
                        putBoolean(PreferenceUtils.PREFERENCE_SUBSCRIBE_TEST_TOPIC, isChecked).apply();
            }
        });
    }
}
