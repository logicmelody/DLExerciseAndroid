package com.dl.dlexerciseandroid.utility.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by logicmelody on 2016/7/12.
 */
public class HttpUtils {

    private static final OkHttpClient sHttpClient = new OkHttpClient();


    public static String getJsonStringFromUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = sHttpClient.newCall(request).execute();

        return response.body().string();
    }

    public static Bitmap downloadBitmap(String uri) {
        Request request = new Request.Builder()
                .url(uri)
                .build();

        Response response = null;
        try {
            response = sHttpClient.newCall(request).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response == null ? null : BitmapFactory.decodeStream(response.body().byteStream());
    }
}
