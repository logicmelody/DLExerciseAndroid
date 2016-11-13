package com.dl.dlexerciseandroid.datastructure;

/**
 * Created by dannylin on 2016/11/10.
 */

/**
 * 相當於我們設計的Message data structure
 */
public class Message {

    /**
     * 這邊多加了一個property，用來存這筆message是誰發送的
     */
    public static final class Owner {
        public static final int ME = 0;
        public static final int YING = 1;
    }

    private int mOwner;
    private String mMessage;
    private long mTime;


    public Message(int owner, String message, long time) {
        mOwner = owner;
        mMessage = message;
        mTime = time;
    }

    public int getOwner() {
        return mOwner;
    }

    public String getMessage() {
        return mMessage;
    }

    public long getTime() {
        return mTime;
    }
}
