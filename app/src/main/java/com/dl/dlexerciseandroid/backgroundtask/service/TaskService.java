package com.dl.dlexerciseandroid.backgroundtask.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.features.doitlater.handleintent.DoItLaterTask;
import com.dl.dlexerciseandroid.features.doitlater.handleintent.InHouseDoItLaterTask;
import com.dl.dlexerciseandroid.features.doitlater.handleintent.OtherDoItLaterTask;
import com.dl.dlexerciseandroid.utils.DbUtils;
import com.dl.dlexerciseandroid.utils.DoItLaterUtils;
import com.dl.dlexerciseandroid.utils.Utils;

/**
 * Created by logicmelody on 2016/4/22.
 */
public class TaskService extends IntentService {

    private static final String TAG = TaskService.class.getName();

    private static final class Action {
        public static final String SAVE_NORMAL_TASK = "com.dl.dlexerciseandroid.SAVE_NORMAL_TASK";
        public static final String SAVE_DO_IT_LATER_TASK = "com.dl.dlexerciseandroid.SAVE_DO_IT_LATER_TASK";
    }

    private static final class ExtraKey {
        public static final String STRING_NORMAL_TASK_TITLE = "com.dl.dlexerciseandroid.EXTRA_STRING_NORMAL_TASK_TITLE";
        public static final String STRING_NORMAL_TASK_DESCRIPTION = "com.dl.dlexerciseandroid.EXTRA_STRING_NORMAL_TASK_DESCRIPTION";
        public static final String INTENT_FROM_OTHER_APP = "com.dl.dlexerciseandroid.EXTRA_INTENT_FROM_OTHER_APP";
    }

    private Handler mHandler;


    public TaskService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
    }

    public static Intent generateSaveNormalTaskIntent(Context context, String title, String description) {
        Intent intent = new Intent(context, TaskService.class);
        intent.setAction(Action.SAVE_NORMAL_TASK);
        intent.putExtra(ExtraKey.STRING_NORMAL_TASK_TITLE, title);
        intent.putExtra(ExtraKey.STRING_NORMAL_TASK_DESCRIPTION, description);

        return intent;
    }

    public static Intent generateSaveDoItLaterTaskIntent(Context context, Intent intentFromOtherApp) {
        Intent intent = new Intent(context, TaskService.class);
        intent.setAction(Action.SAVE_DO_IT_LATER_TASK);
        intent.putExtra(ExtraKey.INTENT_FROM_OTHER_APP, intentFromOtherApp);

        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

        if (Action.SAVE_NORMAL_TASK.equals(action)) {
            saveNormalTask(intent);

        } else if (Action.SAVE_DO_IT_LATER_TASK.equals(action)) {
            saveDoItLaterTask(intent);
        }
    }

    private void saveNormalTask(Intent intent) {
        String title = intent.getStringExtra(ExtraKey.STRING_NORMAL_TASK_TITLE);
        String description = intent.getStringExtra(ExtraKey.STRING_NORMAL_TASK_DESCRIPTION);

        if (TextUtils.isEmpty(title)) {
            return;
        }

        DbUtils.insertTask(this, title, description, System.currentTimeMillis());
        Utils.showToastInNonUIThread(mHandler, this, getString(R.string.do_it_later_save_task_completed));
    }

    private void saveDoItLaterTask(Intent intent) {
        Intent intentFromOtherApp = intent.getParcelableExtra(ExtraKey.INTENT_FROM_OTHER_APP);
        String from = intentFromOtherApp.getAction();
        DoItLaterTask doItLaterTask = null;

        if (DoItLaterUtils.ACTION_DO_IT_LATER.equals(from)) {
            doItLaterTask = new InHouseDoItLaterTask(this, intentFromOtherApp);

        } else if (Intent.ACTION_SEND.equals(from)) {
            doItLaterTask = new OtherDoItLaterTask(this, intentFromOtherApp);
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
