package com.dl.dlexerciseandroid.ui.settings;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dl.dlexerciseandroid.model.settings.BasedSettingModel;

/**
 * Created by logicmelody on 2017/8/30.
 */

public class SettingManager {

    public static final class ItemViewType {
        public static final int ONE_LINE = 0;
        public static final int TWO_LINE = 1;
        public static final int TWO_LINE_WITH_SWITCH = 2;
    }

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

    public void addItem(BasedSettingModel settingModel) {
        if (mSettingRecyclerView == null) {
            return;
        }

        mSettingAdapter.add(settingModel);
    }

    public void clearList() {
        if (mSettingRecyclerView == null) {
            return;
        }

        mSettingAdapter.clear();
    }

    public void refreshList() {
        if (mSettingRecyclerView == null) {
            return;
        }

        mSettingAdapter.notifyDataSetChanged();
    }
}
