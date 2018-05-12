package com.dl.dlexerciseandroid.features.settings.viewholder;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.data.model.settings.BasedSettingModel;
import com.dl.dlexerciseandroid.data.model.settings.TwoLineWithSwitchSettingModel;

/**
 * Created by logicmelody on 2017/8/31.
 */

public class TwoLineWithSwitchSettingItemViewHolder extends BasedSettingItemViewHolder {

    private TextView mSubtitleTextView;
    private Switch mSwitch;


    public TwoLineWithSwitchSettingItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void findViews(View itemView) {
        mSubtitleTextView = (TextView) itemView.findViewById(R.id.text_view_setting_item_subtitle);
        mSwitch = (Switch) itemView.findViewById(R.id.switch_setting_item);
    }

    @Override
    public void onBind(BasedSettingModel settingModel) {
        TwoLineWithSwitchSettingModel twoLineWithSwitchSettingModel = (TwoLineWithSwitchSettingModel) settingModel;

        mRootView.setOnClickListener(twoLineWithSwitchSettingModel.getOnClickListener());
        mTitleTextView.setText(twoLineWithSwitchSettingModel.getTitle());
        mSubtitleTextView.setText(twoLineWithSwitchSettingModel.getSubtitle());
        mSwitch.setChecked(twoLineWithSwitchSettingModel.getIsChecked());
    }
}
