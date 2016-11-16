package com.dl.dlexerciseandroid.ui.chat.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dl.dlexerciseandroid.datastructure.message.Message;

/**
 * Created by dannylin on 2016/11/16.
 */

abstract public class BaseViewHolder extends RecyclerView.ViewHolder {

    abstract void findViews(View itemView);
    abstract public void bind(Message message);


    public BaseViewHolder(View itemView) {
        super(itemView);
        findViews(itemView);
    }
}