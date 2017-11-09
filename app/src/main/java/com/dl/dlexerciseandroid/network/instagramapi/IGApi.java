package com.dl.dlexerciseandroid.network.instagramapi;

import com.dl.dlexerciseandroid.model.instagramapi.IGRecentMediaResponse;
import com.dl.dlexerciseandroid.model.instagramapi.IGUsersSelfResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by logicmelody on 2017/11/7.
 */

public interface IGApi {

    String BASE_URL = "https://api.instagram.com/v1/";

    final class EndPoints {
        public static final String USERS_SELF = "users/self";
        public static final String RECENT_MEDIA = "users/{user}/media/recent";
    }

    final class Queries {
        public static final String ACCESS_TOKEN = "access_token";
        public static final String MAX_ID = "max_id";
        public static final String MIN_ID = "min_id";
        public static final String COUNT = "count";
    }

    /**
     * https://api.instagram.com/v1/users/self/?access_token={token}
     *
     * @param token
     * @return
     */
    @GET(EndPoints.USERS_SELF)
    Observable<IGUsersSelfResponse> getUsersSelf(@Query(Queries.ACCESS_TOKEN) String token);

    /**
     * 如果要讀取login user的recent media，請傳入"self"，別的user請輸入user-id
     *
     * https://api.instagram.com/v1//users/self/media/recent/?access_token={token}
     * https://api.instagram.com/v1//users/{user-id}/media/recent/?access_token={token}
     *
     * Map for query:
     * max_id, min_id, count
     *
     * @param user
     * @return
     */
    @GET(EndPoints.RECENT_MEDIA)
    Observable<IGRecentMediaResponse> getRecentMediaSelf(@Path("user") String user,
                                                   @Query(Queries.ACCESS_TOKEN) String token,
                                                   @Query(Queries.COUNT) String count);
}
