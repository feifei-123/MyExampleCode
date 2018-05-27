package com.example.myanimation;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button btn_dispose;
    private Button btn_share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_dispose = findViewById(R.id.btn_dispose);
        btn_share = (Button)findViewById(R.id.btn_share);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_dispose:{

                //ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(this,R.anim.in_from_left,R.anim.out_from_right);

                ActivityOptions activityOptions = ActivityOptions.makeScaleUpAnimation(btn_dispose,(int)(btn_dispose.getWidth()/2.0),(int)(btn_dispose.getHeight()/2.0),0,0);
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent, activityOptions.toBundle());

            }
                break;
            case R.id.btn_slide:{
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
                break;
            case R.id.btn_fade:
                break;
            case R.id.btn_share:{
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, btn_share, "share").toBundle());
            }
                break;
        }
    }
}
