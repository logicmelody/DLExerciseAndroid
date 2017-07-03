package com.dl.dlexerciseandroid.ui.bubbletext;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by logicmelody on 2017/7/3.
 */

public class SaveMessageService extends IntentService {

    private static final String TAG = SaveMessageService.class.getName();

    public static final class Action {
        public static final String ACTION_SAVE_MESSAGE = "com.dl.dlexerciseandroid.ui.bubbletext.ACTION_SAVE_MESSAGE";
    }

    public static final class Extra {
        public static final String EXTRA_MESSAGE = "com.dl.dlexerciseandroid.ui.bubbletext.EXTRA_MESSAGE";
    }


    public static Intent generateSaveMessageIntent(Context context, String message) {
        Intent intent = new Intent(context, SaveMessageService.class);
        intent.setAction(Action.ACTION_SAVE_MESSAGE);
        intent.putExtra(Extra.EXTRA_MESSAGE, message);

        return intent;
    }

    public SaveMessageService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();

        if (Action.ACTION_SAVE_MESSAGE.equals(action)) {
            saveMessage(intent);
        }
    }

    private void saveMessage(Intent intent) {
        String message = intent.getStringExtra(Extra.EXTRA_MESSAGE);

        if (TextUtils.isEmpty(message)) {
            return;
        }

        startService(BubbleTextService.generateUpdateBubbleTextViewMessageIntent(this, message));
    }
}
