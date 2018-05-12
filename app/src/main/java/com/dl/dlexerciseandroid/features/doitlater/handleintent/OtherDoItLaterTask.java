package com.dl.dlexerciseandroid.features.doitlater.handleintent;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.dl.dlexerciseandroid.widget.PackedString;
import com.dl.dlexerciseandroid.utils.DoItLaterUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by logicmelody on 2016/4/29.
 */
public class OtherDoItLaterTask extends DoItLaterTask {

    public OtherDoItLaterTask(Context context, Intent intent) {
        super(context, intent);
    }

    @Override
    public void retrieveIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            retrieveIntentAfterLollipop();

        } else {
            retrieveIntentBeforeLollipop();
        }
    }

    // Lollipop之後，getRunningTasks()這個method已經duplicate，只能回傳自己或是launcher的task，所以我們必須要用
    // RunningAppProcessInfo這個class來取代。
    // 但是因為從Lollipop之後，Android為了要確保安全性，不讓caller可以取得一些比較私人的資訊，
    // 所以替代方法RunningAppProcessInfo只能取得package name，無法取得class name
    private void retrieveIntentAfterLollipop() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        RunningAppProcessInfo topAppProcessInfo = am.getRunningAppProcesses().get(0);
        PackedString.Builder psBuilder = new PackedString.Builder();

        Uri mediaUri = mIntent.getParcelableExtra(Intent.EXTRA_STREAM);
        String webUri = "";
        String packageName = topAppProcessInfo.processName;
        String extraSubject = mIntent.getStringExtra(Intent.EXTRA_SUBJECT);
        String extraText = mIntent.getStringExtra(Intent.EXTRA_TEXT);
        String type = mIntent.getType();

        Log.d("danny", "Later package name = " + packageName);
        psBuilder.put(PackedString.Key.PACKAGE_NAME, packageName);

        // Class name
        // 無法取得class name

        // White list：Youtube, GooglePlay, GoogleMap, IMDB, Chrome, AsusBrowser
        // 或是
        // EXTRA_TEXT有http的網址出現
        if (!TextUtils.isEmpty(extraText)) {
            if (DoItLaterUtils.sWhiteList.contains(packageName) || extraText.contains("http"))
            webUri = getWebsiteAddress(extraText);
            psBuilder.put(PackedString.Key.ACTION, Intent.ACTION_VIEW);
            psBuilder.put(PackedString.Key.DATA, webUri);

            Log.d("danny", "Later data = " + webUri);

        } else if (mediaUri != null) {
            psBuilder.put(PackedString.Key.ACTION, Intent.ACTION_VIEW);
            psBuilder.put(PackedString.Key.DATA, mediaUri.toString());

            Log.d("danny", "Later data = " + mediaUri.toString());
        }

        psBuilder.put(PackedString.Key.TYPE, type);

        mTitle = getDoItLaterTitle(extraSubject, packageName);
        mDescription = getDoItLaterDescription(extraText, packageName);
        mLaterPackageName = packageName;
        mLaterCallback = psBuilder.toString();
        mTime = System.currentTimeMillis();
    }

    // Lollipop之前，我們可以用getRunningTasks()來取得現在畫面上顯示Activity的package name跟class name
    // Do It Later就是用這個方式來取得本來正在運行的app資訊，而且因為Do It Later是華碩自家的app，apk是放在priv-app中，
    // 所以就算在Lollipop之後，還是可以正常使用getRunningTasks()這個method
    private void retrieveIntentBeforeLollipop() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        RunningTaskInfo taskInfo = am.getRunningTasks(1).get(0);
        PackedString.Builder psBuilder = new PackedString.Builder();

        Uri mediaUri = mIntent.getParcelableExtra(Intent.EXTRA_STREAM);
        String webUri = "";
        String packageName = taskInfo.baseActivity.getPackageName();
        String className = taskInfo.baseActivity.getClassName();
        String extraSubject = mIntent.getStringExtra(Intent.EXTRA_SUBJECT);
        String extraText = mIntent.getStringExtra(Intent.EXTRA_TEXT);
        String type = mIntent.getType();

        Log.d("danny", "Later package name = " + packageName);
        Log.d("danny", "Later class name = " + className);

        psBuilder.put(PackedString.Key.PACKAGE_NAME, packageName);
        psBuilder.put(PackedString.Key.CLASS_NAME, className);

        // White list：Youtube, GooglePlay, GoogleMap, IMDB, Chrome, AsusBrowser
        // 或是
        // EXTRA_TEXT有http的網址出現
        if (!TextUtils.isEmpty(extraText)) {
            if (DoItLaterUtils.sWhiteList.contains(packageName) || extraText.contains("http"))
                webUri = getWebsiteAddress(extraText);
            psBuilder.put(PackedString.Key.ACTION, Intent.ACTION_VIEW);
            psBuilder.put(PackedString.Key.DATA, webUri);

            Log.d("danny", "Later data = " + webUri);

        } else if (mediaUri != null) {
            psBuilder.put(PackedString.Key.ACTION, Intent.ACTION_VIEW);
            psBuilder.put(PackedString.Key.DATA, mediaUri.toString());

            Log.d("danny", "Later data = " + mediaUri.toString());
        }

        psBuilder.put(PackedString.Key.TYPE, type);

        mTitle = getDoItLaterTitle(extraSubject, packageName);
        mDescription = getDoItLaterDescription(extraText, packageName);
        mLaterPackageName = packageName;
        mLaterCallback = psBuilder.toString();
        mTime = System.currentTimeMillis();
    }

    private String getDoItLaterTitle(String extraSubject, String packageName) {
        return TextUtils.isEmpty(extraSubject) ? getAppName(packageName) : extraSubject;
    }

    private String getAppName(String packageName) {
        String appName = packageName;
        PackageManager packageManager = mContext.getPackageManager();

        try {
            ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, 0);

            if (appInfo != null) {
                appName = packageManager.getApplicationLabel(appInfo).toString();
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return appName;
    }

    private String getDoItLaterDescription(String text, String packageName) {
        return TextUtils.isEmpty(text) ? "" : text + "\n" +
               getAppName(packageName);
    }

    private String getWebsiteAddress(String text) {
        String url = null;
        StringBuilder expression = new StringBuilder();

        expression
                .append("((?:(http|https|Http|Https|rtsp|Rtsp):")
                .append("\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)")
                .append("\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_")
                .append("\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)+")
                .append("((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.)+")
                // named host
                .append("(?:")
                // plus top level domain
                .append("(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])")
                .append("|(?:biz|b[abdefghijmnorstvwyz])")
                .append("|(?:cat|com|coop|c[acdfghiklmnoruvxyz])").append("|d[ejkmoz]")
                .append("|(?:edu|e[cegrstu])").append("|f[ijkmor]")
                .append("|(?:gov|g[abdefghilmnpqrstuwy])").append("|h[kmnrtu]")
                .append("|(?:info|int|i[delmnoqrst])").append("|(?:jobs|j[emop])")
                .append("|k[eghimnrwyz]").append("|l[abcikrstuvy]")
                .append("|[a-zA-Z0-9]*")
                .append("|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])")
                .append("|(?:name|net|n[acefgilopruz])").append("|(?:org|om)")
                .append("|(?:pro|p[aefghklmnrstwy])").append("|qa").append("|r[eouw]")
                .append("|s[abcdeghijklmnortuvyz]").append("|(?:tel|travel|t[cdfghjklmnoprtvwz])")
                .append("|u[agkmsyz]").append("|v[aceginu]")
                .append("|w[fs]")
                .append("|y[etu]")
                .append("|z[amw]))")
                .append("|(?:(?:25[0-5]|2[0-4]")
                // or ip address
                .append("[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]")
                .append("|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]")
                .append("[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}")
                .append("|[1-9][0-9]|[0-9])))").append("(?:\\:\\d{1,5})?)")
                // plus option port number
                .append("(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&\\=\\#\\~")
                // plus option query params
                .append("\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?")
                .append("(?:\\b|$)");
        Pattern pattern = Pattern.compile(expression.toString());
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            url = matcher.group();
        }

        return url;
    }
}
