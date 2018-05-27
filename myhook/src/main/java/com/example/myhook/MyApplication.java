package com.example.myhook;

import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HookUtil hookUtil = new HookUtil(SecondActivity.class,this);
        hookUtil.hookAms();

    }
}
