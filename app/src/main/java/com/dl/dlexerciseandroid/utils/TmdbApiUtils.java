package com.dl.dlexerciseandroid.utils;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by logicmelody on 2016/7/29.
 */
public class TmdbApiUtils {

    private static String sBaseUrl = "https://api.themoviedb.org/3";
    private static String sApiKey = "?api_key=75f52adbad642f600178dacee90d3ec4";

    public static final class EndPoints {
        public static final String SEARCH_MOVIES = "/search/movie";
    }


    public static String getSearchMoviesByTextUrl(String queryText) {
        Map<String, String> map = new HashMap<>();
        map.put("query", queryText);

        return buildUrlString(EndPoints.SEARCH_MOVIES, map);
    }

    private static String buildUrlString(String endPoint, Map<String, String> queries) {
        StringBuilder result = new StringBuilder();
        String queryString = TmdbApiUtils.buildQueryString(queries);

        if (!TextUtils.isEmpty(queryString)) {
            result.append(sBaseUrl).append(endPoint).append(sApiKey).append(queryString);

        } else {
            result.append(sBaseUrl).append(endPoint);
        }

        return result.toString();
    }

    private static String buildQueryString(Map<String, String> queries) {
        if (queries == null || queries.size() == 0) {
            return "";
        }

        StringBuilder queryString = new StringBuilder();

        for (Map.Entry<String, String> entry : queries.entrySet()) {
            queryString.append("&")
                    .append(entry.getKey())
                    .append("=")
                    .append(entry.getValue());
        }

        return queryString.toString();
    }
}
