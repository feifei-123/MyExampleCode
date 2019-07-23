package com.sogou.teemo.test_hprof

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class SecondActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        MainActivity.holdActivity.add(this)

        Log.d("feifei","onCreate")
    }

    override fun onClick(p0: View?) {

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("feifei","onDestroy")
    }
}
