package com.dl.dlexerciseandroid.ui.chat.chatlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

import java.util.List;

/**
 * Created by dannylin on 2016/11/9.
 */

/**
 * 這個Adapter是用來產生RecyclerView中每一個item的關鍵class
 * 需要自己實作並且extends RecyclerView.Adapter<RecyclerView.ViewHolder>
 */
public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final class ChatViewType {
        public static final int NORMAL = 0;
        public static final int YING_NORMAL = 1;
    }

    /**
     * 每一個list item都需要一個view holder去記錄這個view中包含的元件
     */
    private class NormalMessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;

        public NormalMessageViewHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_view_normal_message_text);
        }
    }

    private class YingNormalMessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;

        public YingNormalMessageViewHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_view_ying_normal_message_text);
        }
    }

    private Context mContext;
    private List<ChatListItem> mDataList;


    public ChatListAdapter(Context context, List<ChatListItem> dataList) {
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

            default:
                return null;
        }
    }

    /**
     * 主要需要Override的method
     * 將view holder跟data bind在一起
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }

        switch (mDataList.get(position).viewType) {
            case ChatViewType.NORMAL:
                bindNormalMessageView((NormalMessageViewHolder) holder, position);
                break;

            case ChatViewType.YING_NORMAL:
                bindYingNormalMessage((YingNormalMessageViewHolder) holder, position);
                break;
        }
    }

    private void bindNormalMessageView(NormalMessageViewHolder vh, int position) {
        vh.messageText.setText(mDataList.get(position).message.getMessage());
    }

    private void bindYingNormalMessage(YingNormalMessageViewHolder vh, int position) {
        vh.messageText.setText(mDataList.get(position).message.getMessage());
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
        return mDataList.get(position).viewType;
    }
}
