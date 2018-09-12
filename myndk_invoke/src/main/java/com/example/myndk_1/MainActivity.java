package com.example.myndk_1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myndk.JNIUtils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(new JNIUtils().stringFromJNI());
    }
}
