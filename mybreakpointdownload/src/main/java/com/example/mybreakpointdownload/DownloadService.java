package com.example.mybreakpointdownload;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.mybreakpointdownload.bean.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadService extends Service {


    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String DownloadPath = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/download/";

    private DownloadTask mTask = null;

    public static final int MSG_INIT = 0;


    // 從InitThread綫程中獲取FileInfo信息，然後開始下載任務
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.i("feifei", "INIT:" + fileInfo.toString());
                    // 獲取FileInfo對象，開始下載任務
                    mTask = new DownloadTask(DownloadService.this, fileInfo);
                    mTask.download();
                    break;
            }
        };
    };


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ACTION_START.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.i("feifei", "START" + fileInfo.toString());
            new InitThread(fileInfo).start();
        } else if (ACTION_STOP.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.i("feifei", "STOP" + fileInfo.toString());
            if (mTask != null) {
                mTask.mIsPause = true;
            }
        }

        return super.onStartCommand(intent, flags, startId);

    }

    public class InitThread extends Thread {
        private FileInfo mFileInfo = null;

        public InitThread(FileInfo mFileInfo) {
            super();
            this.mFileInfo = mFileInfo;
        }

        @Override
        public void run(){
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;


            try {
                URL url = new URL(mFileInfo.getUrl());
                conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5*1000);
                conn.setRequestMethod("GET");
                int code = conn.getResponseCode();
                int length = -1;
                if(code == HttpURLConnection.HTTP_OK){
                    length = conn.getContentLength();
                }

                if(length <=0){
                    return;
                }

                //创建目录
                File dir = new File(DownloadPath);
                if(!dir.exists()){
                    dir.mkdirs();
                }

                //创建本地目录
                File file = new File(dir,mFileInfo.getFileName());
                raf = new RandomAccessFile(file,"rwd");
                raf.setLength(length);

                //设置文件最大长度
                mFileInfo.setLength(length);

                Message msg = Message.obtain();
                msg.obj = mFileInfo;
                msg.what = MSG_INIT;
                mHandler.sendMessage(msg);




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(conn != null){
                    conn.disconnect();
                }

                try {
                    if(raf != null){
                        raf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
