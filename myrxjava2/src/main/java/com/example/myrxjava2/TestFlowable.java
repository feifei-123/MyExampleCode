package com.example.myrxjava2;


import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TestFlowable {
    public static final String TAG = TestFlowable.class.getSimpleName();


    public void testFlowable(){
//        testFlowable1();

//        testFlowable2();

        testFlowable3();
    }

    /**
     * 验证 Observable 不支持背压
     */
    public void testFlowable1(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                int i = 0;
                while (true){
                    i++;
                    emitter.onNext(i);
                    Log.d(TAG,"发送线程 ----->:"+Thread.currentThread().getName()+",发送:"+i);
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Thread.sleep(5000);
                        Log.d(TAG,"接收线程 ----->:"+Thread.currentThread().getName()+",接收:"+integer);
                    }
                });
    }


    /**
     * Flowable处理背压的默认策略.
     * 在其构造方法中可以发现，其内部维护了一个缓存池SpscLinkedArrayQueue，其大小不限，此策略下，如果Flowable默认的异步缓存池满了，会通过此缓存池暂存数据，它与Observable的异步缓存池一样，可以无限制向里添加数据，不会抛出MissingBackpressureException异常，但会导致OOM。
     */

    public void  testFlowable2(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter ) throws Exception {


                Log.d(TAG,"发送---->1");
                emitter.onNext(1);
                Log.d(TAG,"发送---->2");
                emitter.onNext(2);
                Log.d(TAG,"发送---->3");
                emitter.onNext(3);
                Log.d(TAG,"发送---->4");
                emitter.onNext(4);
                emitter.onComplete();

            }
        },BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Integer.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG,"接收 ---> :"+integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"接收完成 ---> ");
                    }
                });
    }

    public void testFlowable3(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

                for(int i = 0;i< 229;i++){
                    emitter.onNext(i);
                    Log.d(TAG,"发送线程----->"+Thread.currentThread().getName()+",发送:"+i);
                    try {
                        Thread.sleep(100);//每隔100毫秒发射一次数据
                    } catch (Exception ignore) {
                    }
                }
                emitter.onComplete();
            }
        },BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Integer.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ignore) {
                        }
                        Log.d(TAG,"接收线程->>>>>"+Thread.currentThread().getName()+",接收:"+integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG,"接收线程->>>>>"+Thread.currentThread().getName()+",Exception:"+t.getLocalizedMessage());
                            t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
