package com.dl.dlexerciseandroid.database.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dl.dlexerciseandroid.database.dbscheme.TaskTable;

import java.util.ArrayList;

/**
 * Created by logicmelody on 2016/4/14.
 */
public class DLExerciseDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dlexercise.db";

    // 設定現在的DB version，當user已經安裝了app之後，就只能利用update db的方式來修改或是更新db的欄位
    // user安裝了更新版的app之後，如果DB version比舊的高，就會呼叫onUpgrade()
    // Version必須要 >= 1，所以初始只能從1開始
    private static final int DB_VERSION = DbVersion.VERSION_2;

    public static final class DbVersion {
        public static final int VERSION_1 = 1;
        public static final int VERSION_2 = 2;
    }

    private volatile static DLExerciseDatabaseHelper sDLExerciseDatabaseHelper;


    public static DLExerciseDatabaseHelper getInstance(Context context) {
        if (sDLExerciseDatabaseHelper == null) {
            synchronized (DLExerciseDatabaseHelper.class) {
                if (sDLExerciseDatabaseHelper == null) {
                    sDLExerciseDatabaseHelper = new DLExerciseDatabaseHelper(context);
                }
            }
        }

        return sDLExerciseDatabaseHelper;
    }

    private DLExerciseDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TaskTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion <= oldVersion) {
            return;
        }

        int version = oldVersion;

        if (version < DbVersion.VERSION_2) {
            TaskTable.onUpgrade(db, version, DB_VERSION);
            version = DbVersion.VERSION_2;
        }
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }
}
