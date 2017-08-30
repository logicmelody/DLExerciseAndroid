package com.dl.dlexerciseandroid.datastructure.settings;

/**
 * Created by logicmelody on 2017/8/30.
 */

public class SettingData {

    private String mTitle;
    private String mSubtitle;

    private boolean mHasToggle = false;


    public SettingData(String title, String subtitle, boolean hasToggle) {
        mTitle = title;
        mSubtitle = subtitle;
        mHasToggle = hasToggle;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        mSubtitle = subtitle;
    }

    public boolean hasToggle() {
        return mHasToggle;
    }

    public void setHasToggle(boolean hasToggle) {
        mHasToggle = hasToggle;
    }
}
