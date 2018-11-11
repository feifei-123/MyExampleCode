package com.example.testleakcanary;

import android.widget.TextView;

public class TestDataModel {

    private static TestDataModel instance;
    private TextView mText;
    public static TestDataModel getInstance(){
        if(instance == null){
            instance = new TestDataModel();
        }
        return instance;
    }

    public void setRetainedTextView(TextView textView){
        mText = textView;
    }
}
