package com.dl.dlexerciseandroid.utility.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by logicmelody on 2016/3/30.
 */
public class GeneralUtils {

    public static final String URI_SPRING_WEB_SERVICE = "http://rest-service.guides.spring.io/greeting";

    public static final class DataFormat {
        public static final String YYYYMMDDHHMM = "yyyy/MM/dd HH:mm";
    }

    private static int sNotificationId = 0;

    public static Animation sFadeInAnimation;
    public static Animation sFadeOutAnimation;


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

    public static String getExtensionFrom(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }

        int dotPosition = filePath.lastIndexOf(".");

        return dotPosition == -1 ? null : filePath.substring(dotPosition + 1, filePath.length());
    }

    public static int getNotificationId() {
        return sNotificationId++;
    }

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {

        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();

        android.hardware.Camera.getCameraInfo(cameraId, info);

        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;

        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror

        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }

        camera.setDisplayOrientation(result);
    }

    public static int[] getViewLocationOnScreen(View view) {
        int[] pos = new int[2];
        view.getLocationOnScreen(pos);

        return pos;
    }
}
