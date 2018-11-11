package com.example.testlock;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.security.auth.callback.Callback;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    public void test2(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                doSomeThing("Thread-A");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                doSomeThing("Thread-B");
            }
        }).start();

    }

    ReentrantLock lock = new ReentrantLock();
    public void doSomeThing(String info){

        int i = 10;
        lock.lock();
        try {

            while (i>0){
                Log.d("feifei","doSomeThing:"+info);
                i--;
                Thread.sleep(100);
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.d("feifei","doSomeThing Exception:"+e.getLocalizedMessage());
        }finally {
            lock.unlock();
            Log.d("feifei","doSomeThing unlock");
        }
    }



    public void test(){
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        MutiThreadWait pa = new MutiThreadWait("A",c,a);
        MutiThreadWait pb = new MutiThreadWait("B",a,b);
        MutiThreadWait pc = new MutiThreadWait("C",b,c);

        new Thread(pa).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(pb).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(pc).start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
//        test();
        test2();
    }
}

class MutiThreadWait implements Runnable{

    private  String name;
    private Object pre;
    private Object now;

    public MutiThreadWait(String name,Object pre,Object now){
        this.name = name;
        this.pre = pre;
        this.now = now;
        Log.d("feifei","MutiThreadWait,name:"+name+",pre:"+pre+",now:"+now);
    }


    @Override
    public void run() {

        Log.d("feifei",name+" run ---");
        int count = 10;
        while (count > 0){
            synchronized (pre){

                synchronized (now){
                    System.out.println("feifei-"+name);
                    Log.d("feifei",name);
                    count --;
                    now.notify();
                }

                try{
                    pre.wait();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

        }
    }
}