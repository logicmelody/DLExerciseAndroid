package com.dl.dlexerciseandroid.ui.instagramapi.feedview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dl.dlexerciseandroid.datastructure.instagramapi.IGImage;
import com.dl.dlexerciseandroid.datastructure.message.Message;

/**
 * Created by dannylin on 2016/11/16.
 */

abstract public class BaseViewHolder extends RecyclerView.ViewHolder {

    abstract protected void findViews(View itemView);
    abstract public void bind(IGImage igImage);


    public BaseViewHolder(View itemView) {
        super(itemView);
        findViews(itemView);
    }
}
