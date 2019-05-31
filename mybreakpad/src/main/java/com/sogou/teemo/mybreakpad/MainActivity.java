package com.sogou.teemo.mybreakpad;

import android.app.Activity;
import android.os.Bundle;

import com.online.breakpad.BreakpadInit;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BreakpadInit.initBreakpad("/sdcard/");
    }

    public native void nativeCrash();
}
