package com.sogou.teemo.test_hprof

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.view.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {



    companion object {
        var holdActivity = mutableListOf<Activity>()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun go2SecondActivity(){
        var intent = Intent(this,SecondActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_makehprof->
            {
                makeHprof()
            }

            R.id.btn_go2secondAcivity->
            {
                go2SecondActivity()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("feifei","holdActivity.size:"+ holdActivity.size)
    }

    fun makeHprof(){
        HprofUtils.createDumpFile(this)

        Toast.makeText(this,"成功",Toast.LENGTH_LONG)
    }


    fun test(){
        var timeZone = TimeZone.getTimeZone("America/New_York")
        Log.d("feifei",timeZone.getDisplayName())
        Log.d("feifei",timeZone.getDisplayName(false, TimeZone.LONG))
        Log.d("feifei",timeZone.getDisplayName(false, TimeZone.SHORT))
        Log.d("feifei","是否使用夏令时:"+timeZone.useDaylightTime())
        Log.d("feifei","相对于标准时间的面熟:"+timeZone.getDSTSavings())


        val c = Calendar.getInstance()
        c.timeZone =timeZone
        val homeMonth = c.get(Calendar.MONTH) + 1
        val homeDay = c.get(Calendar.DAY_OF_MONTH)
        val homeHour = c.get(Calendar.HOUR_OF_DAY)
        val homeMinute = c.get(Calendar.MINUTE)

        var stamp = System.currentTimeMillis()
        stamp+=timeZone.getDSTSavings()
        var timeinfo = getWholeTimeString(stamp)
        Log.d("feifei","当前时间:"+homeMonth+"月"+homeDay+"日"+homeHour+"时"+homeMonth+"分")
//        Log.d("feifei","timeinfo:"+timeinfo)
        val ids = TimeZone.getAvailableIDs()
        for(id in ids){
            Log.d("feifei","时区ID:"+id)
        }


        var zone = TimeZone.getDefault()
        Log.d("feifei","defaultZone:"+zone.id)

    }

    fun getWholeTimeString(time: Long): String {
        var time = time
        val str = time.toString()
        if (str.length < 13) {
            time = time * 1000
        }
        val formatter = SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss,SSS")
        return formatter.format(Date(time))
    }
}
