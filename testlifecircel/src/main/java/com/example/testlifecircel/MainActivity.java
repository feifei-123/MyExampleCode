package com.example.testlifecircel;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MyText myText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myText = (MyText) findViewById(R.id.my_text);
        getLifecycle().addObserver(myText);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        getLifecycle().removeObserver(myText);


    }


}
