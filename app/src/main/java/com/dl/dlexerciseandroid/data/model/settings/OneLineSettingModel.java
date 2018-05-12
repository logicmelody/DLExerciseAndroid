package com.dl.dlexerciseandroid.data.model.settings;

import android.view.View;

import com.dl.dlexerciseandroid.features.settings.SettingManager;

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
