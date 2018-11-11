package com.example.testkotlin2

import android.app.Activity
import android.app.UiAutomation
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

fun Activity.mytoast(message:String, duration:Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,message,duration).show()
}

fun <T> LifecycleOwner.load(loader:suspend ()->T):Deferred<T> {
    val deferred = async(context = Background, start = CoroutineStart.LAZY) {
        loader()
    }

    lifecycle.addObserver(CoroutineLifeCircleListener(deferred))
    return deferred;
}

infix fun <T> Deferred<T>.then(block:(T)->Unit):Job{
    val job = launch(UI) {
        block(this@then.await())
    }
    return job;
}

class CoroutineLifeCircleListener(val deferred: Deferred<*>): LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancelCoroutine(){
        Log.d("feifei","ON_DESTROY")
        if(!deferred.isCompleted){
            deferred.cancel()
            Log.d("feifei","cancel job")
        }
    }

}
internal val Background = newFixedThreadPoolContext(2, "bg")

