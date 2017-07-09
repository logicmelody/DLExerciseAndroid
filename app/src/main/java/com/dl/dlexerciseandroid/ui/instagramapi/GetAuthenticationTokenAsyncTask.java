package com.dl.dlexerciseandroid.ui.instagramapi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dl.dlexerciseandroid.utility.utils.HttpUtils;
import com.dl.dlexerciseandroid.utility.utils.InstagramApiUtils;

import java.io.IOException;

/**
 * Created by logicmelody on 2017/7/9.
 */

public class GetAuthenticationTokenAsyncTask extends AsyncTask<String, Void, String> {

    private Context mContext;


    public GetAuthenticationTokenAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String jsonString = "";

        try {
            jsonString = HttpUtils.postJsonStringFromUrl(InstagramApiUtils.getTokenUrl(),
                                                         InstagramApiUtils.getTokenBodyMap(mContext, params[0]));
        } catch (IOException e) {
            Log.d("danny", "Get authentication token failed");

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Log.d("danny", "Get authentication token Json = " + s);
    }
}
