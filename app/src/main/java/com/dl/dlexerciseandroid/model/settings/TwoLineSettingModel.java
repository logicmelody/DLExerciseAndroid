package com.dl.dlexerciseandroid.model.settings;

import android.view.View;

import com.dl.dlexerciseandroid.ui.settings.SettingManager;

/**
 * Created by logicmelody on 2017/8/30.
 */

public class TwoLineSettingModel extends BasedSettingModel {

    private String mSubtitle;


    public TwoLineSettingModel(String title, String subtitle, View.OnClickListener onClickListener) {
        super(title, onClickListener);
        mSubtitle = subtitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        mSubtitle = subtitle;
    }

    @Override
    public int getViewType() {
        return SettingManager.ItemViewType.TWO_LINE;
    }
}
