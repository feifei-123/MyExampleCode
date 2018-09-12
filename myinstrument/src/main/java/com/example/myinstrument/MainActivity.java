package com.example.myinstrument;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        intent.setClassName("com.example.myinstrument",SecondActivity.class.getSimpleName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


    }
}
