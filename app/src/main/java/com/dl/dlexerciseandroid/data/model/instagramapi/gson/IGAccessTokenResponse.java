package com.dl.dlexerciseandroid.data.model.instagramapi.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by logicmelody on 2017/11/7.
 */

public class IGAccessTokenResponse {

    @SerializedName("access_token")
    @Expose
    private String mAccessToken;

    @SerializedName("user")
    @Expose
    private IGUser mIGUser;


    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        this.mAccessToken = accessToken;
    }

    public IGUser getIGUser() {
        return mIGUser;
    }

    public void setIGUser(IGUser igUser) {
        this.mIGUser = igUser;
    }
}
