package com.online.breakpad;


public class BreakpadInit {
    static {
        System.loadLibrary("breakpad-core");
    }

    public static void initBreakpad(String path){
        initBreakpadNative(path);
    }

    private static native void initBreakpadNative(String path);
    private static native void test();


}
