package com.dl.dlexerciseandroid.utility.utils;

import android.content.Context;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2017/7/9.
 */

public class InstagramApiUtils {

    private static final String AUTHORIZATION_URL =
            "https://api.instagram.com/oauth/authorize/?client_id=CLIENT-ID&redirect_uri=REDIRECT-URI&response_type=code";
    private static final String TOKEN_URL = "https://api.instagram.com/oauth/access_token";
    private static final String API_URL = "https://api.instagram.com/v1";


    public static String getAuthorizationUrl(Context context) {
        return AUTHORIZATION_URL.replaceAll("CLIENT-ID", context.getString(R.string.instagram_api_client_id))
                                .replaceAll("REDIRECT-URI", context.getString(R.string.instagram_api_client_redirect_uri));
    }

    public static String getAuthorizationUrl(String clientId, String redirectUri) {
        return AUTHORIZATION_URL.replaceAll("CLIENT-ID", clientId).replaceAll("REDIRECT-URI", redirectUri);
    }

    public static String getTokenUrl(Context context, String code) {
        return new StringBuilder(TOKEN_URL)
                .append("?client_id=").append(context.getString(R.string.instagram_api_client_id))
                .append("&client_secret=").append(context.getString(R.string.instagram_api_client_secret))
                .append("&redirect_uri=").append(context.getString(R.string.instagram_api_client_redirect_uri))
                .append("&grant_type=").append(code).toString();
    }

    public static String getTokenUrl(String clientId, String clientSecret, String redirectUri, String code) {
        return new StringBuilder(TOKEN_URL)
                .append("?client_id=").append(clientId)
                .append("&client_secret=").append(clientSecret)
                .append("&redirect_uri=").append(redirectUri)
                .append("&grant_type=").append(code).toString();
    }
}