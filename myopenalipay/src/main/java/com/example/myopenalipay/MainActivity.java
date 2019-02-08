package com.example.myopenalipay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        openAlipayPayPage(this);
    }

    public  boolean openAlipayPayPage(Context context) {

        boolean success = false;
        try {
//             final String alipayqr = "alipayqr://platformapi/startapp?saId=10000001&clientVersion=3.7.0.0718";
            final String alipayqr = "alipays://platformapi/startapp";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(alipayqr));
            context.startActivity(intent);
            success =  true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!success){
            Toast.makeText(this,"请首先安装支付宝",Toast.LENGTH_LONG).show();
        }
        return success;
    }

    /**
     * 发送一个intent
     */
    private static void openUri(Context context, String s) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
        context.startActivity(intent);
    }
}
