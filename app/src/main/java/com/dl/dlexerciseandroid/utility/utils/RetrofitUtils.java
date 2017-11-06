package com.dl.dlexerciseandroid.utility.utils;

import com.dl.dlexerciseandroid.network.stackoverflow.SOApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by logicmelody on 2017/11/3.
 */

public class RetrofitUtils {

    public static SOApi generateSOApi() {
        return generateRetrofit(SOApi.BASE_URL).create(SOApi.class);
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
