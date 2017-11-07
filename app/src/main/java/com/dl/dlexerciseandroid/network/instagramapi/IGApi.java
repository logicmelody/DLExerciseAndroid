package com.dl.dlexerciseandroid.network.instagramapi;

import com.dl.dlexerciseandroid.model.instagramapi.IGAccessTokenResponse;
import com.dl.dlexerciseandroid.model.stackoverflow.SOAnswersResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by logicmelody on 2017/11/7.
 */

public interface IGApi {

    String BASE_TOKEN_URL = "https://api.instagram.com/oauth/";

    final class EndPoints {
        public static final String ACCESS_TOKEN = "access_token";
    }

    @POST(EndPoints.ACCESS_TOKEN)
    Observable<IGAccessTokenResponse> getAccessToken(@FieldMap Map<String, String> map);
}
