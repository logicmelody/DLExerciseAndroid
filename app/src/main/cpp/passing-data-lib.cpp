//
// Created by Danny Lin on 2017/7/2.
//

#include <jni.h>

// C++ syntax: Required to declare as extern "C" to prevent c++ compiler
// to mangle function names
extern "C" {
    // 前兩個參數是native method產生就存在的，我們需要用到的參數只有第三個：jobject ndkData
    JNIEXPORT jint JNICALL
    Java_com_dl_dlexerciseandroid_utility_utils_NdkUtils_getNdkDataIdFromNative(JNIEnv *env,
                                                                                jclass type,
                                                                                jobject ndkData) {
        jclass cls = env->GetObjectClass(ndkData);
        jmethodID methodId = env->GetMethodID(cls, "getId", "()I");

        return env->CallIntMethod(ndkData, methodId);
    }
}
