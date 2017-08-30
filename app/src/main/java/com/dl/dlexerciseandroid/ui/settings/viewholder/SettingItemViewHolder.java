package com.dl.dlexerciseandroid.ui.settings.viewholder;

import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.settings.SettingData;

/**
 * Created by logicmelody on 2017/8/30.
 */

public class SettingItemViewHolder extends BasedSettingItemViewHolder {

    private TextView mTitleTextView;
    private TextView mSubtitleTextView;

    private Switch mSwitch;


    public SettingItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void findViews(View itemView) {
        mTitleTextView = (TextView) itemView.findViewById(R.id.text_view_settings_item_title);
        mSubtitleTextView = (TextView) itemView.findViewById(R.id.text_view_settings_item_subtitle);
        mSwitch = (Switch) itemView.findViewById(R.id.switch_settings_item);
    }

    @Override
    public void onBind(SettingData settingData) {
        mTitleTextView.setText(settingData.getTitle());
        mSubtitleTextView.setText(settingData.getSubtitle());
        mSwitch.setVisibility(settingData.hasToggle() ? View.VISIBLE : View.GONE);
    }
}
