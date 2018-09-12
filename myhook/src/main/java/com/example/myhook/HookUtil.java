package com.example.myhook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Calendar;

public class HookUtil {

    private Class<?> proxyActivity;

    private Context context;

    public HookUtil(Class<?> proxyActivity, Context context) {
        this.proxyActivity = proxyActivity;
        this.context = context;
    }

    public void hookAms() {

        //一路反射，直到拿到IActivityManager的对象
        try {
            Class<?> ActivityManagerNativeClss = Class.forName("android.app.ActivityManagerNative");
            Field defaultFiled = ActivityManagerNativeClss.getDeclaredField("gDefault");
            defaultFiled.setAccessible(true);
            Object defaultValue = defaultFiled.get(null);//因为是静态属性,所以get(null) 不需要传递调用对象.
            //反射SingleTon
            Class<?> SingletonClass = Class.forName("android.util.Singleton");
            Field mInstance = SingletonClass.getDeclaredField("mInstance");
            mInstance.setAccessible(true);
            //到这里已经拿到ActivityManager对象
            Object iActivityManagerObject = mInstance.get(defaultValue);//获取 Singleton中的mInstance属性


            //开始动态代理，用代理对象替换掉真实的ActivityManager，瞒天过海 (动态代理 必须传入 interface,不能是class)
            Class<?> IActivityManagerIntercept = Class.forName("android.app.IActivityManager");

            AmsInvocationHandler handler = new AmsInvocationHandler(iActivityManagerObject);



            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{IActivityManagerIntercept}, handler);

            //现在替换掉这个对象
            mInstance.set(defaultValue, proxy);//将defaultValue这个对象的mInstance 属性赋值为 proxy.


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class AmsInvocationHandler implements InvocationHandler {

        private Object iActivityManagerObject;

        private AmsInvocationHandler(Object iActivityManagerObject) {
            this.iActivityManagerObject = iActivityManagerObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

//            Log.i("HookUtil", method.getName());
            //我要在这里搞点事情
            if ("startActivity".contains(method.getName())) {
                Log.e("feifei","HookUtil Activity已经开始启动");
                Log.e("feifei","HookUtil 小弟到此一游！！！");

                Intent intent = null;

                int index = 0;
                for(int i = 0; i< args.length;i++){
                    Object arg = args[i];
                    if(arg instanceof Intent){
                        intent = (Intent)(arg);
                        index = i;
                    }
                }


                //伪造一个代理的Intent，代理Intent启动的是ProxyActivity
                Intent proxyIntent = new Intent();
                ComponentName componentName = new ComponentName(context,proxyActivity);
                proxyIntent.setComponent(componentName);
                proxyIntent.putExtra("oldIntent",intent);


                args[index] = proxyIntent;

            }
            return method.invoke(iActivityManagerObject, args);
        }
    }
    public void hookSystemHandler() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            //获取主线程对象
            Object activityThread = currentActivityThreadMethod.invoke(null);
            //获取mH字段
            Field mH = activityThreadClass.getDeclaredField("mH");
            mH.setAccessible(true);
            //获取Handler
            Handler handler = (Handler) mH.get(activityThread);
            //获取原始的mCallBack字段
            Field mCallBack = Handler.class.getDeclaredField("mCallback");
            mCallBack.setAccessible(true);
            //这里设置了我们自己实现了接口的CallBack对象
            mCallBack.set(handler, new ActivityThreadHandlerCallback(handler)) ;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ActivityThreadHandlerCallback implements Handler.Callback {

        private Handler handler;

        private ActivityThreadHandlerCallback(Handler handler) {
            this.handler = handler;
        }

        @Override
        public boolean handleMessage(Message msg) {
            Log.i("feifei", "HookAmsUtil handleMessage");
            //替换之前的Intent
            if (msg.what ==100) {
                Log.i("feifei","HookAmsUtil lauchActivity");
                handleLauchActivity(msg);
            }

            handler.handleMessage(msg);
            return true;
        }

        private void handleLauchActivity(Message msg) {
            Object obj = msg.obj;//ActivityClientRecord
            try{
                Field intentField = obj.getClass().getDeclaredField("intent");
                intentField.setAccessible(true);
                Intent proxyInent = (Intent) intentField.get(obj);
                Intent realIntent = proxyInent.getParcelableExtra("oldIntent");
                if (realIntent != null) {
                    proxyInent.setComponent(realIntent.getComponent());
                }
            }catch (Exception e){
                Log.i("feifei","HookAmsUtil lauchActivity falied");
            }

        }
    }


    /**
     * hook view的onClick事件
     * @param view
     */

    public static void hookOnClick(View view){

        try {
            Class classView = Class.forName("android.view.View");
            Method listenerInfomethod = classView.getDeclaredMethod("getListenerInfo");
            if(!listenerInfomethod.isAccessible()){
                listenerInfomethod.setAccessible(true);
            }
            Object listenerInfoObject = listenerInfomethod.invoke(view);

            Class classListenerInfo = Class.forName("android.view.View$ListenerInfo");
            Field clickListenerFiled = classListenerInfo.getDeclaredField("mOnClickListener");
            if(!clickListenerFiled.isAccessible()){
                clickListenerFiled.setAccessible(true);
            }
            View.OnClickListener listener =  (View.OnClickListener)clickListenerFiled.get(listenerInfoObject);
            OnClickListenerProxy onClickProxy = new OnClickListenerProxy(listener);
            clickListenerFiled.set(listenerInfoObject,onClickProxy);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

   static class OnClickListenerProxy implements View.OnClickListener{
        public long lastClickTime = 0;
        public static final int CLICK_INTERVAL = 1000;

        public View.OnClickListener mlistener;
        OnClickListenerProxy(View.OnClickListener listener){
            mlistener = listener;
        }

        @Override
        public void onClick(View v) {
            long current = Calendar.getInstance().getTimeInMillis();;
            Log.d("feifei","_click  current:"+current+",lastClickTime:"+lastClickTime+",interval:"+(current - lastClickTime)+",view:"+v.hashCode());
            if(current - lastClickTime > CLICK_INTERVAL){
                Log.d("feifei","有效 _click:"+(current - lastClickTime));
                lastClickTime = current;
                if(mlistener != null){
                    mlistener.onClick(v);
                }
            }else {
                Log.d("feifei","无效 _click:"+(current - lastClickTime));
            }
        }
    }





    public void hookResources(Context context){
        Log.d("feifei","hookResources 到此一游");
        try {
            Class<?>activityTheadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityTheadClass.getDeclaredMethod("currentActivityThread");

            currentActivityThreadMethod.setAccessible(true);

            Object currentActivityThread = currentActivityThreadMethod.invoke(null);


            //获取Instrumention对象
            Field mInstrumentionField =activityTheadClass.getDeclaredField("mInstrumentation");
            mInstrumentionField.setAccessible(true);

            Instrumentation mInstrumentation = (Instrumentation) mInstrumentionField.get(currentActivityThread);

            //构建自己的MyInstrumentation;

            Instrumentation proxy = new MyInstrumentation(mInstrumentation,context);


            //移花接木

            mInstrumentionField.set(currentActivityThread,proxy);


        } catch (Exception e) {
            Log.d("feifei","hookResources Exceptiop:"+e.getLocalizedMessage());
            e.printStackTrace();
        }

    }
    class MyInstrumentation extends Instrumentation{

        public Instrumentation mInstrumentation;
        public Context mContext;

        public MyInstrumentation(Instrumentation mInstrumentation,Context context){
            this.mInstrumentation = mInstrumentation;
            this.mContext = context;
        }
        @Override
        public void callActivityOnCreate(Activity activity, Bundle icicle) {

                Log.d("feifei","callActivityOnCreate 到此一游");
            try {
                Field mBaseField = Activity.class.getSuperclass().getSuperclass().getDeclaredField("mBase");
                mBaseField.setAccessible(true);
                //得到上下文Context
                Context mBase = (Context) mBaseField.get(activity);


                Class<?> contextImplClass = Class.forName("android.app.ContextImpl");
                Field mResourcesField = contextImplClass.getDeclaredField("mResources");
                mResourcesField.setAccessible(true);

                String dexPath = Environment.getExternalStorageDirectory()+"hookedApp.apk";
                Log.d("feifei","callActivityOnCreate dexPath:"+dexPath);
                String defaultDexPath = activity.getApplicationContext().getPackageResourcePath();
                Log.d("feifei","callActivityOnCreate defaultDexPath:"+defaultDexPath);

                //创建一个AssetManager
                AssetManager assetManager = AssetManager.class.newInstance();
                Method addAssetPath = assetManager.getClass().getMethod("addAssetPath",String.class);

                addAssetPath.setAccessible(true);
                addAssetPath.invoke(assetManager,defaultDexPath);
                addAssetPath.invoke(assetManager,dexPath);


                Method  ensureStringBlocksMethod = AssetManager.class.getDeclaredMethod("ensureStringBlocks");
                ensureStringBlocksMethod.setAccessible(true);
                ensureStringBlocksMethod.invoke(assetManager);


                Resources superRes = activity.getResources();
                Resources resources = new Resources(assetManager,superRes.getDisplayMetrics(),superRes.getConfiguration());

                mResourcesField.set(mBase,resources);



            } catch (Exception e) {
                Log.d("feifei","callActivityOnCreate exception:"+e.getLocalizedMessage());
                e.printStackTrace();
            }


            super.callActivityOnCreate(activity, icicle);
        }
    }
}
