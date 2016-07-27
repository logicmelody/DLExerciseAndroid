package com.dl.dlexerciseandroid.utility.utils;

/**
 * Created by logicmelody on 2016/7/27.
 */
public class NdkUtils {

    static {
        // Library的名稱記得要跟build.gradle中的一樣
        System.loadLibrary("dl-exercise-jni");
    }

    public static native String getMsgFromJni();
}
