package com.example.mywebview;

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

    @Override
    public void onClick(View v) {

         if(v.getId() == R.id.btn_webview){

             Intent intent  = new Intent(this, PaymentActivity.class);
             startActivity(intent);

         }   else if(v.getId() == R.id.btn_mediaplayer){

             Intent intent  = new Intent(this, MyVideoPlayActivity.class);
             startActivity(intent);

         }
    }
}
