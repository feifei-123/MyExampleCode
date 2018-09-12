//
// Created by 飞飞 on 2018/6/12.
//

#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <android/log.h>

#define  LOG    "JNILOG" // 这个是自定义的LOG的TAG
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG,__VA_ARGS__) // 定义LOGD类型
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG,__VA_ARGS__) // 定义LOGI类型
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...)  __android_log_print(ANDROID_LOG_FATAL,LOG,__VA_ARGS__) // 定义LOGF类型


JNIEXPORT jstring JNICALL
Java_com_example_myndk_JNIUtils_stringFromJNI(JNIEnv *env, jobject instance) {

    // TODO
    LOGD("stringFromJNI was called");

    return (*env)->NewStringUTF(env, "Hello World");
}


JNIEXPORT void JNICALL
Java_com_example_myndk_JNIUtils_writeFile(JNIEnv *env, jobject instance, jstring filePath_) {
    const char *filePath = (*env)->GetStringUTFChars(env, filePath_, 0);

    // TODO
    FILE *file = fopen(filePath, "a+");

    char data[] = "I am a boy";
    int count = fwrite(data, strlen(data), 1, file);
    if (file != NULL) {
        fclose(file);
    }
    (*env)->ReleaseStringUTFChars(env, filePath_, filePath);
}

JNIEXPORT void JNICALL
Java_com_example_myndk_JNIUtils_operationArray(JNIEnv *env, jobject instance, jintArray args_) {
    jint *args = (*env)->GetIntArrayElements(env, args_, NULL);

    //获得数组指针
    jint *arr = (*env)->GetIntArrayElements(env,args_,NULL);

    //获得数组长度
    jint len = (*env)->GetArrayLength(env,args_);

    int i = 0;
    for(;i < len ;i++){
        ++arr[i];
    }

    //释放资源
    (*env)->ReleaseIntArrayElements(env, args_, args, 0);
}

JNIEXPORT void JNICALL
Java_com_example_myndk_JNIUtils_operationArray2(JNIEnv *env, jobject instance, jintArray args_) {
    jint *args = (*env)->GetIntArrayElements(env, args_, NULL);

    // TODO

    //声明一个native层的数组,用于拷贝原数组
    jint nativieArray[5];

    //将传入的jintArray数组 拷贝到nativeArray
    (*env)->GetIntArrayRegion(env,args_,0,5,nativieArray);
    int i = 0;
    for(;i<5;i++){
        nativieArray[i]+=5;
    }

    //将操作完成的结果拷贝回jintArray
    (*env)->SetIntArrayRegion(env,nativieArray,0,5,args_);

    (*env)->ReleaseIntArrayElements(env, args_, args, 0);
}