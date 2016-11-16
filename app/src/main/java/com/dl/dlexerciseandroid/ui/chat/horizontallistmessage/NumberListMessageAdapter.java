package com.dl.dlexerciseandroid.ui.chat.horizontallistmessage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

import java.util.List;

/**
 * Created by dannylin on 2016/11/16.
 */

public class NumberListMessageAdapter extends BaseHorizontalListMessageAdapter<Integer> {

    private class NumberViewHolder extends RecyclerView.ViewHolder {

        public TextView numberText;

        public NumberViewHolder(View itemView) {
            super(itemView);
            numberText = (TextView) itemView.findViewById(R.id.text_view_list_message_number_text);
        }
    }

    public NumberListMessageAdapter(Context context, List<Integer> dataList) {
        super(context, dataList);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new NumberViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_message_number,
                parent, false));
    }

    @Override
    public void bind(RecyclerView.ViewHolder holder, Integer data) {
        NumberViewHolder vh = (NumberViewHolder) holder;

        vh.numberText.setText(String.valueOf(data));
    }
}
