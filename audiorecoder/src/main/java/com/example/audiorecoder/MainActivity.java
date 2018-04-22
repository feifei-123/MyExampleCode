package com.example.audiorecoder;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getName();
    public TextView tv_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_status = findViewById(R.id.tv_status);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_record:
                startRecord();
                break;
            case R.id.btn_stopRecord:
                stopRecord();
                break;
            case R.id.btn_start_play:
                startPlay();
                break;
            case R.id.btn_stop_play:
                stopPlay();
                break;
        }
    }


//    RecordTask recorder;
    public static String folder = Environment.getExternalStorageDirectory()+File.separator+"audio"+File.separator;
    public static String fName = "recording.pcm";
    public static File audioFile = null;

    public void startRecord(){
        String fileStr = folder+fName;
        audioFile = new File(fileStr);
        if(!audioFile.getParentFile().exists()){
            audioFile.getParentFile().mkdirs();
        }
        try {
            if(audioFile.exists()){
                audioFile.delete();
            }
            audioFile.createNewFile();
            boolean ex = audioFile.exists();
            Log.d(TAG,audioFile.getAbsolutePath()+" exist:"+ex);

        }catch (Exception e){
            e.printStackTrace();
        }

        AudioRecordManager.getInstance().startRecord(fileStr);
        Log.d(TAG,"create audioFile");
        tv_status.setText("recording");
//        //这里启动录制任务
//        recorder = new RecordTask();
//        recorder.execute();


    }

    public void stopRecord(){
        tv_status.setText("record stopped");
        AudioRecordManager.getInstance().stopRecord();
//        this.isRecording = false;
    }

//    PlayTask player;
    public void startPlay(){

//        player = new PlayTask();
//        player.execute();
        tv_status.setText("audio playing");
        String fileStr = folder+fName;
        AudioTrackManager.getInstance().startPlay(fileStr);


    }

    public void stopPlay(){
        tv_status.setText("audio playing stopped");
        AudioTrackManager.getInstance().stopPlay();
//        this.isPlaying = false;
    }

//    public boolean isRecording = false;
//
//    private int frequence = 8000; //录制频率，单位hz.这里的值注意了，写的不好，可能实例化AudioRecord对象的时候，会出错。我开始写成11025就不行。这取决于硬件设备
//    private int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
//    private int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
//    class RecordTask extends AsyncTask<Void,Integer,Void>{
//
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            isRecording = true;
//            try {
//                Log.d(TAG,"audioRecorder doInBackground");
//                //开通输入流 到指定的文件
//                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(audioFile)));
//
//                //根据定义好的几个配置，来获取合适的缓冲大小
//                int bufferSize = AudioRecord.getMinBufferSize(frequence,channelConfig,audioEncoding);
//
//                //实力化audioRecord
//                AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC,frequence,channelConfig,audioEncoding,bufferSize);
//                record.setRecordPositionUpdateListener(new AudioRecord.OnRecordPositionUpdateListener() {
//                    @Override
//                    public void onMarkerReached(AudioRecord audioRecord) {
//
//                    }
//
//                    @Override
//                    public void onPeriodicNotification(AudioRecord audioRecord) {
//                        Log.d(TAG,"onPeriodicNotification:"+audioRecord.toString());
//                    }
//                });
//                record.setPositionNotificationPeriod(400);
//
//
//                 //定义缓冲
//                short[]buffer = new short[bufferSize];
//
//                //开始录制
//                record.startRecording();
//
//                int r = 0;//存储录制进度
//                while (isRecording){
//                    //从bufferSize 中读取字节，返回读取的short个数
//                    int bufferReadResult = record.read(buffer,0,buffer.length);
//                    for(int i = 0;i<bufferReadResult;i++){
//                        dos.writeShort(buffer[i]);
//                    }
//                    publishProgress(new Integer(r));
//                    r++;//自增进度值
//                    Log.d(TAG,"recoding "+r+",isRecording:"+isRecording);
//                }
//
//                //结束录制
//                record.stop();
//                dos.close();
//            }catch (Exception e){
//                e.printStackTrace();
//          }finally {
//        }
//            return null;
//        }
//
//        protected  void onProcessUpdate(Integer...progress){
//            tv_status.setText("录音中:"+progress);
//        }
//
//        @Override
//        protected void onPostExecute(Void result){
//            tv_status.setText("录音完成");
//        }
//
//        @Override
//        protected void onPreExecute(){
//            tv_status.setText("开始录音");
//        }
//
//    }
//
//    boolean isPlaying = false;
//    class PlayTask extends AsyncTask<Void ,Integer,Void>{
//        @Override
//        protected Void doInBackground(Void... arg0){
//            isPlaying = true;
//            Log.d(TAG,"audioPlayer doInBackground");
//            int bufferSize = AudioTrack.getMinBufferSize(frequence,channelConfig,audioEncoding);
//            short[]buffer = new short[bufferSize/4];
//            try{
//                DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(audioFile)));
//
//                //创建audioTrack track
//                AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC,frequence,channelConfig,audioEncoding,bufferSize,AudioTrack.MODE_STREAM);
//                track.play();
//                //由于AudioTrack播放的是流，所以，我们需要一边播放一边读取
//                while (isPlaying && dis.available() >0){
//                    int i = 0;
//                    while (dis.available() > 0 && i < buffer.length){
//                        buffer[i] = dis.readShort();
//                        i++;
//                    }
//                    track.write(buffer,0,buffer.length);
//                    Log.d(TAG,"audioPlayer while:");
//                }
//
//                track.stop();
//                dis.close();
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }finally {
//
//            }
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result){
//            tv_status.setText("开始完成");
//        }
//
//        @Override
//        protected void onPreExecute(){
//            tv_status.setText("开始播放");
//        }
//    }
}
