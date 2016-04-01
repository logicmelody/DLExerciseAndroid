package com.dl.dlexerciseandroid.utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by daz on 10/9/15.
 */
public class URIUtils {

    public static String buildURIString(String baseURL, HashMap<String, String> queries) {
        String queryString = URIUtils.buildQueryString(queries);

        if (queryString.length() > 0) {
            return baseURL + "?" + queryString;

        } else {
            return baseURL;
        }
    }

    private static String buildQueryString(HashMap<String, String> queries) {
        String queryString = "";

        if (queries != null) {
            Iterator iter = queries.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                queryString += entry.getKey() + "=" + entry.getValue();
                queryString += "&";
            }

            if (queryString.length() > 0) {
                queryString = queryString.substring(0, queryString.length() - 1);
            }
        }

        return queryString;
    }
}
