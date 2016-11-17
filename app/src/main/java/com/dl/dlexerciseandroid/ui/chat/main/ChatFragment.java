package com.dl.dlexerciseandroid.ui.chat.main;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.backgroundtask.service.MessageService;
import com.dl.dlexerciseandroid.database.dbscheme.DLExerciseContract;
import com.dl.dlexerciseandroid.datastructure.message.ListMessage;
import com.dl.dlexerciseandroid.datastructure.message.Message;
import com.dl.dlexerciseandroid.datastructure.message.Message.ChatViewType;
import com.dl.dlexerciseandroid.datastructure.message.MessageFactory;
import com.dl.dlexerciseandroid.ui.chat.chatlist.ChatListAdapter;
import com.dl.dlexerciseandroid.ui.chat.chatlist.MessageItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/6/27.
 */
public class ChatFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = ChatFragment.class.getName();
    private static final int LOADER_ID = 31;

    private static final String[] mProjection = new String[] {
            DLExerciseContract.Message._ID,
            DLExerciseContract.Message.OWNER,
            DLExerciseContract.Message.TEXT,
            DLExerciseContract.Message.VIEW_TYPE,
            DLExerciseContract.Message.TIME,
    };
    private static final int ID = 0;
    private static final int OWNER = 1;
    private static final int TEXT = 2;
    private static final int VIEW_TYPE = 3;
    private static final int TIME = 4;

    // 在order欄位的後面加上"ASC" or "DESC"，可以指定要由小到大 or 由大到小排序
    private static final String mSortOrder = DLExerciseContract.Message.TIME;

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

    private TextView mNoMessageText;
    private ImageView mYingSpeakButton;
    private EditText mMessageBox;
    private ImageView mListMessageButton;
    private Button mSendButton;

    private List<Message> mDataList = new ArrayList<>();


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
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private void initialize() {
        findViews();
        setupChatList();
        setupChatPanel();
    }

    private void findViews() {
        mChatList = (RecyclerView) getView().findViewById(R.id.recyclerview_chat_list);
        mNoMessageText = (TextView) getView().findViewById(R.id.text_view_chat_list_no_message);
        mYingSpeakButton = (ImageView) getView().findViewById(R.id.image_view_chat_panel_ying_speak_button);
        mMessageBox = (EditText) getView().findViewById(R.id.edit_text_chat_panel_text_box);
        mListMessageButton = (ImageView) getView().findViewById(R.id.image_view_chat_panel_list_message_button);
        mSendButton = (Button) getView().findViewById(R.id.button_chat_panel_send);
    }

    private void setupChatList() {
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mChatListAdapter = new ChatListAdapter(mContext, mDataList);

        // 加這一行，可以讓訊息從list的底部開始塞
        // 可以讓訊息很少的時候，從下面開始塞訊息
        mLinearLayoutManager.setStackFromEnd(true);

        mChatList.setLayoutManager(mLinearLayoutManager);
        mChatList.addItemDecoration(new MessageItemDecoration(mContext));
        mChatList.setAdapter(mChatListAdapter);
    }

    private void setupChatPanel() {
        mYingSpeakButton.setOnClickListener(this);
        mListMessageButton.setOnClickListener(this);
        mSendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_chat_panel_ying_speak_button:
                yingSpeak();

                break;

            case R.id.image_view_chat_panel_list_message_button:
                sendListMessage();

                break;

            case R.id.button_chat_panel_send:
                sendMessage();

                break;
        }
    }

    private void yingSpeak() {
        saveMessage(new Message(Message.Owner.YING, "小影說話", ChatViewType.YING_NORMAL, System.currentTimeMillis()));
    }

    private void sendMessage() {
        String messageText = mMessageBox.getText().toString();

        if (TextUtils.isEmpty(messageText)) {
            return;
        }

        saveMessage(new Message(Message.Owner.ME, messageText, ChatViewType.NORMAL, System.currentTimeMillis()));

        mMessageBox.setText("");
    }

    private void saveMessage(Message message) {
        mContext.startService(MessageService.generateSaveMessageIntent(mContext, message));
    }

    private void sendListMessage() {
        saveMessage(new ListMessage(Message.Owner.ME, "", ChatViewType.HORIZONTAL_LIST, System.currentTimeMillis()));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mContext, DLExerciseContract.Message.CONTENT_URI, mProjection, null, null, mSortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("danny", "Chat list onLoadFinished()");

        // Cursor的data count=0，代表沒有data符合我們下的query SQL語法
        // Cursor若是null，代表在query的時候發生錯誤，也有可能會丟出Exception
        if (data == null) {
            return;
        }

        setChatListData(data);
    }

    private void setChatListData(Cursor data) {
        mDataList.clear();

        while (data.moveToNext()) {
            long id = data.getLong(ID);
            int owner = data.getInt(OWNER);
            String text = data.getString(TEXT);
            int viewType = data.getInt(VIEW_TYPE);
            long time = data.getLong(TIME);

            mDataList.add(MessageFactory.createMessage(owner, text, viewType, time));
        }

        /**
         * 每次當我們的data有更新的時候，e.g. 新增一筆data or 刪除一筆data
         * 需要update我們的RecyclerView，方法是用Adapter call notifyDataSetChanged()
         *
         * 改變UI的這段一定要在UI thread上做!!!
         */
        mChatListAdapter.notifyDataSetChanged();
        scrollChatListToBottom();

        mNoMessageText.setVisibility(mDataList.size() == 0 ? View.VISIBLE : View.GONE);
    }

    private void scrollChatListToBottom() {
        mChatList.setVerticalScrollBarEnabled(false);
        mChatList.scrollToPosition(mDataList.size() - 1);
        mChatList.setVerticalScrollBarEnabled(true);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}