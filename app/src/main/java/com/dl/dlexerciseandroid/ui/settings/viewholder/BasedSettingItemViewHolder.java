package com.dl.dlexerciseandroid.ui.settings.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dl.dlexerciseandroid.datastructure.settings.SettingData;

/**
 * Created by logicmelody on 2017/8/30.
 */

abstract public class BasedSettingItemViewHolder extends RecyclerView.ViewHolder {

    abstract protected void findViews(View itemView);
    abstract public void onBind(SettingData settingData);

    protected View mRootView;


    public BasedSettingItemViewHolder(View itemView) {
        super(itemView);
        mRootView = itemView;
        findViews(itemView);
    }
}
