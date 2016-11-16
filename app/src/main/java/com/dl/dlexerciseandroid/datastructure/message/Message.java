package com.dl.dlexerciseandroid.datastructure.message;

/**
 * Created by dannylin on 2016/11/10.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 相當於我們設計的Message data structure
 */
public class Message implements Parcelable {

    /**
     * 這邊多加了一個property，用來存這筆message是誰發送的
     */
    public static final class Owner {
        public static final int ME = 0;
        public static final int YING = 1;
    }

    public static final class ChatViewType {
        public static final int NORMAL = 0;
        public static final int YING_NORMAL = 1;
        public static final int HORIZONTAL_LIST = 2;
    }

    protected int mOwner;
    protected String mMessage;
    protected int mViewType;
    protected long mTime;


    public Message(int owner, String message, int viewType, long time) {
        mOwner = owner;
        mMessage = message;
        mViewType = viewType;
        mTime = time;
    }

    protected Message(Parcel in) {
        mOwner = in.readInt();
        mMessage = in.readString();
        mViewType = in.readInt();
        mTime = in.readLong();
    }

    public int getOwner() {
        return mOwner;
    }

    public String getMessage() {
        return mMessage;
    }

    public int getViewType() {
        return mViewType;
    }

    public long getTime() {
        return mTime;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mOwner);
        dest.writeString(mMessage);
        dest.writeInt(mViewType);
        dest.writeLong(mTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
