package com.sogou.teemo.testannotation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sogou.teemo.annotationlib.Test;

@Test(path = "main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
