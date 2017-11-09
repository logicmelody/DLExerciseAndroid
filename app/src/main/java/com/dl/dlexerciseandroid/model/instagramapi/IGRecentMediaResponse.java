package com.dl.dlexerciseandroid.model.instagramapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by logicmelody on 2017/11/7.
 */

// 利用Gson來deserialize成物件的時候，可以用一個response物件來包第一層
public class IGRecentMediaResponse {

    @SerializedName("data")
    @Expose
    private List<IGMedia> mIGMedias = null;

    private transient String mNextMaxId;


    public List<IGMedia> getIGMedias() {
        return mIGMedias;
    }

    public void setIGMedias(List<IGMedia> igMedias) {
        this.mIGMedias = igMedias;
    }

    public String getNextMaxId() {
        return mNextMaxId;
    }

    public void setNextMaxId(String nextMaxId) {
        this.mNextMaxId = nextMaxId;
    }
}
