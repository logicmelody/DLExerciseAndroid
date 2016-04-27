package com.dl.dlexerciseandroid.doitlater.share;

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


    public abstract void retrieveIntent(Intent intent);

    public DoItLaterTask(Intent intent) {
        retrieveIntent(intent);
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
