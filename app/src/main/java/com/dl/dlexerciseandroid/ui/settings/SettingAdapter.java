package com.dl.dlexerciseandroid.ui.settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.settings.SettingData;
import com.dl.dlexerciseandroid.ui.settings.viewholder.BasedSettingItemViewHolder;
import com.dl.dlexerciseandroid.ui.settings.viewholder.SettingItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2017/8/30.
 */

public class SettingAdapter extends RecyclerView.Adapter<BasedSettingItemViewHolder> {

    private Context mContext;

    private List<SettingData> mDataList;


    public SettingAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public BasedSettingItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SettingItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_setting, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(BasedSettingItemViewHolder viewHolder, int i) {
        viewHolder.onBind(mDataList.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void add(SettingData settingData) {
        mDataList.add(settingData);
    }

    public void clear() {
        mDataList.clear();
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}
