package com.example.testleakcanary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void go2School(){
        Intent intent = new Intent(this,    TextActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        go2School();
    }
}
