package com.dl.dlexerciseandroid.features.chat.chatlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.data.model.message.Message;
import com.dl.dlexerciseandroid.data.model.message.Message.ChatViewType;
import com.dl.dlexerciseandroid.features.chat.viewholder.BaseViewHolder;
import com.dl.dlexerciseandroid.features.chat.viewholder.HorizontalListMessageViewHolder;
import com.dl.dlexerciseandroid.features.chat.viewholder.NormalMessageViewHolder;
import com.dl.dlexerciseandroid.features.chat.viewholder.YingNormalMessageViewHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Long> mIdMap;


    /**
     * 之後將要丟到Adapter的data list的操作都在Adapter中進行
     * 所以需要照使用需求開API給外層的view
     *
     * 這樣的好處是可以將view跟data完全切開，之後RecyclerView只需要切換Adapter就可以改變裡面的資料
     */
    public ChatListAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
        mIdMap = new HashSet<>();
    }

    /**
     * 主要需要Override的method
     * 根據不同的view type去產生對應的view holder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ChatViewType.NORMAL:
                return new NormalMessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_normal_message,
                        parent, false));

            case ChatViewType.YING_NORMAL:
                return new YingNormalMessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ying_normal_message,
                        parent, false));

            case ChatViewType.HORIZONTAL_LIST:
                return new HorizontalListMessageViewHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.item_list_message,
                        parent, false));

            default:
                return new NormalMessageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_normal_message,
                        parent, false));
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

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    public void add(Message message) {
        // 用map來記錄已經存在adapter中的data id，利用此方式可以濾掉onLoadFinished的cursor中，一些已經重複的data
        if (mIdMap.contains(message.getId())) {
            return;
        }

        mDataList.add(message);
        mIdMap.add(message.getId());

        // 以後也盡量在adapter中作notify的動作，像這邊是insert一筆data，並且有動畫的效果
        notifyItemInserted(mDataList.indexOf(message));
    }

    public int getDataListSize() {
        return mDataList.size();
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}
