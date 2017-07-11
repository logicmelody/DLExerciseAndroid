package com.dl.dlexerciseandroid.ui.instagramapi;

import android.content.Context;
import android.preference.PreferenceManager;

import com.dl.dlexerciseandroid.datastructure.instagramapi.IGUser;

/**
 * 一個Singleton的class，用來作Instagram data的cache
 *
 * Created by logicmelody on 2017/7/10.
 */

public class InstagramDataCache {

    private static final String PREFERENCE_TOKEN = "com.dl.dlexerciseandroid.PREFERENCE_TOKEN";

    private volatile static InstagramDataCache sInstagramDataCache = null;

    private String mToken;

    private IGUser mLoginUser;

    private String mMediaNextMaxId;


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

    public static void saveTokenToSharedPreference(Context context, String token) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                         .putString(PREFERENCE_TOKEN, token).apply();
    }

    public static String getTokenFromSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREFERENCE_TOKEN, "");
    }

    public static boolean hasTokenInSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(PREFERENCE_TOKEN);
    }

    public void setLoginUser(IGUser loginUser) {
        mLoginUser = loginUser;
    }

    public IGUser getLoginUser() {
        return mLoginUser;
    }

    public void setMediaNextMaxId(String nextMaxId) {
        mMediaNextMaxId = nextMaxId;
    }

    public String getMediaNextMaxId() {
        return mMediaNextMaxId;
    }
}
