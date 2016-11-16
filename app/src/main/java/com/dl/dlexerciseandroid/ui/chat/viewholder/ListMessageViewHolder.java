package com.dl.dlexerciseandroid.ui.chat.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.message.ListMessage;
import com.dl.dlexerciseandroid.datastructure.message.Message;

import java.util.List;

/**
 * Created by dannylin on 2016/11/16.
 */

/**
 * 每一個list item都需要一個view holder去記錄這個view中包含的元件
 */
public class ListMessageViewHolder extends BaseViewHolder {

    private Context mContext;

    private RecyclerView mHorizontalList;


    private class ListMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private List<Integer> mDataList;


        private class NumberViewHolder extends RecyclerView.ViewHolder {

            public TextView numberText;

            public NumberViewHolder(View itemView) {
                super(itemView);
                numberText = (TextView) itemView.findViewById(R.id.text_view_list_message_number_text);
            }
        }

        public ListMessageAdapter(Context context, List<Integer> dataList) {
            mContext = context;
            mDataList = dataList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NumberViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_message_number,
                    parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            NumberViewHolder vh = (NumberViewHolder) holder;

            vh.numberText.setText(String.valueOf(mDataList.get(position)));
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }
    }

    public ListMessageViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
    }

    @Override
    void findViews(View itemView) {
        mHorizontalList = (RecyclerView) itemView.findViewById(R.id.recyclerview_chat_list_message_horizontal_list);
    }

    @Override
    public void bind(Message message) {
        ListMessage listMessage = (ListMessage) message;

        mHorizontalList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mHorizontalList.setAdapter(new ListMessageAdapter(mContext, listMessage.getDataList()));
    }
}
