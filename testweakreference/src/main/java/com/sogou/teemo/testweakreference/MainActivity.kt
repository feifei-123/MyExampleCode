package com.sogou.teemo.testweakreference

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.lang.ref.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test()
//        test2()
    }

    fun test(){

        //weak1只有一个弱引用
        var queue:ReferenceQueue<Data> = ReferenceQueue()
        var weak1 = WeakReference<Data>(Data(100),queue)


        //fuck2 有一个强引用和一个弱引用
        var fuck2:Data? = Data(200)
        var weak2 = WeakReference<Data>(fuck2,queue)

        var i :Int = 1

        //list 用于验证OOM时 软应用会被释放
        var multList= mutableListOf<SoftReference<Data>>()

        Thread{
            while (true){
                var tmp = queue.poll()
                if(tmp != null){
                    Log.d("feifei","-------t2: something is recycled:"+tmp)

                }
            }
        }.start()



        while (true){
            if(i == 10){
                fuck2 = null
                Log.d("feifei","-----let fuck2 -> null")
            }

            if(i%5==0){
                Runtime.getRuntime().gc()
                Log.d("feifei","try gc")
            }

            if(i>15){
                //OOM内存溢出时,软引用会被GC释放
                Log.d("feifei",""+Runtime.getRuntime().freeMemory()+","+Runtime.getRuntime().maxMemory() +","+Runtime.getRuntime().totalMemory());
                var tmp = SoftReference(Data(ByteArray(Runtime.getRuntime().freeMemory().toInt())),queue)
                multList.add(tmp)
                Runtime.getRuntime().gc()
            }

            var data = weak1.get()
            if(data == null){
                Log.d("feifei","fuck1 hasBeen release")
            } else{
              Log.d("feifei","fuck1 living!")
            }

            var data2 = weak2.get()

            if(data2 == null){
                Log.d("feifei","fuck2 hasBeen release")
            } else{
                Log.d("feifei","fuck2 living!")
            }

            i++
            Thread.sleep(500)
        }
    }
}

class Data{
    var mVar:Int = 0
    var bitmap:ByteArray = ByteArray(0)

    constructor(d:Int){
        mVar = d
    }

    constructor(mData:ByteArray){
        bitmap = mData
    }
}


//fun test2(){
//    var obj:Object? = Object()
//    var queue = ReferenceQueue<Object>()
//    var pr:PhantomReference<Object>  = PhantomReference(obj!!,queue)
//    Log.d("feifei","pr.get():"+pr.get())
//    obj = null
//    System.gc()
//    Log.d("feifei","pr.get():"+pr.get())
//    var r :Reference<Object>? = queue.poll() as Reference<Object>?
//    if(r!= null){
//        Log.d("feifei","r was not null - 回收")
//    }
//
//}

