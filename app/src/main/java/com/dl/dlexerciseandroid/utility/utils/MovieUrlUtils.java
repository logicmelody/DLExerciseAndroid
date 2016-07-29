package com.dl.dlexerciseandroid.utility.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by logicmelody on 2016/7/29.
 */
public class MovieUrlUtils {

    private static String sBaseUrl = "https://api.themoviedb.org/3";
    private static String sApiKey = "?api_key=75f52adbad642f600178dacee90d3ec4";

    public static final class EndPoints {
        public static final String SEARCH_MOVIES = "/search/movie";
    }


    public static String searchMoviesByTextUrl(String queryText) {
        Map<String, String> map = new HashMap<>();
        map.put("query", queryText);

        return buildUrlString(EndPoints.SEARCH_MOVIES, map);
    }

    private static String buildUrlString(String endPoint, Map<String, String> queries) {
        StringBuilder result = new StringBuilder();
        String queryString = MovieUrlUtils.buildQueryString(queries);

        if (queryString.length() > 0) {
            result.append(sBaseUrl).append(endPoint).append(sApiKey).append(queryString);

        } else {
            result.append(sBaseUrl).append(endPoint);
        }

        return result.toString();
    }

    private static String buildQueryString(Map<String, String> queries) {
        if (queries == null) {
            return "";
        }

        StringBuilder queryString = new StringBuilder();

        Iterator iterator = queries.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            queryString.append("&")
                    .append(entry.getKey())
                    .append("=")
                    .append(entry.getValue());
        }

        return queryString.toString();
    }
}
