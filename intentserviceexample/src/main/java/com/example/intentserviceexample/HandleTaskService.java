package com.example.intentserviceexample;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class HandleTaskService extends IntentService {
    private static final String TAG = HandleTaskService.class.getSimpleName();

    /**
     * 单线程 任务队列中，需要处理任务的方法（子线程执行）
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        String op = intent.getStringExtra("op");
        int param = intent.getIntExtra("param",-1);

        Log.d(TAG,"onHandleIntent - op:"+op+"_"+param+",Thread:"+Thread.currentThread().getName());

        try {
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }

        Log.d(TAG,"onHandleIntent task "+op+"_"+param+",finish"+",Thread:"+Thread.currentThread().getName());

    }

    /**
     * 无参构造方法
     */
    public  HandleTaskService(){
        super("HandleTaskService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreage()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy()");
    }
}
