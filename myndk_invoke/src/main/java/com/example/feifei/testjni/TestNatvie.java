package com.example.feifei.testjni;

public class TestNatvie {

    static {
        System.loadLibrary("native-lib");
    }
    public native long natvieLock();
    public native int getAge(long natvieId);
}
