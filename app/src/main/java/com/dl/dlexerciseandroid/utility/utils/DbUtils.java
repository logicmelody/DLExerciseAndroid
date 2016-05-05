package com.dl.dlexerciseandroid.utility.utils;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import com.dl.dlexerciseandroid.database.dbscheme.DLExerciseContract;

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
    }
}
