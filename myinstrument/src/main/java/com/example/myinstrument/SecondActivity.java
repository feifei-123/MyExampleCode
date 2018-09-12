package com.example.myinstrument;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

public class SecondActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d("feifei","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("feifei","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("feifei","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("feifei","onPause");
    }

    @Override
    public void onClick(View v) {
        Log.d("feifei","btn_click onClick");
    }
}
