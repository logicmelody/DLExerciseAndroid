package com.dl.dlexerciseandroid.backgroundtask.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dl.dlexerciseandroid.datastructure.Message;
import com.dl.dlexerciseandroid.utility.utils.DbUtils;

/**
 * Created by logicmelody on 2016/11/13.
 */

public class MessageService extends IntentService {

    private static final String TAG = MessageService.class.getName();

    private static final class Action {
        public static final String SAVE_MESSAGE = "com.dl.dlexerciseandroid.SAVE_MESSAGE";
    }

    private static final class ExtraKey {
        public static final String MESSAGE = "com.dl.dlexerciseandroid.EXTRA_MESSAGE";
    }


    public static Intent generateSaveMessageIntent(Context context, Message message) {
        Intent intent = new Intent(context, MessageService.class);
        intent.setAction(Action.SAVE_MESSAGE);
        intent.putExtra(ExtraKey.MESSAGE, message);

        return intent;
    }

    public MessageService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

        if (Action.SAVE_MESSAGE.equals(action)) {
            saveMessage(intent);
        }
    }

    private void saveMessage(Intent intent) {
        Message message = intent.getParcelableExtra(ExtraKey.MESSAGE);

        DbUtils.insertMessage(this, message);
    }
}
