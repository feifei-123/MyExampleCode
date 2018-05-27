package com.example.mybreakpointdownload;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import com.example.mybreakpointdownload.bean.FileInfo;
import com.example.mybreakpointdownload.bean.ThreadInfo;
import com.example.mybreakpointdownload.db.ThreadDAO;
import com.example.mybreakpointdownload.db.ThreadDAOImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.transform.OutputKeys;

import static com.example.mybreakpointdownload.DownloadService.DownloadPath;

public class DownloadTask {
    private Context mComtext = null;
    private FileInfo mFileInfo = null;
    private ThreadDAO mDao = null;
    private long mFinished = 0;
    public boolean mIsPause = false;

    public DownloadTask(Context comtext, FileInfo fileInfo) {
        super();
        this.mComtext = comtext;
        this.mFileInfo = fileInfo;
        this.mDao = new ThreadDAOImpl(mComtext);
    }

    public void download(){
        List<ThreadInfo> list = mDao.queryTheads(mFileInfo.getUrl());
        ThreadInfo info = null;
        if (list.size() == 0) {
            info = new ThreadInfo(0, mFileInfo.getUrl(), 0, mFileInfo.getLength(), 0);
        }else{
            info= list.get(0);
        }
        Log.d("feifei","download:"+info.toString());
        new DownloadThread(info).start();
    }

    public class DownloadThread extends Thread{

        private ThreadInfo threadInfo = null;
        public DownloadThread(ThreadInfo info){
            super();
            this.threadInfo = info;
        }

        @Override
        public void run() {

            if(!mDao.isExists(threadInfo.getUrl(),threadInfo.getId())){
                mDao.insertThead(threadInfo);
                Log.d("feifei","DownloadThread insertThead:"+threadInfo.toString());
            }
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream is = null;

            try {
                URL url = new URL(mFileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5*1000);
                conn.setRequestMethod("GET");

                long start = threadInfo.getStart()+threadInfo.getFinished();
                conn.setRequestProperty("Rangle","bytes="+start+"-"+threadInfo.getEnd());

                File file = new File(DownloadPath, mFileInfo.getFileName());
                raf = new RandomAccessFile(file,"rwd");
                raf.seek(start);
                mFinished+=threadInfo.getFinished();

                int code = conn.getResponseCode();

                Log.d("feifei","DownloadThread startdownload - url:"+url+",start:"+start+",end:"+threadInfo.getEnd()+",responseCode:"+code);
                if(code == HttpURLConnection.HTTP_OK){
                  is = conn.getInputStream();
                    byte[]bt = new byte[1024];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while ((len = is.read(bt))!=-1){
                        raf.write(bt,0,len);
                        mFinished += len;
                        if(System.currentTimeMillis() - time > 500){
                            time = System.currentTimeMillis();

                            Intent intent = new Intent(DownloadService.ACTION_UPDATE);
                            intent.putExtra("finished", mFinished * 100 / mFileInfo.getLength());
                            Log.i("feifei", "下载进度:"+mFinished * 100 / mFileInfo.getLength() + "%");
                            // 發送廣播給Activity
                            mComtext.sendBroadcast(intent);
                        }

                        if(mIsPause){
                            mDao.updateThread(threadInfo.getUrl(), threadInfo.getId(), mFinished);
                            return;
                        }
                    }
                    // 下載完成后，刪除數據庫信息
                    mDao.deleteThead(threadInfo.getUrl(), threadInfo.getId());

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                conn.disconnect();
                try {
                    if(is != null){
                        is.close();
                    }
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
