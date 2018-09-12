package com.example.myviewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class SecondActivity extends FragmentActivity {

    MyViewModel model;
    TextView tv_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv_info = findViewById(R.id.tv_info);

        model = ViewModelProviders.of(this).get(MyViewModel.class);
        model.getUsers().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User users) {
                String info = "user:"+users.getName()+",age:"+users.getAge();
                tv_info.setText(info);
            }
        });


        findViewById(R.id.btn_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.loadUsers();
            }
        });
    }

}
