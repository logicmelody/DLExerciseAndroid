package com.dl.dlexerciseandroid.model.instagramapi.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by logicmelody on 2017/11/7.
 */

public class IGUsersSelfResponse {

    @SerializedName("data")
    @Expose
    private IGUser mIGUser;


    public IGUser getIGUser() {
        return mIGUser;
    }

    public void setIGUser(IGUser igUser) {
        this.mIGUser = igUser;
    }
}
