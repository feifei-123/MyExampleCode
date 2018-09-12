package com.example.testkotlin2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testInline(10,{
            Log.d("feifei","内联函数")
            mytoast("这是测试扩展", Toast.LENGTH_LONG)
        })


    }

    override fun onResume() {
        super.onResume()


        testSets();

        testInline();

    }

    inline fun testInline(times:Int,block:()->Unit){
        for(time in 1..times-1){
            block()
        }

    }

    fun testSets(){
        var datas = listOf(1,2,3,4,5)
        Log.d("feifei","dataSets:"+datas.toString())
        datas.map {
            it*2 }
        Log.d("feifei","dataSets*2:"+datas.toString())

        Lock

    }


    fun testInline(){
        var l = ReentrantLock()
        lock(l){
            Log.d("feifei","测试内联函数")
        }
    }


    inline fun <T> lock(lock: Lock, body:()->T):T{
        lock.lock();
        try{
            return body()
        }finally {
            lock.unlock();
        }
    }


}
