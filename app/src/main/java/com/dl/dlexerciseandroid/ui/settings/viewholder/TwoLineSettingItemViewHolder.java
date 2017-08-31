package com.dl.dlexerciseandroid.ui.settings.viewholder;

import android.view.View;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.settings.BasedSettingModel;
import com.dl.dlexerciseandroid.datastructure.settings.TwoLineSettingModel;

/**
 * Created by logicmelody on 2017/8/31.
 */

public class TwoLineSettingItemViewHolder extends BasedSettingItemViewHolder {

    private TextView mSubtitleTextView;


    public TwoLineSettingItemViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void findViews(View itemView) {
        mSubtitleTextView = (TextView) itemView.findViewById(R.id.text_view_setting_item_subtitle);
    }

    @Override
    public void onBind(BasedSettingModel settingModel) {
        TwoLineSettingModel twoLineSettingModel = (TwoLineSettingModel) settingModel;

        mRootView.setOnClickListener(twoLineSettingModel.getOnClickListener());
        mTitleTextView.setText(twoLineSettingModel.getTitle());
        mSubtitleTextView.setText(twoLineSettingModel.getSubtitle());
    }
}
