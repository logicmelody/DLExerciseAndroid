package com.dl.dlexerciseandroid.ui.chat.horizontallistmessage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by dannylin on 2016/11/16.
 */

abstract public class BaseHorizontalListMessageAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    abstract public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType);
    abstract public void bind(RecyclerView.ViewHolder holder, T data);

    protected Context mContext;
    protected List<T> mDataList;


    public BaseHorizontalListMessageAdapter(Context context, List<T> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bind(holder, mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
