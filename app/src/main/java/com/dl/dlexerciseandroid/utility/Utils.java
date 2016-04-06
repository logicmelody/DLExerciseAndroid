package com.dl.dlexerciseandroid.utility;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by logicmelody on 2016/3/30.
 */
public class Utils {

    public static final String URI_SPRING_WEB_SERVICE = "http://rest-service.guides.spring.io/greeting";

    public static final class FragmentTag {
        public static final String RIGHT_DRAWER = "com.dl.dlexerciseandroid.RIGHT_DRAWER_FRAGMENT";
        public static final String OVERVIEW = "com.dl.dlexerciseandroid.OVERVIEW_FRAGMENT";
        public static final String CONSUMING_RESTFUL_WEB_SERVICE = "com.dl.dlexerciseandroid.CONSUMING_RESTFUL_WEB_SERVICE_FRAGMENT";
        public static final String TEST = "com.dl.dlexerciseandroid.TEST_FRAGMENT";
    }

    public static void setStatusBarTranslucent(Activity activity, boolean makeTranslucent) {
        if (makeTranslucent) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
