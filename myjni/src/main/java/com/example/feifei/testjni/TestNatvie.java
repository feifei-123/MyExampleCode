package com.example.feifei.testjni;

import android.util.Log;

public class TestNatvie {
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * 用户保存 C++对象的引用
     */
    private long mNatvieId;


    /**
     * Java 对象持有 C++对象
     */
    public native void initSDK();

    /**
     * Java 对象释放 C++对象
     */
    public native void releasSDK();



    /**
     * C++对象中 引用Java对象的示例
     *
     * 该方法返回 C++对象的引用
     */
    public native long putJava2CObjet();

    /**
     *  释放 C++对象中 引用的Java对象
     *
     *  该方法传入 C++对象的引用，供内部使用
     */
    public native void releaseJavaOInCObject(long c_Obj);


    /**
     * C/C++ 字符串转换成JNI 字符串
     * @return
     */
    public native String char2String();


    /**
     * String 转换成 C/C++ Char*
     */
    public native void String2Char(String str);

    /**
     * 字符串相关测试代码
     * @param str
     */
    public native void testJstring(String str);

    /**
     * 整形数组相关代码
     * @param array
     */
    public native void testIntArray(int []array);

    /**
     *
     * Object Array 相关测试 代码
     * @param strArr
     */
    public native void testObjectArray(String[]strArr);

    public native Object testNIO();

    /**
     * Jni调用 java 对象方法
     */
    public native void testCallJavaMethod();

    /**
     * Jni 调用 java static 方法
     */
    public native void testCallStaticJavaMethod();

    /**
     * JNI 访问 java 的对象属性和类属性
     * @param student
     */
    public native void getJavaObjectField(Student student);


    /**
     * JNI 利用 java 对象进行线程同步
     * @param lock
     */
    public native void testJNILock(Object lock);


    /**
     *  1、env->ExceptionOccurred() 判断JNI调用java方法 是否遇到了Exception
     *  2、env->ThrowNew() JNI 可以主动抛出Java Exception异常
     */
    public native void testJavaException();

    /**
     * 利用JNI全局引用持有java 对象
     */
    public native void testJNIReference(Object object);



    public void helloworld(String msg){
        Log.d("feifei","hello world:"+msg);
    }

    public static void helloworldStatic(String msg){
        Log.d("feifei","hello world:"+msg);
    }

    public void helloException(){
        //throw  new NullPointerException("null point occurred ~");
    }


}
