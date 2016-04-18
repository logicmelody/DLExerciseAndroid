package com.dl.dlexerciseandroid.database.dbscheme;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by logicmelody on 2016/4/14.
 */
public class TaskTable {

    private static final String DB_CREATE = "CREATE TABLE "
            + DLExerciseContract.Task.TABLE_NAME
            + "("
            + DLExerciseContract.Task._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " // 由BaseColumns自動產生出來的ID欄位
            + DLExerciseContract.Task.TITLE + " TEXT NOT NULL, "
            + DLExerciseContract.Task.DESCRIPTION + " TEXT, "
            + DLExerciseContract.Task.TIME + " INTEGER NOT NULL"
            + ");";


    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DB_CREATE);

        // 如果要在code中預先在db中塞入些資料，可以用以下方式：
        //ContentValues values = new ContentValues();
        //values.put(DLExerciseContract.Task.TITLE, "build in data title");
        //values.put(DLExerciseContract.Task.DESCRIPTION, "build in data description");
        //values.put(DLExerciseContract.Task.TIME, System.currentTimeMillis());
        //database.insert(DLExerciseContract.Task.TABLE_NAME, null, values);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}
