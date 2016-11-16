package com.dl.dlexerciseandroid.ui.chat.chatlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.Message;
import com.dl.dlexerciseandroid.datastructure.Message.ChatViewType;
import com.dl.dlexerciseandroid.ui.chat.viewholder.BaseViewHolder;
import com.dl.dlexerciseandroid.ui.chat.viewholder.ListMessageViewHolder;
import com.dl.dlexerciseandroid.ui.chat.viewholder.NormalMessageViewHolder;
import com.dl.dlexerciseandroid.ui.chat.viewholder.YingNormalMessageViewHolder;

import java.util.List;

/**
 * Created by dannylin on 2016/11/9.
 */

/**
 * 這個Adapter是用來產生RecyclerView中每一個item的關鍵class
 * 需要自己實作並且extends RecyclerView.Adapter<RecyclerView.ViewHolder>
 */
public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Message> mDataList;


    public ChatListAdapter(Context context, List<Message> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    /**
     * 主要需要Override的method
     * 根據不同的view type去產生對應的view holder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ChatViewType.NORMAL:
                return new NormalMessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_normal_message, parent, false));

            case ChatViewType.YING_NORMAL:
                return new YingNormalMessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ying_normal_message,
                                                       parent, false));

            case ChatViewType.HORIZONTAL_LIST:
                return new ListMessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ying_normal_message,
                                                 parent, false));

            default:
                return new NormalMessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_normal_message, parent, false));
        }
    }

    /**
     * 主要需要Override的method
     * 將view holder跟data bind在一起
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null || !(holder instanceof BaseViewHolder)) {
            return;
        }

        ((BaseViewHolder) holder).bind(mDataList.get(position));
    }

    /**
     * 主要需要Override的method
     * 回傳現在data list中總共有幾筆資料
     */
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 可以Override，也可以不Override
     *
     * 回傳每個item的view type，一般如果每個item都長一樣的話，這個method不需要override，
     * 但我們的chat list，需要顯示出小影的訊息跟user的訊息，所以會需要兩種不同的view
     */
    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getViewType();
    }
}
