package com.example.intentserviceexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int i = 0;
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,HandleTaskService.class);
        intent.putExtra("op","delete");
        intent.putExtra("param",i++);
        startService(intent);
    }
}
