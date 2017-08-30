package com.dl.dlexerciseandroid.ui.settings;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dl.dlexerciseandroid.datastructure.settings.SettingData;

/**
 * Created by logicmelody on 2017/8/30.
 */

public class SettingManager {

    private Context mContext;

    private RecyclerView mSettingRecyclerView;
    private SettingAdapter mSettingAdapter;


    public SettingManager(Context context, RecyclerView settingRecyclerView) {
        mContext = context;
        mSettingRecyclerView = settingRecyclerView;

        if (settingRecyclerView == null) {
            return;
        }

        initialize();
    }

    private void initialize() {
        mSettingRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mSettingAdapter = new SettingAdapter(mContext);

        mSettingRecyclerView.setAdapter(mSettingAdapter);
    }

    public void addItem(SettingData settingData) {
        mSettingAdapter.add(settingData);
    }

    public void clearList() {
        mSettingAdapter.clear();
    }

    public void refreshList() {
        mSettingAdapter.notifyDataSetChanged();
    }
}
