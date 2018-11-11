package com.example.myndk;

public class Food {
    static {
        System.loadLibrary("jnifood");
    }

    //用于存储C++层的对象指针
    private int mObject;

    public Food(String name,double price){

    }

//    public native void setFoodParam(String name,double price);
//    public native String getName();
//    public native String getPrice();
//    protected native void finalize();



}
