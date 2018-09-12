package com.example.myclassloader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.feifei.testclassloader.MyInterface;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void testClassloader(){


    }

    public void testClassLoader1(){
        ClassLoader classLoader = getClassLoader();
        if(classLoader != null){
            Log.d(TAG,"[onCreate] classLoader "+":"+classLoader.toString());
            while (classLoader.getParent() != null){
                classLoader = classLoader.getParent();
                Log.d(TAG,"[onCreate ] classloader "+":"+classLoader.toString());
            }
        }

    }

    public void testDexClassLoader_reflect(){

        //外部的未安装的apk(dex、jar)路径
        String dexPath = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"app-debug.apk";
        //临时内部路径（用于存放提取出来的dex）
        File optimizedDirectory = this.getDir("dex1",0);
        //创建DexClassLoader
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath,optimizedDirectory.getAbsolutePath(),null,getClassLoader());

        Class libClazz = null;

        try {
            //加载第三方类
            libClazz = dexClassLoader.loadClass("com.example.feifei.testclassloader.MyUtils");


            //通过反射方式调用第三方类
            Method[] methods = libClazz.getDeclaredMethods();
            for(int i = 0;i<methods.length;i++){
                Log.e(TAG,methods[i].toString());
            }


            //setName方法
            Method setName = libClazz.getDeclaredMethod("setName",String.class);
            Object libObject = libClazz.newInstance();
            setName.invoke(libObject,"nancy");

            //Debug方法
            Method debug = libClazz.getDeclaredMethod("debug");
            debug.setAccessible(true);
            String result = (String)debug.invoke(libObject);
            Log.d(TAG,"debug 输出结果:"+result);
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void testDexClassLoader_interface(){
        //外部的未安装的apk(dex、jar)路径
        String dexPath = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"app-debug.apk";
        //临时内部路径（用于存放提取出来的dex）
        File optimizedDirectory = this.getDir("dex1",0);
        //创建DexClassLoader
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath,optimizedDirectory.getAbsolutePath(),null,getClassLoader());

        Class libClazz = null;

        try {
            //加载第三方类
            libClazz = dexClassLoader.loadClass("com.example.feifei.testclassloader.MyUtils");


            Object libObject = libClazz.newInstance();
            //通过接口的方式 调用第三方类的方法
            MyInterface myInterface = (MyInterface)libObject;
            String result = myInterface.debug();
            Log.d(TAG,"debug 输出结果:"+result);
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
//        testDexClassLoader_reflect();
        testDexClassLoader_interface();
    }
}
