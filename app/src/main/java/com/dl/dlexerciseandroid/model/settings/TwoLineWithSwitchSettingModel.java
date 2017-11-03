package com.dl.dlexerciseandroid.model.settings;

import android.view.View;

import com.dl.dlexerciseandroid.ui.settings.SettingManager;

/**
 * Created by logicmelody on 2017/8/30.
 */

public class TwoLineWithSwitchSettingModel extends BasedSettingModel {

    private String mSubtitle;
    private boolean mIsChecked;


    public TwoLineWithSwitchSettingModel(String title, String subtitle, boolean isChecked,
                                         View.OnClickListener onClickListener) {
        super(title, onClickListener);
        mSubtitle = subtitle;
        mIsChecked = isChecked;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        mSubtitle = subtitle;
    }

    public boolean getIsChecked() {
        return mIsChecked;
    }

    public void setIsChecked(boolean isChecked) {
        mIsChecked = isChecked;
    }

    @Override
    public int getViewType() {
        return SettingManager.ItemViewType.TWO_LINE_WITH_SWITCH;
    }
}
