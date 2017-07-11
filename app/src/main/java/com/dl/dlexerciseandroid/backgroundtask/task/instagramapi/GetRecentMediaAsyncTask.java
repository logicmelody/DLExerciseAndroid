package com.dl.dlexerciseandroid.backgroundtask.task.instagramapi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dl.dlexerciseandroid.datastructure.instagramapi.IGImage;
import com.dl.dlexerciseandroid.ui.instagramapi.InstagramDataCache;
import com.dl.dlexerciseandroid.utility.utils.HttpUtils;
import com.dl.dlexerciseandroid.utility.utils.InstagramApiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by logicmelody on 2017/7/11.
 */

public class GetRecentMediaAsyncTask extends AsyncTask<Void, Void, List<IGImage>> {

    private static final int DEFAULT_COUNT = 10;

    public interface OnGetRecentMediaListener {
        void onGetRecentMediaSuccessful();
        void onGetRecentMediaFailed();
    }

    private Context mContext;
    private OnGetRecentMediaListener mOnGetRecentMediaListener;

    private String mUserId;


    public GetRecentMediaAsyncTask(Context context, OnGetRecentMediaListener listener) {
        mContext = context;
        mOnGetRecentMediaListener = listener;
    }

    public GetRecentMediaAsyncTask(Context context, OnGetRecentMediaListener listener, String userId) {
        mContext = context;
        mOnGetRecentMediaListener = listener;
        mUserId = userId;
    }

    @Override
    protected List<IGImage> doInBackground(Void... params) {
        String getRecentMediaUrl =
                InstagramApiUtils.getRecentMediaUrl(InstagramDataCache.getTokenFromSharedPreference(mContext),
                                                    mUserId, "", "", DEFAULT_COUNT);
        String jsonString;

        try {
            jsonString = HttpUtils.getJsonStringFromUrl(getRecentMediaUrl);

            Log.d("danny", jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<IGImage> igImages) {
        super.onPostExecute(igImages);
    }
}