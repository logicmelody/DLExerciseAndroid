package com.dl.dlexerciseandroid.ui.chat.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.message.ListMessage;
import com.dl.dlexerciseandroid.datastructure.message.Message;
import com.dl.dlexerciseandroid.ui.chat.horizontallistmessage.NumberListMessageAdapter;

/**
 * Created by dannylin on 2016/11/16.
 */

/**
 * 每一個list item都需要一個view holder去記錄這個view中包含的元件
 */
public class HorizontalListMessageViewHolder extends BaseViewHolder {

    private Context mContext;

    private View mRootView;
    private RecyclerView mHorizontalList;


    public HorizontalListMessageViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        setupHorizontalList();
    }

    private void setupHorizontalList() {
        mHorizontalList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void findViews(View itemView) {
        mRootView = itemView;
        mHorizontalList = (RecyclerView) itemView.findViewById(R.id.recyclerview_chat_list_message_horizontal_list);
    }

    @Override
    public void bind(Message message) {
        // 轉型成對應的message data
        ListMessage listMessage = (ListMessage) message;

        // 各自的message data有自己的extra data list，交由各自實作的adapter去handle
        mHorizontalList.setAdapter(new NumberListMessageAdapter(mContext, listMessage.getDataList()));
    }
}
