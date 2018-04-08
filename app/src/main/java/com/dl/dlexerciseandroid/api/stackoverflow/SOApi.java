package com.dl.dlexerciseandroid.api.stackoverflow;

import com.dl.dlexerciseandroid.model.stackoverflow.SOAnswersResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by logicmelody on 2017/11/3.
 */

public interface SOApi {

    String BASE_URL = "https://api.stackexchange.com/2.2/";

    final class EndPoints {
        public static final String ANSWERS = "answers?order=desc&sort=activity&site=stackoverflow";
    }

    // Retrofit interface中的method都一定要有endpoint的url
    @GET(EndPoints.ANSWERS)
    Observable<SOAnswersResponse> getAnswers();

    // 這個method所產生的Url = answers?order=desc&sort=activity&site=stackoverflow&tagged=[tags]
    @GET(EndPoints.ANSWERS)
    Observable<SOAnswersResponse> getAnswers(@Query("tagged") String tags);
}
