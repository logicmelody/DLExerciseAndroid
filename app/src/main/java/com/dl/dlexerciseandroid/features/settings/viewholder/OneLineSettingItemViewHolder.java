package com.dl.dlexerciseandroid.features.settings.viewholder;

import android.view.View;

import com.dl.dlexerciseandroid.data.model.settings.BasedSettingModel;
import com.dl.dlexerciseandroid.data.model.settings.OneLineSettingModel;

/**
 * Created by logicmelody on 2017/8/31.
 */

public class OneLineSettingItemViewHolder extends BasedSettingItemViewHolder {

    public OneLineSettingItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void findViews(View itemView) {

    }

    @Override
    public void onBind(BasedSettingModel settingModel) {
        OneLineSettingModel oneLineSettingModel = (OneLineSettingModel) settingModel;

        mRootView.setOnClickListener(oneLineSettingModel.getOnClickListener());
        mTitleTextView.setText(oneLineSettingModel.getTitle());
    }
}
