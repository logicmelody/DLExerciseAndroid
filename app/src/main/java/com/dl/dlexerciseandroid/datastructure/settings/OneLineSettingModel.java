package com.dl.dlexerciseandroid.datastructure.settings;

import android.view.View;

import com.dl.dlexerciseandroid.ui.settings.SettingManager;

/**
 * Created by logicmelody on 2017/8/30.
 */

public class OneLineSettingModel extends BasedSettingModel {

    public OneLineSettingModel(String title, View.OnClickListener onClickListener) {
        super(title, onClickListener);
    }

    @Override
    public int getViewType() {
        return SettingManager.ItemViewType.ONE_LINE;
    }
}
