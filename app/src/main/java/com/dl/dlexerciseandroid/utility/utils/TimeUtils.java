package com.dl.dlexerciseandroid.utility.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by dannylin on 2016/12/12.
 */

public class TimeUtils {

    public static final long MIN_MILLISEC = 60000;
    public static final long HOUR_MILLISEC = 3600000;

    public static final class Format {
        public static final String HMMA = "h:mm a";
        public static final String EEEEMMMMD = "EEEE, MMMM d";
    }


    public static String timeToString(long milliseconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds);

        return SimpleDateFormat.getDateTimeInstance().format(c.getTime());
    }

    public static String timeToStringHMMA(long milliseconds) {
        return timeToString(milliseconds, Format.HMMA);
    }

    public static String timeToStringEEEEMMMMD(long milliseconds) {
        return timeToString(milliseconds, Format.EEEEMMMMD);
    }

    private static String timeToString(long milliseconds, String format) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds);

        return new SimpleDateFormat(format).format(c.getTime());
    }
}
