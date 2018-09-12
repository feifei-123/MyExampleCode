package com.example.mylock;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testLock();
//        testTryLock();
        testInterruptLock();
    }



    private Lock lock = new ReentrantLock();
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();

    public void testLock(){
        new Thread(){
            @Override
            public void run() {
                insert(Thread.currentThread());
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                insert(Thread.currentThread());
            }
        }.start();

    }


    public void insert(Thread thread){
        lock.lock();

        try{
            Log.d(TAG,thread.getName()+" 等到了锁");
            for(int i = 0;i< 10;i++){
                arrayList.add(i);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
            Log.d(TAG,thread.getName()+" 释放了锁");
        }
    }




    public void testTryLock(){
        new Thread(){
            @Override
            public void run() {
                Tryinsert(Thread.currentThread());
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                Tryinsert(Thread.currentThread());
            }
        }.start();

    }

    public void Tryinsert(Thread thread){
        if(lock.tryLock()){
            try{
                Log.d(TAG,thread.getName()+" 等到了锁");
                for(int i = 0;i< 10;i++){
                    arrayList.add(i);
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
                Log.d(TAG,thread.getName()+" 释放了锁");
            }
        }else {
            Log.d(TAG,thread.getName()+" 获取锁失败");
        }


    }


    public void testInterruptLock(){
       Thread thread1=  new Thread(){
            @Override
            public void run() {


                try {
                    InterruptLock(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d(TAG,getName()+" 被中断了");
                }
            }
        };
        thread1.start();

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                try {
                    InterruptLock(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d(TAG,getName()+" 被中断了");
                }
            }
        };
        thread2.start();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.interrupt();
    }



    public void InterruptLock(Thread thread) throws InterruptedException {
             lock.lockInterruptibly();
            try{
                Log.d(TAG,thread.getName()+" 等到了锁");
                long startTime = System.currentTimeMillis();
                for(    ;     ;) {
                    if(System.currentTimeMillis() - startTime >= Integer.MAX_VALUE){
                        break;
                    }

                    //插入数据
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
                Log.d(TAG,thread.getName()+" 释放了锁");
            }



    }



}
