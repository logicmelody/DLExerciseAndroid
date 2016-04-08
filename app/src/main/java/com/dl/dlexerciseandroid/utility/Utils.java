package com.dl.dlexerciseandroid.utility;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by logicmelody on 2016/3/30.
 */
public class Utils {

    public static final String URI_SPRING_WEB_SERVICE = "http://rest-service.guides.spring.io/greeting";

    public static void setStatusBarTranslucent(Activity activity, boolean makeTranslucent) {
        if (makeTranslucent) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
