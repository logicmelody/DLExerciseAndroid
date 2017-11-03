package com.dl.dlexerciseandroid.model.settings;

import android.view.View.OnClickListener;

/**
 * Created by logicmelody on 2017/8/30.
 */

abstract public class BasedSettingModel {

    abstract public int getViewType();

    protected String mTitle;
    protected OnClickListener mOnClickListener;


    public BasedSettingModel(String title, OnClickListener onClickListener) {
        mTitle = title;
        mOnClickListener = onClickListener;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }
}
