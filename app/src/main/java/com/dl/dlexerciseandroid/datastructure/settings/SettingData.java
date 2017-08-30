package com.dl.dlexerciseandroid.datastructure.settings;

import android.view.View.OnClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * Created by logicmelody on 2017/8/30.
 */

public class SettingData {

    private String mTitle;
    private String mSubtitle;

    private boolean mHasToggle = false;

    private OnClickListener mOnClickListener;
    private OnCheckedChangeListener mOnCheckedChangeListener;


    public SettingData(String title, String subtitle, boolean hasToggle, OnClickListener onClickListener) {
        mTitle = title;
        mSubtitle = subtitle;
        mHasToggle = hasToggle;
        mOnClickListener = onClickListener;
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

    public void setOnClickListener(OnClickListener listener) {
        mOnClickListener = listener;
    }

    public OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    public OnCheckedChangeListener getOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }
}
