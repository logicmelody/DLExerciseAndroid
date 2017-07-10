package com.dl.dlexerciseandroid.ui.instagramapi;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.dl.dlexerciseandroid.datastructure.IGUser;
import com.dl.dlexerciseandroid.utility.utils.HttpUtils;
import com.dl.dlexerciseandroid.utility.utils.InstagramApiUtils;
import com.dl.dlexerciseandroid.utility.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 與Instagram server溝通，拿到token跟login user的一些基本資訊
 *
 * Created by logicmelody on 2017/7/9.
 */

public class GetAuthenticationTokenAsyncTask extends AsyncTask<String, Void, String> {

    public interface OnGetAuthenticationTokenListener {
        void onGetAuthenticationTokenSuccessful();
        void onGetAuthenticationTokenFailed();
    }

    private Context mContext;
    private OnGetAuthenticationTokenListener mOnGetAuthenticationTokenListener;


    public GetAuthenticationTokenAsyncTask(Context context, OnGetAuthenticationTokenListener listener) {
        mContext = context;
        mOnGetAuthenticationTokenListener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String jsonString;
        String token;

        try {
            jsonString = HttpUtils.postJsonStringFromUrl(InstagramApiUtils.getTokenUrl(),
                                                         InstagramApiUtils.getTokenBodyMap(mContext, params[0]));

            JSONObject authenticationObject = new JSONObject(jsonString);
            token = JsonUtils.getStringFromJson(authenticationObject, InstagramApiUtils.EndPointKeys.ACCESS_TOKEN);

            if (TextUtils.isEmpty(token)) {
                return "";
            }

            IGUser loginUser = InstagramApiUtils
                    .getLoginUser(authenticationObject.getJSONObject(InstagramApiUtils.EndPointKeys.USER));

            if (loginUser == null) {
                return "";
            }

            InstagramDataCache.getInstance().setToken(token);
            InstagramDataCache.getInstance().setLoginUser(loginUser);

        } catch (IOException e) {
            e.printStackTrace();
            token = "";

        } catch (JSONException e) {
            e.printStackTrace();
            token = "";
        }

        return token;
    }

    @Override
    protected void onPostExecute(String token) {
        super.onPostExecute(token);

        if (TextUtils.isEmpty(token)) {
            mOnGetAuthenticationTokenListener.onGetAuthenticationTokenFailed();

        } else {
            mOnGetAuthenticationTokenListener.onGetAuthenticationTokenSuccessful();
        }
    }
}
