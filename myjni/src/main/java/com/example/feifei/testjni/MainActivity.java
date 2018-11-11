package com.example.feifei.testjni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    // Used to load the 'native-lib' library on application startup.


    public Object mLock = new Object();
    public static long stamp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("feifei ","--- onCreate");

    }




    public long mNatvieObject;
    public TestNatvie testNatvie = new TestNatvie();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_initSDK:
               testNatvie.initSDK();
                break;

            case R.id.btn_releaseSDK:
                testNatvie.releasSDK();
                break;

            case R.id.btn_putJava2CObject:
                mNatvieObject = testNatvie.putJava2CObjet();
                break;
            case R.id.btn_releaseJavaInC:

                testNatvie.releaseJavaOInCObject(mNatvieObject);
                break;

            case R.id.btn_testStr:

                //testString();

                //testArray();

                //testNIO();

                //testCallJavaMethod();

                //testLock();

                testJavaException();
                break;
        }
    }

    public void testString(){
        testNatvie.testJstring("hello world~");
    }

    public void testArray(){
        int []array = {1,2,3,4,5,6,7,9,0,8};
        testNatvie.testIntArray(array);
        for(int i = 0;i<array.length;i++){
            Log.d("feifei","orginal array:["+i+"]:"+array[i]);
        }

        String[] example = {"Hello world","Nice to meet you"};
        testNatvie.testObjectArray(example);
        for(int i = 0;i< example.length;i++){
           String info = example[i];
           Log.d("feifei","String ["+i+"]:"+info);
        }
    }

    public void testNIO(){
        testNatvie.testNIO();
    }

    public void testCallJavaMethod(){
        //JNI 调用Java对象方法
        testNatvie.testCallJavaMethod();
        //JNI 调用Java静态方法
        testNatvie.testCallStaticJavaMethod();

        Student student = new Student();
        student.name = "小明";
        student.age = 18;
        Student.grade = 6;
        Student.nickname = "hi,fly";
        testNatvie.getJavaObjectField(student);

    }


    public void testLock(){
        testNatvie.testJNILock(mLock);
    }

    public void testJavaException(){

        try {
            testNatvie.testJavaException();
        }catch (Exception e){
            Log.e("feifei","find a exception:"+e.getLocalizedMessage());
            e.getLocalizedMessage();
        }

    }


}
