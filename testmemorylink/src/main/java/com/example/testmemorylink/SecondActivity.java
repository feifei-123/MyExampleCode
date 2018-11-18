package com.example.testmemorylink;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends Activity implements View.OnClickListener{

    private static Link linkInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if (linkInstance == null) {
            linkInstance = new SecondActivity.Link();
        }

        CommonUtils.getInstance(this);

//        new MyThread().start();
    }

    @Override
    public void onClick(View v) {
        finish();
    }


    class Link{
        public void dosomething(){

        }
    }


    class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
