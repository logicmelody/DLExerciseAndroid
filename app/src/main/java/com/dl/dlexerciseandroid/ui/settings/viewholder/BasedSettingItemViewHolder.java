package com.dl.dlexerciseandroid.ui.settings.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.settings.BasedSettingModel;

/**
 * Created by logicmelody on 2017/8/30.
 */

abstract public class BasedSettingItemViewHolder extends RecyclerView.ViewHolder {

    abstract protected void findViews(View itemView);
    abstract public void onBind(BasedSettingModel settingModel);

    protected View mRootView;
    protected TextView mTitleTextView;


    public BasedSettingItemViewHolder(View itemView) {
        super(itemView);
        mRootView = itemView;
        mTitleTextView = (TextView) itemView.findViewById(R.id.text_view_setting_item_title);

        findViews(itemView);
    }
}
