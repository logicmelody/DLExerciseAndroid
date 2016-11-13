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
        public static final String LATER_PACKAGE_NAME = "later_package_name";
        public static final String LATER_CALL_BACK = "later_call_back";
    }

    public static final class Message implements BaseColumns {

        public static final String TABLE_NAME = "message";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, TABLE_NAME);

        // DB columns
        public static final String OWNER = "owner";
        public static final String TEXT = "text";
        public static final String VIEW_TYPE = "view_type";
        public static final String TIME = "time";
    }
}
