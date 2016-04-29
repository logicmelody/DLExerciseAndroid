package com.dl.dlexerciseandroid.doitlater.share;

import android.content.Context;
import android.content.Intent;

/**
 * Created by logicmelody on 2016/4/27.
 */
public abstract class DoItLaterTask {

    protected String mTitle;
    protected String mDescription;
    protected String mLaterPackageName;
    protected String mLaterCallback;
    protected long mTime;


    public abstract void retrieveIntent(Context context, Intent intent);

    public DoItLaterTask(Context context, Intent intent) {
        retrieveIntent(context, intent);
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getLaterPackageName() {
        return mLaterPackageName;
    }

    public String getLaterCallback() {
        return mLaterCallback;
    }

    public long getTime() {
        return mTime;
    }
}
