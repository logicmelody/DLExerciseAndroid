package com.dl.dlexerciseandroid.datastructure.message;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dannylin on 2016/11/16.
 */

public class ListMessage extends Message {

    private List<Integer> mDataList = new ArrayList<>();


    public ListMessage(long id, int owner, String message, int viewType, long time) {
        super(id, owner, message, viewType, time);

        for (int i = 0 ; i <= 5 ; i ++) {
            mDataList.add(i);
        }
    }

    protected ListMessage(Parcel in) {
        super(in);
    }

    public List<Integer> getDataList() {
        return mDataList;
    }
}
