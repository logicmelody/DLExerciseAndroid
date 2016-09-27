package com.dl.dlexerciseandroid.utility.utils;

/**
 * Created by logicmelody on 2016/7/27.
 */
public class NdkUtils {

    static {
        System.loadLibrary("dl-exercise-jni");
    }

    public static native String getMsgFromJni();
}
