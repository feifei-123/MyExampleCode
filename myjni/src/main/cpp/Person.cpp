//
// Created by 飞飞 on 2018/11/7.
//

#include <iostream>
#include <malloc.h>
#include "Person.h"
#include <android/log.h>

#ifndef LOG
#define  LOG    "JNILOG" // 这个是自定义的LOG的TAG
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG,__VA_ARGS__) // 定义LOGD类型
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG,__VA_ARGS__) // 定义LOGI类型
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...)  __android_log_print(ANDROID_LOG_FATAL,LOG,__VA_ARGS__) // 定义LOGF类型
#endif

using namespace std;

Person::Person() {
    LOGD("feifei Person() was called");
}

void Person::setAge(int age) {
    this->age = age;
    LOGD("feifei setAge:%d",age);
}

int Person::getAge() {
    LOGD("feifei getAge:%d",this->age);
    return this->age;
}

Person::~Person() {

    LOGD("feifei ~Person() 析构函数 was called ");
}

void Person::initSDK() {
    p  = (int*)malloc(1024*1024*100);//解决方式，可以设置最开始的malloc分配空间为0
//        memset(p,0,1024*1024*20);
    LOGD("feifei initSDK was called :%x",p);
    p  = (int*)realloc(p,1024*1024*300);
}

void Person::releaseSDK() {
    LOGD("feifei releaseSDK was called:%x:",p);
    free(p);

}

