package com.example.myhook;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends Activity implements View.OnClickListener{

    View tv_go2second;
    TextView tv_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_go2second =  findViewById(R.id.tv_go2second);
        tv_go2second.setOnClickListener(this);
        tv_bg = findViewById(R.id.tv_whole_bg);

    }

    @Override
    protected void onResume() {
        super.onResume();
        HookUtil.hookOnClick(tv_go2second);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_go2second:{
                Log.d("feifei","_click 点击了按钮");
                try2runThirdAppActivity();
            }
                break;
            case R.id.tv_changebg:
                changeBg();
                break;
        }

    }

    public void try2StartUnRegisteredActivityInApp(){
        Intent intent = new Intent(this,NoDeclaredActivithy.class);
        startActivity(intent);
    }

    public void try2runThirdAppActivity(){

        String apkPath = Environment.getExternalStorageDirectory()+File.separator+"hookedApp.apk";
        String packageName = getUninstallApkPkgName(this,apkPath);
        File file = new File(apkPath);
        boolean exit = file.exists();
        Log.d("feifei","apkPath:"+apkPath+",exist:"+exit+",packageName:"+packageName);
        danamicLoadApk(apkPath,packageName);

        ComponentName componentName = new ComponentName("com.example.feifei.testhookedapp","com.example.feifei.testhookedapp.HookedActivity");

        Intent intent = new Intent();
        intent.setComponent(componentName);
        startActivity(intent);
    }


    /**
     * 加载第三方apk的图片资源，设置成自己的背景图
     */
    public void changeBg(){

        String apkPath = Environment.getExternalStorageDirectory()+File.separator+"appbg.apk";
        String packageName = getUninstallApkPkgName(this,apkPath);
        File file = new File(apkPath);
        boolean exit = file.exists();
        Log.d("feifei","apkPath:"+apkPath+",exist:"+exit+",packageName:"+packageName);
        danamicLoadApk(apkPath,packageName);
    }

    /**
     * 获取未安装apk的信息
     * @param context
     * @param pApkFilePath apk文件的path
     * @return
     */
    private String getUninstallApkPkgName(Context context, String pApkFilePath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = pm.getPackageArchiveInfo(pApkFilePath, PackageManager.GET_ACTIVITIES);
        if (pkgInfo != null) {
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            return appInfo.packageName;
        }
        return "";
    }


    public void danamicLoadApk(String pApkFilePath,String pApkPakcageName){
        File file = getDir("dex",Context.MODE_PRIVATE);
        DexClassLoader classLoader = new DexClassLoader(pApkFilePath,file.getAbsolutePath(),null,getClassLoader());

        try {
            Class<?> loadClazz = classLoader.loadClass(pApkPakcageName+".R$drawable");

            Field skinFiled = loadClazz.getDeclaredField("bg1");
            skinFiled.setAccessible(true);
            int resourceId = (int)skinFiled.get(null);//静态属性 直接获取,不用传入对象.

            Resources resources = createResources(pApkFilePath);
            if(resources != null){
                Drawable drawable = resources.getDrawable(resourceId);
                tv_bg.setBackground(drawable);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取AssetManager   用来加载插件资源
     * @param pFilePath  插件的路径
     * @return
     */
    private AssetManager createAssetManager(String pFilePath) {
        try {
            final AssetManager assetManager = AssetManager.class.newInstance();
            final Class<?> assetManagerClazz = Class.forName("android.content.res.AssetManager");
            final Method addAssetPathMethod = assetManagerClazz.getDeclaredMethod("addAssetPath", String.class);
            addAssetPathMethod.setAccessible(true);
            addAssetPathMethod.invoke(assetManager, pFilePath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //这个Resources就可以加载非宿主apk中的资源
    private Resources createResources(String pFilePath){
        final AssetManager assetManager = createAssetManager(pFilePath);
        Resources superRes = this.getResources();
        return new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
    }



}
