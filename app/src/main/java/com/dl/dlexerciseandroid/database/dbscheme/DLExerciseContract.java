package com.dl.dlexerciseandroid.database.dbscheme;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by logicmelody on 2016/4/14.
 */
public class DLExerciseContract {

    public static final String AUTHORITY = "com.dl.dlexerciseandroid.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static final class Task implements BaseColumns {

        public static final String TABLE_NAME = "task";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE_NAME);

        // DB columns
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String TIME = "time";
    }
}