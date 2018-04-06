package com.dl.dlexerciseandroid.backgroundtask.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.dl.dlexerciseandroid.utility.utility.HttpUtils;
import com.dl.dlexerciseandroid.utility.utility.TmdbApiUtils;

import java.io.IOException;

/**
 * Created by logicmelody on 2016/7/29.
 */
public class TmdbApiService extends IntentService {

    private static final String TAG = TmdbApiService.class.getName();

    public static final class Actions {
        public static final String SEARCH_MOVIES_BY_TEXT = "com.dl.dlexerciseandroid.ACTION_SEARCH_MOVIES_BY_TEXT";
        public static final String GET_MOVIE_POSTERS = "com.dl.dlexerciseandroid.GET_MOVIE_POSTERS";
    }

    public static final class ExtraKeys {
        public static final String STRING_QUERY_TEXT = "com.dl.dlexerciseandroid.EXTRA_QUERY_TEXT";
        public static final String STRING_MOVIE_ID = "com.dl.dlexerciseandroid.EXTRA_MOVIE_ID";
    }


    public static Intent generateSearchMoviesByTextIntent(Context context, String queryText) {
        Intent intent = new Intent(context, TmdbApiService.class);
        intent.setAction(Actions.SEARCH_MOVIES_BY_TEXT);
        intent.putExtra(ExtraKeys.STRING_QUERY_TEXT, queryText);

        return intent;
    }

    public static Intent generateGetMoviePostersIntent(Context context, String movieId) {
        Intent intent = new Intent(context, TmdbApiService.class);
        intent.setAction(Actions.GET_MOVIE_POSTERS);
        intent.putExtra(ExtraKeys.STRING_MOVIE_ID, movieId);

        return intent;
    }

    public TmdbApiService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

        try {
            if (Actions.SEARCH_MOVIES_BY_TEXT.equals(action)) {
                searchMoviesByText(intent);

            } else if (Actions.GET_MOVIE_POSTERS.equals(action)) {
                getMoviePosters();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchMoviesByText(Intent intent) throws IOException {
        String queryText = intent.getStringExtra(ExtraKeys.STRING_QUERY_TEXT);

        if (TextUtils.isEmpty(queryText)) {
            return;
        }

        String url = TmdbApiUtils.getSearchMoviesByTextUrl(queryText);
        String jsonString = HttpUtils.getJsonStringFromUrl(url);

        Log.d("danny", "Movie search Url string = " + url);
        Log.d("danny", "Movie search Json string = " + jsonString);
    }

    private void getMoviePosters() {

    }
}
