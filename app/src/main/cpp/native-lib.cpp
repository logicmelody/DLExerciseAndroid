//
// Created by Danny Lin on 2017/7/1.
//

#include <jni.h>
#include <string>

// C++ syntax: Required to declare as extern "C" to prevent c++ compiler
// to mangle function names
extern "C" {
    JNIEXPORT jstring JNICALL
    Java_com_dl_dlexerciseandroid_utility_utils_NdkUtils_getTestStringFromJNI(JNIEnv *env,
                                                                              jclass type) {
        std::string hello = "Hello from native library";

        return env->NewStringUTF(hello.c_str());
    }
}