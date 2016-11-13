package com.dl.dlexerciseandroid.ui.chat.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.Message;
import com.dl.dlexerciseandroid.ui.chat.chatlist.ChatListAdapter;
import com.dl.dlexerciseandroid.ui.chat.chatlist.ChatListAdapter.ChatViewType;
import com.dl.dlexerciseandroid.ui.chat.chatlist.ChatListItem;
import com.dl.dlexerciseandroid.ui.chat.chatlist.MessageItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/6/27.
 */
public class ChatFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = ChatFragment.class.getName();

    private Context mContext;

    /**
     * 使用RecyclerView時需要四樣東西：
     * 1. RecyclerView: 主要的View
     * 2. LinearLayoutManager: 用來決定View要怎麼呈現data的排列：
     *    Vertical ListView, Horizontal ListView, GridView, StaggeredGridView
     * 3. RecyclerView.Adapter
     * 4. 要丟到RecyclerView中呈現的data: 我都習慣用一個ArrayList來存
     */
    private RecyclerView mChatList;
    private LinearLayoutManager mLinearLayoutManager;
    private ChatListAdapter mChatListAdapter;

    private ImageView mYingSpeakButton;
    private EditText mMessageBox;
    private Button mSendButton;

    private List<ChatListItem> mDataList = new ArrayList<>();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        scrollChatListToBottom();
    }

    private void scrollChatListToBottom() {
        mChatList.setVerticalScrollBarEnabled(false);
        mChatList.scrollToPosition(mDataList.size() - 1);
        mChatList.setVerticalScrollBarEnabled(true);
    }

    private void initialize() {
        findViews();
        setupChatList();
        setupChatPanel();
    }

    private void findViews() {
        mChatList = (RecyclerView) getView().findViewById(R.id.recyclerview_chat_list);
        mYingSpeakButton = (ImageView) getView().findViewById(R.id.image_view_chat_panel_ying_speak_button);
        mMessageBox = (EditText) getView().findViewById(R.id.edit_text_chat_panel_text_box);
        mSendButton = (Button) getView().findViewById(R.id.button_chat_panel_send);
    }

    private void setupChatList() {
        setChatListData();
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mChatListAdapter = new ChatListAdapter(mContext, mDataList);

        mChatList.setLayoutManager(mLinearLayoutManager);
        mChatList.addItemDecoration(new MessageItemDecoration(mContext));
        mChatList.setAdapter(mChatListAdapter);
    }

    private void setChatListData() {
        mDataList.add(new ChatListItem(new Message(Message.Owner.ME, "我說話"), ChatViewType.NORMAL));
        mDataList.add(new ChatListItem(new Message(Message.Owner.ME, "我說話"), ChatViewType.NORMAL));
        mDataList.add(new ChatListItem(new Message(Message.Owner.ME, "我說話"), ChatViewType.NORMAL));
        mDataList.add(new ChatListItem(new Message(Message.Owner.YING, "小影說話"), ChatViewType.YING_NORMAL));
        mDataList.add(new ChatListItem(new Message(Message.Owner.YING, "小影說話"), ChatViewType.YING_NORMAL));
        mDataList.add(new ChatListItem(new Message(Message.Owner.YING, "小影說話"), ChatViewType.YING_NORMAL));
        mDataList.add(new ChatListItem(new Message(Message.Owner.ME, "我說話"), ChatViewType.NORMAL));
        mDataList.add(new ChatListItem(new Message(Message.Owner.ME, "我說話"), ChatViewType.NORMAL));
        mDataList.add(new ChatListItem(new Message(Message.Owner.ME, "我說話"), ChatViewType.NORMAL));
        mDataList.add(new ChatListItem(new Message(Message.Owner.ME, "我說話"), ChatViewType.NORMAL));
        mDataList.add(new ChatListItem(new Message(Message.Owner.ME, "我說話"), ChatViewType.NORMAL));
    }

    private void setupChatPanel() {
        mYingSpeakButton.setOnClickListener(this);
        mSendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_chat_panel_ying_speak_button:
                yingSpeak();

                break;

            case R.id.button_chat_panel_send:
                sendMessage();

                break;
        }
    }

    private void yingSpeak() {
        mDataList.add(new ChatListItem(new Message(Message.Owner.YING, "小影說話"), ChatViewType.YING_NORMAL));

        /**
         * 每次當我們的data有更新的時候，e.g. 新增一筆data or 刪除一筆data
         * 需要update我們的RecyclerView，方法是用Adapter call notifyDataSetChanged()
         */
        mChatListAdapter.notifyDataSetChanged();
        scrollChatListToBottom();
    }

    private void sendMessage() {
        String messageText = mMessageBox.getText().toString();

        if (TextUtils.isEmpty(messageText)) {
            return;
        }

        mMessageBox.setText("");
        mDataList.add(new ChatListItem(new Message(Message.Owner.ME, messageText), ChatViewType.NORMAL));
        mChatListAdapter.notifyDataSetChanged();
        scrollChatListToBottom();
    }
}