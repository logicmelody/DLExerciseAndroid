package com.dl.dlexerciseandroid.network.instagramapi;

import com.dl.dlexerciseandroid.model.instagramapi.IGAccessTokenResponse;
import com.dl.dlexerciseandroid.model.instagramapi.IGUsersSelfResponse;
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

    String BASE_URL = "https://api.instagram.com/v1/";

    final class EndPoints {
        public static final String USERS_SELF = "users/self";
    }

    final class Queries {
        public static final String ACCESS_TOKEN = "access_token";
    }

    @GET(EndPoints.USERS_SELF)
    Observable<IGUsersSelfResponse> getUsersSelf(@Query(Queries.ACCESS_TOKEN) String token);
}
