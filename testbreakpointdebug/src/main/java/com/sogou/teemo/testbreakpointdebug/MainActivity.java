package com.sogou.teemo.testbreakpointdebug;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.NumberFormat;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        test();
    }


    public void test(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                printInfo();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                printInfo();
            }
        }).start();

        testException();
    }

    public void printInfo(){
        for(int i = 0;i< 100;i++){
            Log.d("feifei","本次输出:"+i);
        }
    }

    public void testException(){
        String a = "12a";
        try{
            Integer.parseInt(a);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
