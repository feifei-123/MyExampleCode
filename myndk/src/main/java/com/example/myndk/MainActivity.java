package com.example.myndk;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_tips = (TextView) findViewById(R.id.tv_tips);
        tv_tips.setText(new JNIUtils().stringFromJNI());
        jniUtils = new JNIUtils();

    }

    public JNIUtils jniUtils;


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case  R.id.btn_file:
            {
                String filepath = Environment.getExternalStorageDirectory()+File.separator+"test.txt";
                Toast.makeText(MainActivity.this,filepath,Toast.LENGTH_LONG).show();
                new JNIUtils().writeFile(filepath);
            }
                break;
            case R.id.btn_array:{
                int[] testData2 = new int[]{1, 2, 3, 4, 5};
                for (int i = 0; i < testData2.length; i++) {
                    Log.d(TAG, "testData2: origin " + testData2[i]);
                }
                new JNIUtils().operationArray2(testData2);
                for (int i = 0; i < testData2.length; i++) {
                    Log.d(TAG, "testData2: after " + testData2[i]);
                }
            }
                break;
            case R.id.btn_malloc:
                Log.d("feifei","jniUtils.initSDK():"+jniUtils);
                jniUtils.initSDK();
                testmkdir();
                break;

            case R.id.btn_free:
                Log.d("feifei","jniUtils.releaseSDK():"+jniUtils);
                jniUtils.releaseSDK();
                break;

        }

    }

    public  void testmkdir(){

        String path = "/data/local/model_file/langs/fr_tmp";
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
        boolean succes = file.mkdir();
        Log.d(TAG,"file:"+file.getAbsolutePath()+",mkdir:"+succes);
    }
}
