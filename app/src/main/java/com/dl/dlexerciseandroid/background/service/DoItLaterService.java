package com.dl.dlexerciseandroid.background.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by logicmelody on 2016/4/22.
 */
public class DoItLaterService extends IntentService {

    private static final String TAG = "com.dl.dlexerciseandroid.DoItLaterService";

    private static final class Action {
        public static final String SAVE_DO_IT_LATER_TASK = "com.dl.dlexerciseandroid.SAVE_DO_IT_LATER_TASK";
    }


    public DoItLaterService() {
        super(TAG);
    }

    public static Intent generateSaveDoItLaterTaskIntent(Context context) {
        Intent intent = new Intent(context, DoItLaterService.class);
        intent.setAction(Action.SAVE_DO_IT_LATER_TASK);

        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

        if (Action.SAVE_DO_IT_LATER_TASK.equals(action)) {
            saveDoItLaterTask();
        }
    }

    private void saveDoItLaterTask() {
        Log.d("danny", "Save Do It Later task");

    }
}
