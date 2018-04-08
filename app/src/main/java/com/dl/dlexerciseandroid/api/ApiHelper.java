package com.dl.dlexerciseandroid.api;

import com.dl.dlexerciseandroid.api.instagramapi.IGAccessTokenApi;
import com.dl.dlexerciseandroid.api.instagramapi.IGApi;
import com.dl.dlexerciseandroid.api.stackoverflow.SOApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by logicmelody on 2017/11/3.
 */

public class ApiHelper {

    public static SOApi generateSOApi() {
        return generateRetrofit(SOApi.BASE_URL).create(SOApi.class);
    }

    public static IGAccessTokenApi generateIGAccessTokenApi() {
        return generateRetrofit(IGAccessTokenApi.BASE_URL).create(IGAccessTokenApi.class);
    }

    public static IGApi generateIGApi() {
        return generateRetrofit(IGApi.BASE_URL).create(IGApi.class);
    }

    // 如果要整合Retrofit跟RxJava，必須要在build.gradle中加入這個library：
    // com.squareup.retrofit2:adapter-rxjava2:2.3.0
    //
    // 並且在生成Retrofit物件的時候，必須要加這一行：
    // addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    private static Retrofit generateRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
