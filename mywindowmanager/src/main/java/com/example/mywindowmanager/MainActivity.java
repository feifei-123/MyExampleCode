package com.example.mywindowmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        createFloatingWindow();
    }

    public void createFloatingWindow(){
        Button button = new Button(getApplicationContext());
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService("window");

        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.type = 2002;
        wmParams.format = 1;

        wmParams.flags = 40;
        wmParams.width = 40;
        wmParams.height = 40;
        wm.addView(button,wmParams);

    }
}
