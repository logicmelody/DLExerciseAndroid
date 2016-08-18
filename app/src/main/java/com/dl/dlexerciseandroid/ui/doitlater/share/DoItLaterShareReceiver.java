package com.dl.dlexerciseandroid.ui.doitlater.share;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by logicmelody on 2016/4/22.
 */
public class DoItLaterShareReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("danny", "DoItLaterShareReceiver get share intent");
    }
}
