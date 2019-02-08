package com.example.testuiautomator;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;
import android.util.TimeUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    public static final String TAG = "ExampleInstrumentedTest";
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        Log.d(TAG,"useAppContext 测试 UiAutomator");
        assertEquals("com.example.testuiautomator", appContext.getPackageName());

    }


    @Test
    public void launcherTranslateMachine() throws RemoteException {

        //获取设备对象
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();//获得Instrument对象
        UiDevice uiDevice = UiDevice.getInstance(instrumentation);//获取device对象

        uiDevice.wakeUp();

        //获取上下文
        Context context = instrumentation.getContext();


        //启动被测试App
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.sogou.teemo.translate.launcher");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void TakeAPicture() throws RemoteException, UiObjectNotFoundException {
        launcherTranslateMachine();
        //获取设备对象
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();//获得Instrument对象
        UiDevice uiDevice = UiDevice.getInstance(instrumentation);//获取device对象

        uiDevice.wakeUp();

        Log.d(TAG,"wait - 拍照翻译 - start");
       boolean result =  uiDevice.wait(Until.hasObject(By.text("拍照翻译")), 10000);
       Log.d(TAG,"wait - 拍照翻译 end - result:"+result);

        UiObject object = uiDevice.findObject(new UiSelector().text("拍照翻译"));
        if(object != null && object.exists()){
            object.click();
        }else {
            Log.d(TAG,"拍照翻译 not find in the window");
        }

        Log.d(TAG,"wait - 平行于参考线拍摄 - start");
        boolean result1 =  uiDevice.wait(Until.hasObject(By.text("平行于参考线拍摄")), 10000);
        Log.d(TAG,"wait - 平行于参考线拍摄 - end - result:"+result);


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UiObject takePicture = uiDevice.findObject(new UiSelector().resourceId("com.sogou.teemo.translate.launcher:id/take_picture"));

        if(takePicture != null){
            Log.d(TAG,"takePicture:");
            takePicture.click();
        }
    }

    //批量拍照的自动化工具
    @Test
    public void startBatch() throws RemoteException, UiObjectNotFoundException, InterruptedException {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();//获得Instrument对象
        UiDevice uiDevice = UiDevice.getInstance(instrumentation);//获取device对象

        uiDevice.wakeUp();

        Log.d(TAG,"wait - batch - start");
        boolean result =  uiDevice.wait(Until.hasObject(By.text("batch")), 5000);
        Log.d(TAG,"wait - batch end - result:"+result);

       UiObject batchBtn = uiDevice.findObject(new UiSelector().text("batch").className("android.widget.TextView"));
       if(batchBtn != null && batchBtn.exists()){
           batchBtn.click();
       }

       while (true){
           Thread.sleep(1000);
//           boolean batchSuccess =  uiDevice.wait(Until.hasObject(By.text("batch")), 1000);
           boolean batchSuccess = false;
            UiObject batch = uiDevice.findObject(new UiSelector().text("batch"));
            if(batch !=  null && batch.exists()){
                batchSuccess = true;
            }else {
                batchSuccess = false;
            }

           if(batchSuccess == true){
               Log.d(TAG,"批量拍照 完成!");
               break;
           }else {
               Log.d(TAG,"批量拍照 正在进行中....");
           }

       }

    }
    @Test
    public void testA() throws UiObjectNotFoundException, InterruptedException, RemoteException {

        //获取设备对象
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();//获得Instrument对象
        UiDevice uiDevice = UiDevice.getInstance(instrumentation);//获取device对象

        uiDevice.wakeUp();

        //获取上下文
        Context context = instrumentation.getContext();


        //启动被测试App
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.yang.designsupportdemo");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        Log.d(TAG,"启动被测程序");

        //打开CollapsingToolbarLayout
        String resourceId = "com.yang.designsupportdemo:id/CollapsingToolbarLayout";
        UiObject collapsingToolbarLayout = uiDevice.findObject(new UiSelector().resourceId(resourceId));
        collapsingToolbarLayout.click();

//        Thread.sleep(1000);

        Log.d(TAG,"进入CollapsingToolbarLayout");
        for(int i = 0;i<5;i++){

            //向上移动

            int startX = uiDevice.getDisplayWidth() / 2;
            int startY = uiDevice.getDisplayHeight()/3;
            int endX = uiDevice.getDisplayWidth() / 2;
            int endY = uiDevice.getDisplayHeight() / 2;

            Log.d(TAG,"start :("+startX+","+startY+"),end:("+endX+","+endY+")");

            uiDevice.swipe(startX, startY, endX,endY, 10);
            uiDevice.swipe(startX, startY, endX,endY, 10);
            uiDevice.swipe(startX, startY, endX,endY, 10);
            Log.d(TAG,"上滑");

//            Thread.sleep(1000);
            uiDevice.swipe(endX, endY,
                    startX, startY, 10);
            uiDevice.swipe(endX, endY,
                    startX, startY, 10);
            uiDevice.swipe(endX, endY,
                    startX, startY, 10);
            Log.d(TAG,"下滑");


        }

        //点击应用返回按钮
        UiObject back = uiDevice.findObject(new UiSelector().description(("Navigate up")));
//        back.click();

        //点击设备返回按钮
        uiDevice.pressBack();

    }
//
//    public void testB() throws RemoteException, UiObjectNotFoundException {
//        //获取设备对象
//        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();//获得Instrument对象
//        UiDevice uiDevice = UiDevice.getInstance(instrumentation);//获取device对象
//
//        uiDevice.wakeUp();
//        uiDevice.pressHome();
//
//        UiObject settingsApp = new UiObject(new UiSelector().text("Settings"));
//        settingsApp.click();
//
//        //等待3秒
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }
//
//        //用滚动的方式查找并进入“语言和输入法设置”菜单
//        UiScrollable settingItems = new UiScrollable(
//                new UiSelector().scrollable(true));
//
//
//        UiObject languageAndInputItem = settingItems.getChildByText(new UiSelector().text("Language & input"), "Language & input", true);
//
//        languageAndInputItem.clickAndWaitForNewWindow();
//
//
//        //找到“English”的可点击项（因为当前是英文环境）
//
//        UiObject setLanItem = new UiObject(new UiSelector().text("English"));
//        setLanItem.clickAndWaitForNewWindow();
//
//
//
//        System.out.println("setLanItem-->" + setLanItem.getPackageName());
//
//
//        uiDevice.click(350, 250);
//
//        uiDevice.pressBack();
//
//        uiDevice.pressBack();
//
//        uiDevice.pressBack();
//
//    }

}
