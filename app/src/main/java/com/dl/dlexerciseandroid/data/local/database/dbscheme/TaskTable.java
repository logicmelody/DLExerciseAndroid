package com.dl.dlexerciseandroid.data.local.database.dbscheme;

import android.database.sqlite.SQLiteDatabase;

import com.dl.dlexerciseandroid.data.local.database.provider.DLExerciseDatabaseHelper;

/**
 * Created by logicmelody on 2016/4/14.
 */
public class TaskTable {

    // 每次更新db的時候，不只要實作onUpgrade()，這裡也必須要記得更新
    private static final String DB_CREATE = "CREATE TABLE "
            + DLExerciseContract.Task.TABLE_NAME
            + "("
            + DLExerciseContract.Task._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " // 由BaseColumns自動產生出來的ID欄位
            + DLExerciseContract.Task.TITLE + " TEXT NOT NULL, "
            + DLExerciseContract.Task.DESCRIPTION + " TEXT, "
            + DLExerciseContract.Task.TIME + " INTEGER NOT NULL, "
            + DLExerciseContract.Task.LATER_PACKAGE_NAME + " TEXT, "
            + DLExerciseContract.Task.LATER_CALL_BACK + " TEXT"
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
        if (oldVersion < DLExerciseDatabaseHelper.DbVersion.VERSION_2) {
            String updateDb = "ALTER TABLE " + DLExerciseContract.Task.TABLE_NAME +
                    " ADD COLUMN " + DLExerciseContract.Task.LATER_PACKAGE_NAME + " TEXT;";

            String updateDb2 = "ALTER TABLE " + DLExerciseContract.Task.TABLE_NAME +
                    " ADD COLUMN " + DLExerciseContract.Task.LATER_CALL_BACK + " TEXT;";

            database.execSQL(updateDb);
            database.execSQL(updateDb2);
        }
    }
}
