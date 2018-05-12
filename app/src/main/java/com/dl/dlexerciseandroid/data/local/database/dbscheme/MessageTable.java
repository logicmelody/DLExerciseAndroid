package com.dl.dlexerciseandroid.data.local.database.dbscheme;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by logicmelody on 2016/4/14.
 */
public class MessageTable {

    // 每次更新db的時候，不只要實作onUpgrade()，這裡也必須要記得更新
    private static final String DB_CREATE = "CREATE TABLE "
            + DLExerciseContract.Message.TABLE_NAME
            + "("
            + DLExerciseContract.Message._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " // 由BaseColumns自動產生出來的ID欄位
            + DLExerciseContract.Message.OWNER + " INTEGER NOT NULL, "
            + DLExerciseContract.Message.TEXT + " TEXT, "
            + DLExerciseContract.Message.VIEW_TYPE + " INTEGER NOT NULL, "
            + DLExerciseContract.Message.TIME + " INTEGER NOT NULL"
            + ");";


    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DB_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}
