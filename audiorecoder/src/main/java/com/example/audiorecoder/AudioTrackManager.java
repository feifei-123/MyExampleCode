package com.example.audiorecoder;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class AudioTrackManager {
    public static final String TAG = "AudioTrackManager";
    private AudioTrack audioTrack;
    private DataInputStream dis;
    private Thread recordThread;
    private boolean isPlaying = false;
    private static AudioTrackManager mInstance;
    private int bufferSize;


    private int frequence = 8000; //录制频率，单位hz.这里的值注意了，写的不好，可能实例化AudioRecord对象的时候，会出错。我开始写成11025就不行。这取决于硬件设备
    private int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    private int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;


    public AudioTrackManager() {
//        bufferSize = AudioTrack.getMinBufferSize(frequence, channelConfig, audioEncoding);
//        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, frequence, channelConfig, audioEncoding, bufferSize * 2, AudioTrack.MODE_STREAM);
    }

    /**
     * 获取单例引用
     *
     * @return
     */
    public static AudioTrackManager getInstance() {
        if (mInstance == null) {
            synchronized (AudioTrackManager.class) {
                if (mInstance == null) {
                    mInstance = new AudioTrackManager();
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
            isPlaying = false;
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
     * 启动播放线程
     */
    private void startThread() {
        destroyThread();
        isPlaying = true;
        if (recordThread == null) {
            recordThread = new Thread(recordRunnable);
            recordThread.start();
        }
    }

    /**
     * 播放线程
     */
    Runnable recordRunnable = new Runnable() {
        @Override
        public void run() {
             bufferSize = AudioTrack.getMinBufferSize(frequence,channelConfig,audioEncoding);
            short[]buffer = new short[bufferSize/4];
            try{
                //创建audioTrack track
                audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,frequence,channelConfig,audioEncoding,bufferSize,AudioTrack.MODE_STREAM);
                audioTrack.play();
                //由于AudioTrack播放的是流，所以，我们需要一边播放一边读取
                while (isPlaying && dis.available() >0){
                    int i = 0;
                    while (dis.available() > 0 && i < buffer.length){
                        buffer[i] = dis.readShort();
                        i++;
                    }
                    audioTrack.write(buffer,0,buffer.length);
                    Log.d(TAG,"audioPlayer while:");
                }

                audioTrack.stop();
                dis.close();

            }catch (Exception e){
                e.printStackTrace();
            }finally {

            }
        }

    };

    /**
     * 播放文件
     *
     * @param path
     * @throws Exception
     */
    private void setPath(String path) throws Exception {
        File file = new File(path);
//        dis = new DataInputStream(new FileInputStream(file));
        dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));

    }

    /**
     * 启动播放
     *
     * @param path
     */
    public void startPlay(String path) {
        try {
            setPath(path);
            startThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放
     */
    public void stopPlay() {
        try {
            destroyThread();
            if (audioTrack != null) {
                if (audioTrack.getState() == AudioRecord.STATE_INITIALIZED) {
                    audioTrack.stop();
                }
                if (audioTrack != null) {
                    audioTrack.release();
                }
            }
            if (dis != null) {
                dis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}