package com.dl.dlexerciseandroid.utility.utility;

/**
 * Created by logicmelody on 2017/7/1.
 */

// 我們建立一個utility的class，專門用來放跟Ndk有關的操作
public class OpenCVUtils {

    static {
        System.loadLibrary("opencv-lib");
        System.loadLibrary("opencv_java3");
    }

    // 定義好要call native library的Java methods
    public static native int[] getCannyImg(int[] a,int b,int c);
}
