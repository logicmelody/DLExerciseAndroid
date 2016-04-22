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

        }

        ContentValues values = new ContentValues();
        values.put(DLExerciseContract.Task.TITLE, title);
        values.put(DLExerciseContract.Task.DESCRIPTION, description);
        values.put(DLExerciseContract.Task.TIME, time);

        context.getContentResolver().insert(DLExerciseContract.Task.CONTENT_URI, values);
    }
}
