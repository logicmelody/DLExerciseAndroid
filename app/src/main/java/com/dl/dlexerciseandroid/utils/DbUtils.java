package com.dl.dlexerciseandroid.utils;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.data.local.database.dbscheme.DLExerciseContract;
import com.dl.dlexerciseandroid.data.model.message.Message;

/**
 * Created by logicmelody on 2016/4/22.
 */
public class DbUtils {

    public static void insertTask(Context context, String title, String description, long time) {
        if (TextUtils.isEmpty(title)) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(DLExerciseContract.Task.TITLE, title);
        values.put(DLExerciseContract.Task.DESCRIPTION, description);
        values.put(DLExerciseContract.Task.TIME, time);

        context.getContentResolver().insert(DLExerciseContract.Task.CONTENT_URI, values);
    }

    public static void insertDoItLaterTask(Context context, String title, String description, long time,
                                           String laterPackageName, String laterCallback) {

        ContentValues values = new ContentValues();
        values.put(DLExerciseContract.Task.TITLE, title);
        values.put(DLExerciseContract.Task.DESCRIPTION, description);
        values.put(DLExerciseContract.Task.TIME, time);
        values.put(DLExerciseContract.Task.LATER_PACKAGE_NAME, laterPackageName);
        values.put(DLExerciseContract.Task.LATER_CALL_BACK, laterCallback);

        context.getContentResolver().insert(DLExerciseContract.Task.CONTENT_URI, values);
    }

    public static void deleteTask(Context context, long id) {
        String selection = DLExerciseContract.Task._ID + " = ?";
        String[] selectionArgs = {
                String.valueOf(id)
        };

        context.getContentResolver().delete(DLExerciseContract.Task.CONTENT_URI, selection, selectionArgs);
        Utils.showToastInNonUIThread(new Handler(), context, context.getString(R.string.do_it_later_delete_task));
    }

    public static void insertMessage(Context context, Message message) {
        if (message == null) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(DLExerciseContract.Message.OWNER, message.getOwner());
        values.put(DLExerciseContract.Message.TEXT, message.getMessage());
        values.put(DLExerciseContract.Message.VIEW_TYPE, message.getViewType());
        values.put(DLExerciseContract.Message.TIME, message.getTime());

        context.getContentResolver().insert(DLExerciseContract.Message.CONTENT_URI, values);
    }
}
