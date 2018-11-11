package com.example.testlifecircel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyText extends android.support.v7.widget.AppCompatTextView implements LifecycleObserver {

    public static final String TAG = MyText.class.getSimpleName();
    public MyText(Context context) {
        super(context);
    }

    public MyText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create(){
        Log.d(TAG,"ON_CREATE");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        Log.d(TAG,"ON_RESUME");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        Log.d(TAG,"ON_PAUSE");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory(){
        Log.d(TAG,"ON_DESTROY");
    }
}
