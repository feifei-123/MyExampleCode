package com.example.myndk_1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.feifei.testjni.TestNatvie;
import com.example.myndk.JNIUtils;
import com.example.myndk_invoke.R;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TextView tv_title = (TextView) findViewById(R.id.btn_new);
//        tv_title.setText(new JNIUtils().stringFromJNI());
    }

    public long persionId;
    public TestNatvie testNatvie = new TestNatvie();
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_new:
                persionId = testNatvie.natvieLock();
                break;

            case R.id.btn_delete:
                testNatvie.getAge(persionId);
                break;
        }
    }
}
