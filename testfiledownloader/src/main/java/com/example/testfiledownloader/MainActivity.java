package com.example.testfiledownloader;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{


    String[] BIG_FILE_URLS = {
            // 5m
            "http://mirror.internode.on.net/pub/test/5meg.test5",
            // 6m
            "http://download.chinaunix.net/down.php?id=10608&ResourceID=5267&site=1",
            // 8m
            "http://7xjww9.com1.z0.glb.clouddn.com/Hopetoun_falls.jpg",
            // 10m
            "http://dg.101.hk/1.rar",
            // 342m
            "http://180.153.105.144/dd.myapp.com/16891/E2F3DEBB12A049ED921C6257C5E9FB11.apk",
            // 10m
            "http://mirror.internode.on.net/pub/test/10meg.test4",
//            "http://mirror.internode.on.net/pub/test/10meg.test5",
            // 20m
            "http://www.pc6.com/down.asp?id=72873",
            // 22m
            "http://113.207.16.84/dd.myapp.com/16891/2E53C25B6BC55D3330AB85A1B7B57485.apk?mkey=5630b43973f537cf&f=cf87&fsname=com.htshuo.htsg_3.0.1_49.apk&asr=02f1&p=.apk",
            // 206m
            "http://down.tech.sina.com.cn/download/d_load.php?d_id=49535&down_id=1&ip=42.81.45.159"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_start_single:
                start_single();
                break;
            case R.id.tv_pause_single:
                pause_single();
                break;
            case R.id.tv_delete_single:
                delete_single();
                break;
            case R.id.tv_start_multi:
                start_multi();
                break;
            case R.id.tv_stop_multi:
                stop_multi();
                break;
            case R.id.tv_delete_all:
                deleteAllFile();
                break;
        }
    }

    BaseDownloadTask singleTask ;
    public int singleTaskId = 0;
    String apkUrl = "http://cdn.llsapp.com/android/LLS-v4.0-595-20160908-143200.apk";
    String singleFileSaveName = "liulishuo.apk";
    public String mSinglePath = FileDownloadUtils.getDefaultSaveRootPath()+ File.separator+"feifei_save"
            +File.separator+singleFileSaveName;;
    public String mSaveFolder = FileDownloadUtils.getDefaultSaveRootPath()+File.separator+"feifei_save";

    public void start_single(){

        String url = apkUrl;
        singleTask = FileDownloader.getImpl().create(url)
//                .setPath(mSinglePath,false)
                .setPath(mSinglePath,true)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                //.setTag()
        .setListener(new FileDownloadSampleListener(){
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.d("feifei","pending taskId:"+task.getId()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes);

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.d("feifei","progress taskId:"+task.getId()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes+",speed:"+task.getSpeed()+",task.status:"+task.getStatus());
            }

            @Override
            protected void blockComplete(BaseDownloadTask task) {
                Log.d("feifei","blockComplete taskId:"+task.getId()+",filePath:"+task.getPath()+",fileName:"+task.getFilename()+",speed:"+task.getSpeed()+",isReuse:"+task.reuse());
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Log.d("feifei","completed taskId:"+task.getId()+",isReuse:"+task.reuse());
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.d("feifei","paused taskId:"+task.getId()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes);
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                Log.d("feifei","error taskId:"+task.getId()+",e:"+e.getLocalizedMessage());
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                Log.d("feifei","warn taskId:"+task.getId());
            }
        });

        singleTaskId =  singleTask.start();

    }


    public void pause_single(){

        Log.d("feifei","pause_single task:"+singleTaskId);
        FileDownloader.getImpl().pause(singleTaskId);
    }

    public void delete_single(){

        //删除单个任务的database记录
        boolean deleteData =  FileDownloader.getImpl().clear(singleTaskId,mSaveFolder);
        File targetFile = new File(mSinglePath);
        boolean delate = false;
        if(targetFile.exists()){
             delate = targetFile.delete();
        }

        Log.d("feifei","delete_single file,deleteDataBase:"+deleteData+",mSinglePath:"+mSinglePath+",delate:"+delate);

        new File(FileDownloadUtils.getTempPath(mSinglePath)).delete();
    }



    // 多任务下载
    private FileDownloadListener downloadListener;

    public FileDownloadListener createLis(){
        return new FileDownloadSampleListener(){
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if(task.getListener() != downloadListener){
                    return;
                }
                Log.d("feifei","pending taskId:"+task.getId()+",fileName:"+task.getFilename()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes);

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if(task.getListener() != downloadListener){
                    return;
                }
                Log.d("feifei","progress taskId:"+task.getId()+",fileName:"+task.getFilename()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes+",speed:"+task.getSpeed());
            }

            @Override
            protected void blockComplete(BaseDownloadTask task) {
                if(task.getListener() != downloadListener){
                    return;
                }
                Log.d("feifei","blockComplete taskId:"+task.getId()+",filePath:"+task.getPath()+",fileName:"+task.getFilename()+",speed:"+task.getSpeed()+",isReuse:"+task.reuse());
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                if(task.getListener() != downloadListener){
                    return;
                }
                Log.d("feifei","completed taskId:"+task.getId()+",isReuse:"+task.reuse());
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if(task.getListener() != downloadListener){
                    return;
                }
                Log.d("feifei","paused taskId:"+task.getId()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes);
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                if(task.getListener() != downloadListener){
                    return;
                }
                Log.d("feifei","error taskId:"+task.getId()+",e:"+e.getLocalizedMessage());
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                if(task.getListener() != downloadListener){
                    return;
                }
                Log.d("feifei","warn taskId:"+task.getId());
            }
        };
    }

    public void start_multi(){

        downloadListener = createLis();
        //(1) 创建 FileDownloadQueueSet
        final FileDownloadQueueSet queueSet = new FileDownloadQueueSet(downloadListener);

        //(2) 创建Task 队列
        final List<BaseDownloadTask> tasks = new ArrayList<>();
            BaseDownloadTask task1 = FileDownloader.getImpl().create(BIG_FILE_URLS[3]).setPath(mSaveFolder,true);
            tasks.add(task1);
            BaseDownloadTask task2 = FileDownloader.getImpl().create(BIG_FILE_URLS[4]).setPath(mSaveFolder,true);
            tasks.add(task2);

        //(3) 设置参数

        // 每个任务的进度 无回调
        //queueSet.disableCallbackProgressTimes();
        // do not want each task's download progress's callback,we just consider which task will completed.

        queueSet.setCallbackProgressTimes(100);
        queueSet.setCallbackProgressMinInterval(100);
        //失败 重试次数
        queueSet.setAutoRetryTimes(3);

        //避免掉帧
        FileDownloader.enableAvoidDropFrame();

        //(4)串行下载
        queueSet.downloadSequentially(tasks);

        //(5)任务启动
        queueSet.start();
    }

    public void stop_multi(){
        FileDownloader.getImpl().pause(downloadListener);
    }

    public void deleteAllFile(){

        //清除所有的下载任务
        FileDownloader.getImpl().clearAllTaskData();

        //清除所有下载的文件
        int count = 0;
        File file = new File(FileDownloadUtils.getDefaultSaveRootPath());
        do {
            if (!file.exists()) {
                break;
            }

            if (!file.isDirectory()) {
                break;
            }

            File[] files = file.listFiles();

            if (files == null) {
                break;
            }

            for (File file1 : files) {
                count++;
                file1.delete();
            }

        } while (false);

        Toast.makeText(this,
                String.format("Complete delete %d files", count), Toast.LENGTH_LONG).show();
    }

}
