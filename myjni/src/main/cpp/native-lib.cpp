#include <jni.h>
#include <string>
#include <android/log.h>
#include<malloc.h>
#include "Person.h"


#ifndef LOG
#define  LOG    "JNILOG" // 这个是自定义的LOG的TAG
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG,__VA_ARGS__) // 定义LOGD类型
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG,__VA_ARGS__) // 定义LOGI类型
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...)  __android_log_print(ANDROID_LOG_FATAL,LOG,__VA_ARGS__) // 定义LOGF类型
#endif


//
//class Person{
//public:
//    int age;
//    int *p;
////    jintArray aa;
//public :
//    Person(){
//        p  = (int*)malloc(1024*1024*100);//解决方式，可以设置最开始的malloc分配空间为0
////        memset(p,0,1024*1024*20);
//        LOGD("feifei Person() was called 1 %x:",p);
//        p  = (int*)realloc(p,1024*1024*300);
////        memset(p,0,1024*1024*300);
////        LOGD("feifei Person() was called 2 %x:",p);
////        if (p1 != p){
////            free(p1);
////        }
////        p  = (int*)realloc(p,100*1024*50);
////        p  = (int*)realloc(p,1024*449); //设置这个值dirty才有变化，低于这个不行
////        LOGD("feifei Person() was called 2 :%x",p);
//    }
//
//
//    ~Person(){
//
//        LOGD("feifei ~Person() 析构函数 was called!:%x",p);
////        LOGD("feifei ~Person() 析构函数 was called - :%x",aa);
////        free(aa);
//        free(p);
//
//    }
//public:
//    int getAge(){
//        return this->age;
//    }
//
//    void setAge(int age){
//        this->age = age;
//    }
//};


extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_feifei_testjni_TestNatvie_natvieLock(JNIEnv *env, jobject instance) {
    Person *person = new Person();
    person->setAge(5);

    jintArray aa=env->NewIntArray(1024*1024*25) ;
//    env->NewGlobalRef(aa);
//    person->aa = aa;
    LOGD("feifei new Persion(), setAge(5)");
    return (jlong) person;
    // TODO

}extern "C"
JNIEXPORT jint JNICALL
Java_com_example_feifei_testjni_TestNatvie_getAge(JNIEnv *env, jobject instance, jlong natvieId) {
    Person * person = (Person*)natvieId;
    int age =  person->getAge();
    delete(person);

    LOGD("feifei new Persion(), getAge:%d",age);
    return age;
    // TODO

}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_feifei_testjni_TestNatvie_initSDK(JNIEnv *env, jobject instance) {

    Person * person = new Person();
    person->setAge(18);
    person->initSDK();

    jclass classzz = env->GetObjectClass(instance);
    jfieldID fid = env->GetFieldID(classzz,"mNatvieId","J");

    //将C++对象的地址绑定到Java变量中
    env->SetLongField(instance,fid,(jlong)person);


}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_feifei_testjni_TestNatvie_releasSDK(JNIEnv *env, jobject instance) {

    jclass objectClass = env->GetObjectClass(instance);
    jfieldID fid = env->GetFieldID(objectClass,"mNatvieId","J");

    //取出java对象中保存的C++对象地址
    jlong  p = env->GetLongField(instance,fid);

    //转换成 C++对象
    Person * person = (Person*)p;
    person->releaseSDK();
    //释放person C++对象
    free(person);
    env->SetLongField(instance,fid,-1);
}






extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_feifei_testjni_TestNatvie_putJava2CObjet(JNIEnv *env, jobject instance) {

    Person * person = new Person();

    //创建一个 全局引用
    jobject  gThiz = (jobject)env->NewGlobalRef(instance);

    //将java对象 持久化到 C++变量中
    person->mJavaObj = (jlong)gThiz;

    LOGD("feifei Hold Java Object in C++");
    if(person != NULL){
        return (jlong)person;
    } else{
        LOGE("new Person object error.");
        return jlong (1);
    }

}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_feifei_testjni_TestNatvie_releaseJavaOInCObject(JNIEnv *env, jobject instance,
                                                                 jlong c_Obj) {

    Person * person = (Person*)c_Obj;

    jobject  javaObj = (jobject)person->mJavaObj;

    //释放 全局引用
    env->DeleteGlobalRef(javaObj);

    if(person != NULL){
        free(person);
        person = NULL;
    }
    LOGD("feifei RELEASE Java Object in C++");
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_feifei_testjni_TestNatvie_char2String(JNIEnv *env, jobject instance) {

   char const * str = "hello world!";

    //生成JNI String
    jstring  jstring = env->NewStringUTF(str);

     jchar const * jchar2 = env->GetStringChars(jstring,NULL);//从jstring->jchar
    jsize len = env->GetStringLength(jstring);//获得jstring的长度
//    jstring jstr2 = env->NewString(jchar2,len);//有jchar -> 转换成 jstr2;


    return jstring;
}


extern "C"
JNIEXPORT void JNICALL
Java_com_example_feifei_testjni_TestNatvie_String2Char(JNIEnv *env, jobject instance,
                                                       jstring str_) {
    const char *str = env->GetStringUTFChars(str_, 0);

    env->ReleaseStringUTFChars(str_, str);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_feifei_testjni_TestNatvie_testJstring(JNIEnv *env, jobject instance,
                                                       jstring str_) {

  //（1）生成JNI String
    char const * str = "hello world!";
    jstring  jstring = env->NewStringUTF(str);

    // (2) jstring 转换成 const char * charstr
    const char *charstr = env->GetStringUTFChars(str_, 0);
    // (3) 释放 const char *
    env->ReleaseStringUTFChars(str_, charstr);

    //(4) 获取字符串子集
    char * subStr = new char;
    env->GetStringUTFRegion(str_,0,3,subStr);//截取字符串char*;


    env->ReleaseStringUTFChars(str_, subStr);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_feifei_testjni_TestNatvie_testIntArray(JNIEnv *env, jobject instance,
                                                     jintArray array_) {

    //----获取数组元素
    //(1)获取数组中元素
    jint * intArray = env->GetIntArrayElements(array_,NULL);

    int len = env->GetArrayLength(array_);//(2)获取数组长度

    LOGD("feifei len:%d",len);

    for(int i = 0; i < len;i++){
        jint item = intArray[i];
        LOGD("feifei item[%d]:%d",i,item);
    }

    env->ReleaseIntArrayElements(array_, intArray, 0);

    //----- 获取子数组
    jint *subArray = new jint;
    env->GetIntArrayRegion(array_,0,3,subArray);
    for(int i = 0;i<3;i++){
        subArray[i]= subArray[i]+5;
        LOGD("feifei subArray:[%d]:",subArray[i]);
    }

    //用子数组修改原数组元素
    env->SetIntArrayRegion(array_,0,3,subArray);

    env->ReleaseIntArrayElements(array_,subArray,0);//释放子数组元素


}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_feifei_testjni_TestNatvie_testObjectArray(JNIEnv *env, jobject instance,

                                                           jobjectArray strArr) {
    //获取数组长度
    int len = env->GetArrayLength(strArr);
    for(int i = 0;i< len;i++){
        //获取Object数组元素
        jstring item = (jstring)env->GetObjectArrayElement(strArr,i);

        const char * charStr = env->GetStringUTFChars(item, false);
        LOGD("feifei strArray item:%s",charStr);

        jstring jresult = env->NewStringUTF("HaHa");
        //设置Object数组元素
        env->SetObjectArrayElement(strArr,i,jresult);
        env->ReleaseStringUTFChars(item,charStr);
    }

}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_feifei_testjni_TestNatvie_testNIO(JNIEnv *env, jobject instance) {
    const char * data = "hello world";
    int len = strlen(data);
    jobject jobj = env->NewDirectByteBuffer((void*)data,len);
    long capicity = env->GetDirectBufferCapacity(jobj);
    char * data2 = (char*)env->GetDirectBufferAddress(jobj);
    LOGD("feifei - len:%d,capicity:%ld,data2:%s",len,capicity,data2);
    return jobj;


}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_feifei_testjni_TestNatvie_testCallJavaMethod(JNIEnv *env, jobject instance) {


    //获取类名
    jclass  clazz = env->GetObjectClass(instance);
    if(clazz == NULL) return;

    jmethodID  javaMethod = env->GetMethodID(clazz,"helloworld","(Ljava/lang/String;)V");
    if(javaMethod == NULL)return;
    const char * msg = "nancy";
    jstring  jmsg = env->NewStringUTF(msg);
    env->CallVoidMethod(instance,javaMethod,jmsg);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_feifei_testjni_TestNatvie_testCallStaticJavaMethod(JNIEnv *env, jobject instance) {

    //获取java类型
    jclass clazz = env->GetObjectClass(instance);
    if(clazz == NULL) return;
    jmethodID staticMethod = env->GetStaticMethodID(clazz,"helloworldStatic","(Ljava/lang/String;)V");
    if(staticMethod == NULL) return;

    jstring jmsg = env->NewStringUTF("wangfeng");
    env->CallStaticVoidMethod(clazz,staticMethod,jmsg);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_feifei_testjni_TestNatvie_getJavaObjectField(JNIEnv *env, jobject instance,
                                                              jobject student) {

    jclass  clazz = env->GetObjectClass(student);
    if(clazz == NULL )return;

    // 获取Object 实例属性
    jfieldID  nameId = env->GetFieldID(clazz,"name","Ljava/lang/String;");
    jstring jname = (jstring)env->GetObjectField(student,nameId);

    jfieldID  ageId = env->GetFieldID(clazz,"age","I");
    jint jage = env->GetIntField(student,ageId);

    const char * name = env->GetStringUTFChars(jname,false);
    env->ReleaseStringUTFChars(jname,name);


    //获取java 类属性:

    jfieldID  gradeId = env->GetStaticFieldID(clazz,"grade","I");
    jint  jgrade = env->GetStaticIntField(clazz,gradeId);

    jfieldID  nickeNameID = env->GetStaticFieldID(clazz,"nickname","Ljava/lang/String;");
    jstring  jnickname = (jstring)env->GetStaticObjectField(clazz,nickeNameID);

    const char * nickeName = env->GetStringUTFChars(jnickname, false);
    env->ReleaseStringUTFChars(jnickname,nickeName);

    LOGD("feifei name:%s,age:%d,grade:%d,nickname:%s",name,jage,jgrade,nickeName);

    //JNI 设置 java对象属性
    env->SetObjectField(student,nameId,env->NewStringUTF("张三"));
    //JNI 设置 java 类属性
    env->SetStaticObjectField(clazz,nickeNameID,env->NewStringUTF("小白"));
    jstring jnameNew = (jstring)env->GetObjectField(student,nameId);
    jstring jnickNameNew = (jstring)env->GetStaticObjectField(clazz,nickeNameID);

    const char * newName = env->GetStringUTFChars(jnameNew, false);
    const char *newNickName = env->GetStringUTFChars(jnickNameNew, false);

    env->ReleaseStringUTFChars(jnameNew,newName);
    env->ReleaseStringUTFChars(jnickNameNew,newName);
    LOGD("feifei after update name:%s,age:%d,grade:%d,nickname:%s",newName,jage,jgrade,newNickName);


}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_feifei_testjni_TestNatvie_testJNILock(JNIEnv *env, jobject instance,
                                                       jobject lock) {

    //加锁
    env->MonitorEnter(lock);

    //doSomething
    LOGD("feifei, this is in lock");

    //释放锁
    env->MonitorExit(lock);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_feifei_testjni_TestNatvie_testJavaException(JNIEnv *env, jobject instance) {

    jclass  clazz = env->GetObjectClass(instance);
    if(clazz == NULL) return;

    jmethodID helloException_method  = env->GetMethodID(clazz,"helloException","()V");
    if(helloException_method == NULL )return;
    env->CallVoidMethod(instance,helloException_method);
    if(env->ExceptionOccurred() != NULL){
//        env->ExceptionDescribe();
        env->ExceptionClear();
        LOGD("feifei,调用java 方法时 遇到了Exception");
        return;

    }
    LOGD("feifei,调用helloException 方法成功了!");

    LOGD("feifei,now JNI throw java exception - beging");
    jclass  expetionClazz = env->FindClass("java/lang/Exception");
    if(expetionClazz == NULL) return;
    env->ThrowNew(expetionClazz,"this is a exception");

}

/**
 * (1)在JNI接口函数中引用JNI对象的局部变量，都是对JNI对象的局部引用，一旦JNI接口函数返回，所有这些JNI对象都会被自动释放。不过我们也可以采用JNI代码提供的DeleteLocalRef函数来删除一个局部JNI对象引用
 * (2)对于JNI对象，绝对不能简单的声明一个全局变量，在JNI接口函数里面给这个全局变量赋值这么简单，一定要使用JNI代码提供的管理JNI对象的函数.
 *  JNI 全局引用分为两种: 一种全局引用,这种引用会阻止Java垃圾回收器回收JNI代码引用的对象；
 *  另一种是弱全局引用，这种全局引用不会阻止垃圾回收器回收JNI 代码引用的Java对象
 *  - NewGlobalRef用来创建强全局引用的JNI对象
 *  - DeleteGlobalRef用来删除强全局引用的JNI对象
 *  - NewWeakGlobalRef用来创建弱全局引用的JNI对象
 *  - DeleteWeakGlobalRef用来删除弱全局引用的JNI对象
 *  - IsSameObject用来判断两个JNI对象是否相同
 */

jobject  gThiz; //全局JNI对象引用
jobject  gWeakThiz;//全局JNI对象弱应用
extern "C"
JNIEXPORT void JNICALL
Java_com_example_feifei_testjni_TestNatvie_testJNIReference(JNIEnv *env, jobject instance,jobject obj) {


    //声明局部变量clazz
    jclass clazz = env->GetObjectClass(instance);

    //手动释放 局部变量 clazz ;DeleteLocalRef 也可不用手动调用，JNI方法返回之后，会自动释放局部JNI变量
    env->DeleteLocalRef(clazz);

    //---- 强全局变量
    gThiz = env->NewGlobalRef(obj);//生成全局的JNI 对象引用,这样生成的全局的JNI对象 才可以在其他函数中使用

    env->DeleteGlobalRef(gThiz);//在我们不需要gThis这个全局JNI对象应用时，可以将其删除。

    //---- 全局弱引用
    gWeakThiz = env->NewWeakGlobalRef(obj);//生成全局的JNI对象引用，这样生成的全局的JNI对象才可以在其它函数中使用

    if(env->IsSameObject(gWeakThiz,NULL)){
        LOGD("全局弱引用 已经被释放了");
    }

    //释放 全局弱应用对象
    env->DeleteWeakGlobalRef(gWeakThiz);

}