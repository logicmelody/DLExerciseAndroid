package com.dl.dlexerciseandroid.utility.utils;

/**
 * Created by logicmelody on 2017/7/1.
 */

// 我們建立一個utility的class，專門用來放跟Ndk有關的操作
public class NdkUtils {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    // 定義好要call native library的Java methods
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public static native String getTestStringFromJNI();
}
