package com.example.mythreadpool;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_stop_thread:
//                testThreadInterrupt();

//                testThreadInterrupt1();
                testThreadInterrupt2();
                break;
            case R.id.btn_threadpol_shutdown:
                testShutDownNow(100);
                break;

        }

    }


    public void testThreadInterrupt(){

        MyThread thread = new MyThread();
        thread.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG,"interrupt Thread");
        thread.shouldRun = false;
        thread.interrupt();

    }

    public class MyThread extends Thread{
        public volatile boolean shouldRun = true;
        @Override
        public void run() {

            Log.d(TAG,"MyThread start");
            while (shouldRun){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG,"MyThread running");
            }

            Log.d(TAG,"MyThread stopped");
        }
    }


    public void testThreadInterrupt1(){

        MyThreadIO thread = new MyThreadIO();
        thread.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG,"interrupt Thread");
        thread.shouldRun = false;

        try {
            thread.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public class MyThreadIO extends Thread{
        public volatile boolean shouldRun = true;
        public volatile ServerSocket socket;
        @Override
        public void run() {

            try {
                socket = new ServerSocket(7856);
            } catch ( IOException e ) {
                System.out.println( "Could not create the socket..." );
                return;
            }

            Log.d(TAG,"MyThread start");
            while (shouldRun){
                Log.d(TAG,"MyThread running");
                try {
                    Socket sock = socket.accept();
                } catch ( IOException e ) {
                    Log.d(TAG, "accept() failed or interrupted..." );
                }
            }
            Log.d(TAG,"MyThread stopped");
        }
    }

    public void testThreadInterrupt2(){

        synchronized (lock){
            MyThreadWait thread = new MyThreadWait();
            thread.start();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d(TAG,"interrupt Thread");
            thread.shouldRun = false;
            thread.interrupt();

            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    Object lock = new Object();
    public class MyThreadWait extends Thread{
        public boolean shouldRun = true;
        @Override
        public void run() {

            Log.d(TAG,"MyThreadWait run");
            while (shouldRun){
                synchronized (lock){
                    try {
                        Log.d(TAG,"before lock.wait");
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.d(TAG,"after lock.wait");
                }
            }
        }
    }

    public void testShutDown(int startNo)  {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
        executorService.execute(getTask(i + startNo));
        }
        Log.d(TAG,"testShutDownNow was called");
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"ShutDown finished");
    }
    public void testShutDownNow(int startNo)  {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            executorService.execute(getTask(i + startNo));
        }
        Log.d(TAG,"testShutDownNow was called");
        executorService.shutdownNow();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"ShutDown finished");
    }


    public static Runnable getTask(int threadNo){
        final Random random = new Random();
        final int no= threadNo;
        Runnable task = new Runnable(){
              @Override
            public void run() {


              try {
                Log.d(TAG,no + "--> running" );
                Thread.sleep(1000);
                  Log.d(TAG,no + "--> run finish" );
              } catch (InterruptedException e) {
                  Log.d(TAG,"thread " + no + " has error" + e);

              }


            }
        };
         return task;
    }
}
