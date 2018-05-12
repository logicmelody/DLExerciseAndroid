package com.dl.dlexerciseandroid.features.strategypattern.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by logicmelody on 2017/5/8.
 */

public class ActivityFinishedReceiver extends BroadcastReceiver {

    public static final String ACTION_ACTIVITY_FINISHED = "com.dl.dlexerciseandroid.ACTION_ACTIVITY_FINISHED";

    public interface OnActivityFinishedListener {
        void onActivityFinished();
    }

    private OnActivityFinishedListener mOnActivityFinishedListener;


    public ActivityFinishedReceiver(OnActivityFinishedListener listener) {
        mOnActivityFinishedListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (ACTION_ACTIVITY_FINISHED.equals(action)) {
            mOnActivityFinishedListener.onActivityFinished();
        }
    }
}
