package com.dl.dlexerciseandroid.network.instagramapi;

import com.dl.dlexerciseandroid.model.instagramapi.gson.IGAccessTokenResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by logicmelody on 2017/11/7.
 */

public interface IGAccessTokenApi {

    String BASE_URL = "https://api.instagram.com/oauth/";

    final class EndPoints {
        public static final String ACCESS_TOKEN = "access_token";
    }

    // @FieldMap只能用在request body，比如@POST
    @FormUrlEncoded
    @POST(EndPoints.ACCESS_TOKEN)
    Observable<IGAccessTokenResponse> getAccessToken(@FieldMap Map<String, String> map);
}
