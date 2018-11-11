package com.example.myndk;

public class JNIUtils {

    static {
        System.loadLibrary("hello-lib");
    }

    public native String stringFromJNI();

    public native void writeFile(String filePath);

    public native void operationArray(int[]args);
    public native void operationArray2(int[]args);

    public native void initSDK();

    public native void releaseSDK();

    public native long natvieLock();
    public native int getAge(long natvieId);


}
