package com.example.myhook;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HookUtil hookUtil = new HookUtil(ProxyActivity.class,this);
        hookUtil.hookSystemHandler();
        hookUtil.hookAms();
        hookUtil.hookResources(this);
        Log.d("feifei","hookUtil.hookAms()");
    }
}
