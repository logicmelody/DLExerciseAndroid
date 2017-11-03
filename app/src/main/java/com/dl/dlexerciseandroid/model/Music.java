package com.dl.dlexerciseandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by logicmelody on 2016/5/9.
 */
public class Music implements Parcelable {

    public long id;
    public String title;
    public String artist;

    public Music(long id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    protected Music(Parcel in) {
        id = in.readLong();
        title = in.readString();
        artist = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(artist);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Music> CREATOR = new Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };
}
