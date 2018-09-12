package com.example.myndk;

public class JNIUtils {

    static {
        System.loadLibrary("hello-lib");
    }

    public native String stringFromJNI();

    public native void writeFile(String filePath);

    public native void operationArray(int[]args);
    public native void operationArray2(int[]args);

}
