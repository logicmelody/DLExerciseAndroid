package com.dl.dlexerciseandroid.background.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.doitlater.share.DoItLaterTask;
import com.dl.dlexerciseandroid.doitlater.share.InHouseDoItLaterTask;
import com.dl.dlexerciseandroid.utility.utils.DbUtils;
import com.dl.dlexerciseandroid.utility.utils.DoItLaterUtils;
import com.dl.dlexerciseandroid.utility.utils.Utils;

/**
 * Created by logicmelody on 2016/4/22.
 */
public class DoItLaterService extends IntentService {

    private static final String TAG = "com.dl.dlexerciseandroid.DoItLaterService";

    private static final class Action {
        public static final String SAVE_DO_IT_LATER_TASK = "com.dl.dlexerciseandroid.SAVE_DO_IT_LATER_TASK";
    }

    private static final class ExtraKey {
        public static final String INTENT_FROM_OTHER_APP = "com.dl.dlexerciseandroid.EXTRA_INTENT_FROM_OTHER_APP";
    }

    private Handler mHandler;


    public DoItLaterService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
    }

    public static Intent generateSaveDoItLaterTaskIntent(Context context, Intent intentFromOtherApp) {
        Intent intent = new Intent(context, DoItLaterService.class);
        intent.setAction(Action.SAVE_DO_IT_LATER_TASK);
        intent.putExtra(ExtraKey.INTENT_FROM_OTHER_APP, intentFromOtherApp);

        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

        if (Action.SAVE_DO_IT_LATER_TASK.equals(action)) {
            saveDoItLaterTask(intent);
        }
    }

    private void saveDoItLaterTask(Intent intent) {
        Intent intentFromOtherApp = intent.getParcelableExtra(ExtraKey.INTENT_FROM_OTHER_APP);
        String from = intentFromOtherApp.getAction();
        DoItLaterTask doItLaterTask = null;

        if (DoItLaterUtils.ACTION_DO_IT_LATER.equals(from)) {
            doItLaterTask = new InHouseDoItLaterTask(intentFromOtherApp);

        } else if (Intent.ACTION_SEND.equals(from)) {

        }

        if (doItLaterTask != null) {
            DbUtils.insertDoItLaterTask(this, doItLaterTask.getTitle(), doItLaterTask.getDescription(),
                                        doItLaterTask.getTime(), doItLaterTask.getLaterPackageName(),
                                        doItLaterTask.getLaterCallback());
            Utils.showToastInNonUIThread(mHandler, this, getString(R.string.do_it_later_save_task_completed));

        } else {
            Utils.showToastInNonUIThread(mHandler, this, getString(R.string.do_it_later_save_task_failed));
        }
    }
}
