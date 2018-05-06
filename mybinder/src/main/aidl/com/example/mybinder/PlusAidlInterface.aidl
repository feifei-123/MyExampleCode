// PlusAidlInterface.aidl
package com.example.mybinder;

import com.example.mybinder.User;
// Declare any non-default types here with import statements

interface PlusAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

     int plus(int a,int b);

    String evalate(in User user);
}
