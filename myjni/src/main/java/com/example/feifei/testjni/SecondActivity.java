package com.example.feifei.testjni;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    protected void onResume() {
        super.onResume();
        long stamp = System.currentTimeMillis();
        Log.d("feifei","test timpe:"+(stamp-MainActivity.stamp));
    }
}
