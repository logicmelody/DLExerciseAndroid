package com.dl.dlexerciseandroid.backgroundtask.task.instagramapi;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.dl.dlexerciseandroid.model.instagramapi.gson.IGUser;
import com.dl.dlexerciseandroid.ui.instagramapi.main.InstagramDataCache;
import com.dl.dlexerciseandroid.utility.utility.HttpUtils;
import com.dl.dlexerciseandroid.utility.utility.InstagramApiUtils;
import com.dl.dlexerciseandroid.utility.utility.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 拿token跟get login user的API做溝通，如果token是正確的沒有問題，那可以得到完整的login user資料，
 * 也代表login成功，可以使用token跟server要資料
 *
 * Created by logicmelody on 2017/7/10.
 */

public class GetLoginUserAsyncTask extends AsyncTask<Void, Void, IGUser> {

    public interface OnGetLoginUserListener {
        void onGetLoginUserSuccessful();
        void onGetLoginUserFailed();
    }

    private Context mContext;
    private OnGetLoginUserListener mOnGetLoginUserListener;


    public GetLoginUserAsyncTask(Context context, OnGetLoginUserListener listener) {
        mContext = context;
        mOnGetLoginUserListener = listener;
    }

    @Override
    protected IGUser doInBackground(Void... params) {
        IGUser loginUser;
        String token = InstagramDataCache.getTokenFromSharedPreference(mContext);
        String jsonString;

        if (TextUtils.isEmpty(token)) {
            return null;
        }

        try {
            jsonString = HttpUtils.getJsonStringFromUrl(InstagramApiUtils.getLoginUserUrl(token));

            if (TextUtils.isEmpty(jsonString)) {
                return null;
            }

            JSONObject loginUserObject = new JSONObject(jsonString);
            JSONObject dataObject =
                    JsonUtils.getJsonObjectFromJson(loginUserObject, InstagramApiUtils.EndPointKeys.DATA);

            if (dataObject == null) {
                return null;
            }

            loginUser = InstagramApiUtils.getLoginUserFromEndPoint(dataObject);
            InstagramDataCache.getInstance().setLoginUser(loginUser);

        } catch (IOException e) {
            e.printStackTrace();
            loginUser = null;

        } catch (JSONException e) {
            e.printStackTrace();
            loginUser = null;
        }

        return loginUser;
    }

    @Override
    protected void onPostExecute(IGUser igUser) {
        super.onPostExecute(igUser);

        if (igUser == null) {
            mOnGetLoginUserListener.onGetLoginUserFailed();

        } else {
            mOnGetLoginUserListener.onGetLoginUserSuccessful();
        }
    }
}
