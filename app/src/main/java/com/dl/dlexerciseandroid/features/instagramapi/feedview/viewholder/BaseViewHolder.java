package com.dl.dlexerciseandroid.features.instagramapi.feedview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dl.dlexerciseandroid.data.model.instagramapi.gson.IGMedia;

/**
 * Created by dannylin on 2016/11/16.
 */

abstract public class BaseViewHolder extends RecyclerView.ViewHolder {

    abstract protected void findViews(View itemView);
    abstract public void bind(IGMedia igMedia);

    protected View mRootView;


    public BaseViewHolder(View itemView) {
        super(itemView);
        mRootView = itemView;
        findViews(itemView);
    }
}
