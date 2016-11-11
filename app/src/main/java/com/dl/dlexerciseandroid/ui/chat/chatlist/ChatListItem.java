package com.dl.dlexerciseandroid.ui.chat.chatlist;

/**
 * Created by dannylin on 2016/11/10.
 */

import com.dl.dlexerciseandroid.datastructure.Message;

/**
 * 我們在Message的外面再包一層，因為在Adapter產生view的時候，我們會需要知道view type是什麼
 */
public class ChatListItem {

    public Message message;
    public int viewType;

    public ChatListItem(Message message, int viewType) {
        this.message = message;
        this.viewType = viewType;
    }
}
