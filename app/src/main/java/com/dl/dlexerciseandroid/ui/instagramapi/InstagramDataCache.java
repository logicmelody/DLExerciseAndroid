package com.dl.dlexerciseandroid.ui.instagramapi;

import com.dl.dlexerciseandroid.datastructure.IGUser;

/**
 * 一個Singleton的class，用來作Instagram data的cache
 *
 * Created by logicmelody on 2017/7/10.
 */

public class InstagramDataCache {

    private volatile static InstagramDataCache sInstagramDataCache = null;

    private String mToken;

    private IGUser mLoginUser;


    private InstagramDataCache() {}

    public static InstagramDataCache getInstance() {
        if (sInstagramDataCache == null) {
            synchronized (InstagramDataCache.class) {
                if (sInstagramDataCache == null) {
                    sInstagramDataCache = new InstagramDataCache();
                }
            }
        }

        return sInstagramDataCache;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getToken() {
        return mToken;
    }

    public void setLoginUser(IGUser loginUser) {
        mLoginUser = loginUser;
    }

    public IGUser getLoginUser() {
        return mLoginUser;
    }
}
