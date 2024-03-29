package com.dl.dlexerciseandroid.backgroundtask.task.instagramapi;

import android.content.Context;
import android.os.AsyncTask;

import com.dl.dlexerciseandroid.data.model.instagramapi.old.IGImage;
import com.dl.dlexerciseandroid.data.model.instagramapi.old.IGRecentMedia;
import com.dl.dlexerciseandroid.features.instagramapi.main.InstagramDataCache;
import com.dl.dlexerciseandroid.utils.HttpUtils;
import com.dl.dlexerciseandroid.utils.InstagramApiUtils;
import com.dl.dlexerciseandroid.utils.InstagramApiUtils.EndPointKeys;
import com.dl.dlexerciseandroid.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2017/7/11.
 */

public class GetRecentMediaAsyncTask extends AsyncTask<Void, Void, IGRecentMedia> {

    private static final int DEFAULT_COUNT = 20;

    public interface OnGetRecentMediaListener {
        void onGetRecentMediaSuccessful(IGRecentMedia igRecentMedia);
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
    protected IGRecentMedia doInBackground(Void... params) {
        String getRecentMediaUrl =
                InstagramApiUtils.getRecentMediaUrl(InstagramDataCache.getTokenFromSharedPreference(mContext),
                                                    mUserId, "", "", DEFAULT_COUNT);
        List<IGImage> imageList;
        String jsonString;
        String nextMaxId;

        try {
            jsonString = HttpUtils.getJsonStringFromUrl(getRecentMediaUrl);
            JSONObject jsonObject = new JSONObject(jsonString);

            if (JsonUtils.getJsonObjectFromJson(jsonObject, EndPointKeys.PAGINATION) == null) {
                return null;
            }

            imageList = new ArrayList<>();
            nextMaxId = JsonUtils.getStringFromJson(
                    JsonUtils.getJsonObjectFromJson(jsonObject, EndPointKeys.PAGINATION),
                                                    EndPointKeys.NEXT_MAX_ID);
            JSONArray dataJsonArray = JsonUtils.getJsonArrayFromJson(jsonObject, EndPointKeys.DATA);

            if (dataJsonArray == null) {
                return null;
            }

            for (int i = 0 ; i < dataJsonArray.length() ; i++) {
                JSONObject dataJson = dataJsonArray.getJSONObject(i);

                if (!JsonUtils.getStringFromJson(dataJson, EndPointKeys.TYPE).equals(EndPointKeys.IMAGE)) {
                    continue;
                }

                JSONObject imageJson = JsonUtils.getJsonObjectFromJson(dataJson, EndPointKeys.IMAGES);

                if (imageJson == null) {
                    continue;
                }

                JSONObject thumbnailObject = JsonUtils.getJsonObjectFromJson(imageJson, EndPointKeys.THUMBNAIL);
                JSONObject standardObject = JsonUtils.getJsonObjectFromJson(imageJson, EndPointKeys.STANDARD_RESOLUTION);

                String id = JsonUtils.getStringFromJson(dataJson, EndPointKeys.ID);
                String thumbnailUrl = JsonUtils.getStringFromJson(thumbnailObject, EndPointKeys.URL);
                String standardUrl = JsonUtils.getStringFromJson(standardObject, EndPointKeys.URL);

                int width = JsonUtils.getIntFromJson(standardObject, EndPointKeys.WIDTH);
                int height = JsonUtils.getIntFromJson(standardObject, EndPointKeys.HEIGHT);
                float ratio = (float) width / (float) height;
                long createdTime = JsonUtils.getLongFromJson(dataJson, EndPointKeys.CREATED_TIME);
                int likeCount = dataJson.getJSONObject(EndPointKeys.LIKES).getInt(EndPointKeys.COUNT);

                imageList.add(new IGImage(id, thumbnailUrl, standardUrl, ratio, createdTime, likeCount));
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return new IGRecentMedia(nextMaxId, imageList);
    }

    @Override
    protected void onPostExecute(IGRecentMedia igRecentMedia) {
        super.onPostExecute(igRecentMedia);

        if (igRecentMedia == null) {
            mOnGetRecentMediaListener.onGetRecentMediaFailed();

        } else {
            mOnGetRecentMediaListener.onGetRecentMediaSuccessful(igRecentMedia);
        }
    }
}