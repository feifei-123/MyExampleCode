package com.example.myrxjava2;


import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class TestObservable {

    public static final String TAG = "TestObservable";

    public void testObservable(){
//        testObserable1();
//        testObserable2();
//          testObservable3();
//        testObservable4();
        testObservable5();
    }

    //Observable.create() 创建observable
    public void testObserable1(){

        //(1)创建一个Observable
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello world");
                emitter.onComplete();
            }
        });

        //(2)创建一个Observer

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"onSubscribe - Thread:"+Thread.currentThread().getName());
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,"onNext :"+s+",Thread:"+Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"onError :"+e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onComplete "+Thread.currentThread().getName());
            }
        };

        //(3)observer订阅创建一个Observable

        observable.subscribe(observer);


    }

    /**
     *   Observable.just() 创建observable 发送数据
     */

    public void testObserable2(){
        Observable.just("Hello world","World","Nice to meet you").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "接收到数据:"+s +",thread:"+ Thread.currentThread().getName());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG, "收到异常:" + throwable.getLocalizedMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.d(TAG,"收到 结束complete");
            }
        });
    }

    /**
     * 利用 Observable.create(ObservableOnSubscribe) 发送数组元素
     */
    public void testObservable3(){

        final List<String> array = new ArrayList<>();
        array.add("feifei");
        array.add("nancy");
        array.add("wangfeng");


        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try{
                    for(String str:array){
                        Log.d(TAG," 发送 :"+str);
                        emitter.onNext(str);
                    }
                    emitter.onComplete();
                }catch (Exception e){
                    emitter.onError(e);
                }finally {

                }
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"onSubscribe 开始订阅");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,"onNext 收到消息:"+s+"\n");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"onError 收到异常:"+e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onComplete 收到结束");
            }
        });

    }


    public void testObservable4(){
        List<String> list = new LinkedList<String>();
        list.add("A");
        list.add("B");
        list.add("C");

       Disposable disposable =  Observable.fromIterable(list)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG,"fromIterable - 收到数据:"+s);
                    }
                });
        disposable.dispose();
    }

    public void testObservable5(){

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for(int i = 0;i<10;i++){
                    Log.d(TAG,"feifei 发送:"+i);
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG,"feifei ------- 接收:"+integer);
                if(integer > 4){
                    disposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"feifei onError:"+e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"feifei onComplete:");
            }
        });
    }

}
