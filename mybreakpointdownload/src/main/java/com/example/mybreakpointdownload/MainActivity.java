package com.example.mybreakpointdownload;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mybreakpointdownload.bean.FileInfo;

public class MainActivity extends Activity implements View.OnClickListener{

    private TextView fileName;
    private Button startButton;
    private Button stopButton;
    private ProgressBar downloadProgress;
    private FileInfo fileInfo;
    private String urlstr = "http://106.120.188.43/app/android/x108286.apk";

    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileName = (TextView) findViewById(R.id.file_textview);
        downloadProgress = (ProgressBar) findViewById(R.id.progressBar2);
        startButton = (Button) findViewById(R.id.start_button);
        stopButton = (Button) findViewById(R.id.stop_button);
        downloadProgress.setMax(100);

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);

        fileInfo = new FileInfo(0, urlstr, getfileName(urlstr), 0, 0);

        myReceiver = new MyReceiver();
        register();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregister();
    }

    public void register(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(myReceiver, intentFilter);
    }

    public void unregister(){
        unregisterReceiver(myReceiver);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:

            {
                fileName.setText(getfileName(urlstr));
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                intent.setAction(DownloadService.ACTION_START);
                intent.putExtra("fileInfo", fileInfo);
                startService(intent);
            }


                break;
            case R.id.stop_button:
            {
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                intent.setAction(DownloadService.ACTION_STOP);
                intent.putExtra("fileInfo", fileInfo);
                startService(intent);
            }

                break;
        }
    }

    private String getfileName(String url) {

        return url.substring(url.lastIndexOf("/") + 1);
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                long finished = intent.getLongExtra("finished", 0);
                Log.d("feifei","MyReceiver - finished:"+finished);
                downloadProgress.setProgress((int)finished);

            }
        }

    }
}
