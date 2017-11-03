package com.dl.dlexerciseandroid.utility.utils;

import com.dl.dlexerciseandroid.network.stackoverflow.SOApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by logicmelody on 2017/11/3.
 */

public class RetrofitUtils {

    public static SOApi generateSOApi() {
        return generateRetrofit(SOApi.BASE_URL).create(SOApi.class);
    }

    private static Retrofit generateRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
