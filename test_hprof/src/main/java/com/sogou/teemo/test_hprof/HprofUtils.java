package com.sogou.teemo.test_hprof;

import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HprofUtils {

//    public static boolean createDumpFile(Context context) {
//        boolean bool = false;
//        String filePath = "/sdcard/dump/";
//
//        File file = new File(filePath);
//        if(!file.exists()){
//            file.mkdirs();
//        }
//
//        String filename = System.currentTimeMillis()+".hprof";
//
//        String hprofPath = filePath+filename;
//       Log.d("feifei","filePath:"+hprofPath);
//        try {
//            Debug.dumpHprofData(hprofPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bool;
//    }

    public static boolean createDumpFile(Context context) {
        String LOG_PATH = "/dump.gc/";
        boolean bool = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ssss", Locale.getDefault());
        String createTime = sdf.format(new Date(System.currentTimeMillis()));
        String state = android.os.Environment.getExternalStorageState();

        // 判断SdCard是否存在并且是可用的
        if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
            File file = new File(Environment.getExternalStorageDirectory().getPath() + LOG_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }

            String hprofPath = file.getAbsolutePath();
            if (!hprofPath.endsWith("/")) {
                hprofPath += "/";
            }

            hprofPath += createTime + ".hprof";
            try {
                android.os.Debug.dumpHprofData(hprofPath);
                bool = true;
                Log.d("feifei", "create dumpfile done!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            bool = false;
            Log.d("feifei", "no sdcard!");
        }

        return bool;
    }
}
