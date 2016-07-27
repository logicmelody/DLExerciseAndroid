#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_dl_dlexerciseandroid_utility_utils_NdkUtils_getMsgFromJni(JNIEnv *env, jobject instance) {
    return (*env)->NewStringUTF(env, "Hello from Jni");
}