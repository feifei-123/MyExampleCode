package com.example.testlifecircel;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyText1 extends android.support.v7.widget.AppCompatTextView implements DefaultLifecycleObserver{
    public MyText1(Context context) {
        super(context);
    }

    public MyText1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyText1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {

    }
}
