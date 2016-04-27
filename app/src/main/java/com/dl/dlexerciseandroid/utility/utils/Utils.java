package com.dl.dlexerciseandroid.utility.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by logicmelody on 2016/3/30.
 */
public class Utils {

    public static final String URI_SPRING_WEB_SERVICE = "http://rest-service.guides.spring.io/greeting";

    public static final class DataFormat {
        public static final String YYYYMMDDHHMM = "yyyy/MM/dd HH:mm";
    }


    public static void setStatusBarTranslucent(Activity activity, boolean makeTranslucent) {
        if (makeTranslucent) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * Get default format time string
     *
     * @param milliseconds
     * @return
     */
    public static String timeToString(long milliseconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds);

        return SimpleDateFormat.getDateTimeInstance().format(c.getTime());
    }

    /**
     * Get format time string with the given format
     *
     * @param milliseconds
     * @return
     */
    public static String timeToString(long milliseconds, String format) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds);

        return new SimpleDateFormat(format).format(c.getTime());
    }

    public static void showToastInNonUIThread(Handler handler, final Context context, final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
