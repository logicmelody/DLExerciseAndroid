package com.dl.dlexerciseandroid.ui.settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.settings.BasedSettingModel;
import com.dl.dlexerciseandroid.ui.settings.viewholder.BasedSettingItemViewHolder;
import com.dl.dlexerciseandroid.ui.settings.viewholder.OneLineSettingItemViewHolder;
import com.dl.dlexerciseandroid.ui.settings.viewholder.TwoLineSettingItemViewHolder;
import com.dl.dlexerciseandroid.ui.settings.viewholder.TwoLineWithSwitchSettingItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2017/8/30.
 */

public class SettingAdapter extends RecyclerView.Adapter<BasedSettingItemViewHolder> {

    private Context mContext;

    private List<BasedSettingModel> mDataList;


    public SettingAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public BasedSettingItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case SettingManager.ItemViewType.ONE_LINE:
                return new OneLineSettingItemViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_setting_one_line, viewGroup, false));

            case SettingManager.ItemViewType.TWO_LINE:
                return new TwoLineSettingItemViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_setting_two_line, viewGroup, false));

            case SettingManager.ItemViewType.TWO_LINE_WITH_SWITCH:
                return new TwoLineWithSwitchSettingItemViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_setting_two_line_with_switch, viewGroup, false));

            default:
                return new OneLineSettingItemViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_setting_one_line, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(BasedSettingItemViewHolder viewHolder, int i) {
        viewHolder.onBind(mDataList.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getViewType();
    }

    public void add(BasedSettingModel settingModel) {
        mDataList.add(settingModel);
    }

    public void clear() {
        mDataList.clear();
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}
