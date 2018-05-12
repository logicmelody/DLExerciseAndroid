package com.dl.dlexerciseandroid.data.model.message;

/**
 * Created by dannylin on 2016/11/16.
 */

public class MessageFactory {

    public static Message createMessage(long id, int owner, String message, int viewType, long time) {
        switch (viewType) {
            case Message.ChatViewType.NORMAL:
            case Message.ChatViewType.YING_NORMAL:
                return new Message(id, owner, message, viewType, time);

            case Message.ChatViewType.HORIZONTAL_LIST:
                return new ListMessage(id, owner, message, viewType, time);

            default:
                return new Message(id, owner, message, viewType, time);
        }
    }
}
