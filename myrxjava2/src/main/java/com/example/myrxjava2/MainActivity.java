package com.example.myrxjava2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testFlowable();
    }

    public void testFlowable(){

    }

    @Override
    public void onClick(View v) {
//        TestObservable testObservable =  new TestObservable();
//        testObservable.testObservable();

//        TestScheduler testScheduler = new TestScheduler();
//        testScheduler.testScheduler();

        TestFlowable testFlowable = new TestFlowable();
        testFlowable.testFlowable();
    }
}
