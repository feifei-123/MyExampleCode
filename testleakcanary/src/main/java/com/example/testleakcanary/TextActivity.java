package com.example.testleakcanary;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        TextView textView = (TextView)findViewById(R.id.tv_test_text);
        TestDataModel.getInstance().setRetainedTextView(textView);

    }
}
