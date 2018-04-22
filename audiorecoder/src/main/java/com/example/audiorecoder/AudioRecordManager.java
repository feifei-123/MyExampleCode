package com.example.audiorecoder;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class AudioRecordManager {
    public static final String TAG = "AudioRecordManager";
    private AudioRecord mRecorder;
    private DataOutputStream dos;
    private Thread recordThread;
    private boolean isRecording = false;
    private static AudioRecordManager mInstance;
    private  int bufferSize;



    private int frequence = 8000; //录制频率，单位hz.这里的值注意了，写的不好，可能实例化AudioRecord对象的时候，会出错。我开始写成11025就不行。这取决于硬件设备
    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    private int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;

    public AudioRecordManager() {

    }

    /**
     * 获取单例引用
     *
     * @return
     */
    public static AudioRecordManager getInstance() {
        if (mInstance == null) {
            synchronized (AudioRecordManager.class) {
                if (mInstance == null) {
                    mInstance = new AudioRecordManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 销毁线程方法
     */
    private void destroyThread() {
        try {
            isRecording = false;
            if (null != recordThread && Thread.State.RUNNABLE == recordThread.getState()) {
                try {
                    Thread.sleep(500);
                    recordThread.interrupt();
                } catch (Exception e) {
                    recordThread = null;
                }
            }
            recordThread = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            recordThread = null;
        }
    }

    /**
     * 启动录音线程
     */
    private void startThread() {
//        destroyThread();
        isRecording = true;
        if (recordThread == null) {
            recordThread = new Thread(recordRunnable);
            recordThread.start();
        }
    }

    /**
     * 录音线程
     */
    Runnable recordRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Log.d(TAG,"audioRecorder doInBackground");
                //开通输入流 到指定的文件
                //根据定义好的几个配置，来获取合适的缓冲大小
                bufferSize = AudioRecord.getMinBufferSize(frequence,channelConfig,audioEncoding);

                //实力化audioRecord
                mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC,frequence,channelConfig,audioEncoding,bufferSize);
                mRecorder.setRecordPositionUpdateListener(new AudioRecord.OnRecordPositionUpdateListener() {
                    @Override
                    public void onMarkerReached(AudioRecord audioRecord) {

                    }

                    @Override
                    public void onPeriodicNotification(AudioRecord audioRecord) {
                        Log.d(TAG,"onPeriodicNotification:"+audioRecord.toString());
                    }
                });
                mRecorder.setPositionNotificationPeriod(400);


                //定义缓冲
                short[]buffer = new short[bufferSize];

                //开始录制
                mRecorder.startRecording();

                while (isRecording){
                    //从bufferSize 中读取字节，返回读取的short个数
                    int bufferReadResult = mRecorder.read(buffer,0,buffer.length);
                    double volume = getAudioAmp(buffer,bufferReadResult);

                    if(listener != null){
                        listener.volumeChanged(volume);
                    }

                    Log.d(TAG,"volume:"+volume);
                    for(int i = 0;i<bufferReadResult;i++){
                        dos.writeShort(buffer[i]);
                    }
                }

                //结束录制
                mRecorder.stop();
                dos.close();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
            }
        }

    };

    /**
     * 保存文件
     *
     * @param path
     * @throws Exception
     */
    private void setPath(String path) throws Exception {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

    }

    /**
     * 启动录音
     *
     * @param path
     */
    public void startRecord(String path) {
        try {
            setPath(path);
            startThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止录音
     */
    public void stopRecord() {
        try {
            destroyThread();
            if (mRecorder != null) {
                if (mRecorder.getState() == AudioRecord.STATE_INITIALIZED) {
                    mRecorder.stop();
                }
                if (mRecorder != null) {
                    mRecorder.release();
                }
            }
            if (dos != null) {
                dos.flush();
                dos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getAudioAmp(short[]buffer,int count){
        long v = 0;
        for (int i = 0; i < buffer.length; i++) {
            v += buffer[i] * buffer[i];
        }
        // 平方和除以数据总长度，得到音量大小。
        double mean = v / (double) count;
        double volume = 10 * Math.log10(mean);
        return volume;
    }


    public AudioRecordListener getListener() {
        return listener;
    }

    public void setListener(AudioRecordListener listener) {
        this.listener = listener;
    }

    public AudioRecordListener listener;
    public interface  AudioRecordListener{
        public void recordStart();
        public void volumeChanged(double volume);
    }
}