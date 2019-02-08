package com.example.testdpi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

public class MainActivity extends Activity {

    public static final String TAG = "feifei";
    Bitmap bitmap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//       DisplayMetrics density = getResources().getDisplayMetrics();


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Log.d(TAG,"SCREEN_ display w:"+displayMetrics.widthPixels+",h:"+displayMetrics.heightPixels);
        Log.i(TAG,"SCREEN_ density:"+displayMetrics.density);
        Log.i(TAG,"SCREEN_ scaledDensity:"+displayMetrics.scaledDensity);
        Log.i(TAG,"SCREEN_ densityDpi:"+displayMetrics.densityDpi);




        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
//        options.inDensity = 160;
//        options.inTargetDensity =280;
//        options.inScreenDensity = 160;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ocr_sample_cn, options);
        int density = bitmap.getDensity();
        bitmap.setDensity(density/2);
        ((ImageView)findViewById(R.id.iv_bitmap)).setImageBitmap(bitmap);
        Log.i(TAG,"SCREEN_ bitmap.width:"+bitmap.getWidth()+",height:"+bitmap.getHeight()+",bitmap.inDensity:"+bitmap.getDensity());

    }
}
