package com.example.myrxjava2;


import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TestScheduler {

    public static final String TAG = TestScheduler.class.getSimpleName();


    public void testScheduler(){
//     testScheduler5();
//        testScheduler6();
    }

    /**
     * Schedulers.io( )：
     * 用于IO密集型的操作，例如读写SD卡文件，查询数据库，访问网络等，具有线程缓存机制，在此调度器接收到任务后，先检查线程缓存池中，是否有空闲的线程，如果有，则复用，如果没有则创建新的线程，并加入到线程池中，如果每次都没有空闲线程使用，可以无上限的创建新线程。
     */
    public void testScheduler1() {

            Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {

                    for(int i=0;i<20;i++){
                        Log.d(TAG, "发送线程:" + Thread.currentThread().getName() + ",发送:" + i);
                        Thread.sleep(1000);
                        emitter.onNext(i);
                    }
                    emitter.onComplete();
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            Log.d(TAG, "接收线程:" + Thread.currentThread().getName() + ",---->:" + integer);
                        }
                    });
    }


    public void testScheduler3(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for(int i = 0;i< 2;i++){
                    Log.d(TAG,"发射线程:"+Thread.currentThread().getName());
                    Thread.sleep(1000);
                    e.onNext(i);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        Log.d(TAG,"处理线程:"+Thread.currentThread().getName()+"---> 处理:"+integer);
                        return integer+"_info";
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG,"处理线程:"+Thread.currentThread().getName()+"---> onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG,"接收线程:"+Thread.currentThread().getName()+"------> 接收:"+s);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * Schedulers.trampoline()
     * 在当前线程立即执行任务，如果当前线程有任务在执行，则会将其暂停，等插入进来的任务执行完之后，再将未完成的任务接着执行。
     */
    public void testScheduler5(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for(int i = 0;i< 5;i++){
                    Log.d(TAG,"发射线程:"+Thread.currentThread().getName()+",发送:"+i);
                    Thread.sleep(1000);
                    e.onNext(i);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline())//设置观察者在当前线程(Observable发送线程)中接收数据
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer tmp) throws Exception {
                Thread.sleep(2000);
                Log.d(TAG,"接收线程 ------->:"+Thread.currentThread().getName()+",接收:"+tmp);
            }
        });
    }

    /**
     * Schedulers.single() 指定在一个单例线程中 排队执行.
     * 拥有一个线程单例，所有的任务都在这一个线程中执行，当此线程中有任务执行时，其他任务将会按照先进先出的顺序依次执行。
     */
    public void testScheduler6(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for(int i = 0;i< 5;i++){
                    Log.d(TAG,"发射线程:"+Thread.currentThread().getName()+",发送:"+i);
                    Thread.sleep(1000);
                    e.onNext(i);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.single())
                .observeOn(Schedulers.single())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG,"接收线程 ------->:"+Thread.currentThread().getName()+",接收:"+integer);
                    }
                });
    }
}
