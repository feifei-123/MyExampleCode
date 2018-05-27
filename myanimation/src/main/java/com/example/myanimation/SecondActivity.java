package com.example.myanimation;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.view.Window;

public class SecondActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setSharedElementEnterTransition(new ChangeBounds());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

        }
        finishAfterTransition();
//        finish();
//        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }


}
