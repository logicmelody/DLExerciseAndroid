package com.dl.dlexerciseandroid.database.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dl.dlexerciseandroid.database.dbscheme.DLExerciseContract;

/**
 * Created by logicmelody on 2016/4/14.
 */
public class DLExerciseContentProvider extends ContentProvider {

    private static final class UriMatcherIndex {
        public static final int TASK = 0;
    }

    private static final UriMatcher sUriMatcher;
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        sUriMatcher.addURI(DLExerciseContract.AUTHORITY, DLExerciseContract.Task.TABLE_NAME, UriMatcherIndex.TASK);
    }

    private DLExerciseDatabaseHelper mDLExerciseDatabaseHelper;


    @Override
    public boolean onCreate() {
        // 如果app開啟的時候，db跟provider還沒有建立，會在MainActivity的onCreate()之前先讀取這裡
        // db跟provider的建立或更新會在app開啟之前先完成
        Log.d("danny", "DLExerciseContentProvider onCreate");

        mDLExerciseDatabaseHelper = DLExerciseDatabaseHelper.getInstance(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDLExerciseDatabaseHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (sUriMatcher.match(uri)) {
            case UriMatcherIndex.TASK:
                queryBuilder.setTables(DLExerciseContract.Task.TABLE_NAME);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        // 必須要加這一行才可以跟LoaderManager連動
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDLExerciseDatabaseHelper.getWritableDatabase();
        long id = 0;

        switch (sUriMatcher.match(uri)) {
            case UriMatcherIndex.TASK:
                id = db.insert(DLExerciseContract.Task.TABLE_NAME, null, values);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(DLExerciseContract.Task.CONTENT_URI, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDLExerciseDatabaseHelper.getWritableDatabase();
        int rowsDeleted = 0;

        switch (sUriMatcher.match(uri)) {
            case UriMatcherIndex.TASK:
                rowsDeleted = db.delete(DLExerciseContract.Task.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDLExerciseDatabaseHelper.getWritableDatabase();
        int rowsUpdated = 0;

        switch (sUriMatcher.match(uri)) {
            case UriMatcherIndex.TASK:
                rowsUpdated = db.update(DLExerciseContract.Task.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }
}
