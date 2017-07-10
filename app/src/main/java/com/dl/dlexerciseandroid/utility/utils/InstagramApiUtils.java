package com.dl.dlexerciseandroid.utility.utils;

import android.content.Context;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.IGUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by logicmelody on 2017/7/9.
 */

public class InstagramApiUtils {

    private static final String AUTHORIZATION_URL =
            "https://api.instagram.com/oauth/authorize/?client_id=CLIENT-ID&redirect_uri=REDIRECT-URI&response_type=code";
    private static final String TOKEN_URL = "https://api.instagram.com/oauth/access_token";
    private static final String API_URL = "https://api.instagram.com/v1";

    public static final class EndPointKeys {
        public static final String ACCESS_TOKEN = "access_token";
        public static final String USER = "user";
        public static final String ID = "id";
        public static final String USER_NAME = "username";
        public static final String FULL_NAME = "full_name";
        public static final String PROFILE_PICTURE = "profile_picture";
    }


    public static String getAuthorizationUrl(Context context) {
        return AUTHORIZATION_URL.replaceAll("CLIENT-ID", context.getString(R.string.instagram_api_client_id))
                                .replaceAll("REDIRECT-URI", context.getString(R.string.instagram_api_client_redirect_uri));
    }

    public static String getAuthorizationUrl(String clientId, String redirectUri) {
        return AUTHORIZATION_URL.replaceAll("CLIENT-ID", clientId).replaceAll("REDIRECT-URI", redirectUri);
    }

    public static String getTokenUrl() {
        return TOKEN_URL;
    }

    public static Map<String, String> getTokenBodyMap(Context context, String code) {
        Map<String, String> map = new HashMap<>();

        map.put("client_id", context.getString(R.string.instagram_api_client_id));
        map.put("client_secret", context.getString(R.string.instagram_api_client_secret));
        map.put("redirect_uri", context.getString(R.string.instagram_api_client_redirect_uri));
        map.put("grant_type", "authorization_code");
        map.put("code", code);

        return map;
    }

    public static IGUser getLoginUser(JSONObject userObject) throws JSONException {
        if (userObject == null) {
            return null;
        }

        String id = JsonUtils.getStringFromJson(userObject, EndPointKeys.ID);
        String userName = JsonUtils.getStringFromJson(userObject, EndPointKeys.USER_NAME);
        String fullName = JsonUtils.getStringFromJson(userObject, EndPointKeys.FULL_NAME);
        String profilePicture = JsonUtils.getStringFromJson(userObject, EndPointKeys.PROFILE_PICTURE);

        return new IGUser(id, userName, fullName, profilePicture);
    }
}