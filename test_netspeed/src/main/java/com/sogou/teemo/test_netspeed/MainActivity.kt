package com.sogou.teemo.test_netspeed

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import org.xbill.DNS.Lookup
import org.xbill.DNS.Resolver
import org.xbill.DNS.SimpleResolver
import org.xbill.DNS.Type
import java.io.InputStream
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat.getSystemService



class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var handler:Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var thread_test = HandlerThread("net_check")
        thread_test.start()
        handler = Handler(thread_test.looper)

    }


    fun makePing(){
        handler.post({
            var result: ShellUtils.CommandResult =   ShellUtils.execCommand("ping -c 3 -w 100 www.baidu.com",false)
            Log.d("feifei","result:${result.result},successMsg:${result.successMsg},errorMsg:${result.errorMsg}")

        })

    }

    override fun onClick(view: View?) {
//        makePing()
        makeTracePath()
//        queryDNS()
        getIp4Wifi()
    }


    fun makeTracePath(){

        handler.post({
            Log.d("feifei","makeTracePath")
//            Ping().doMakeTrace("www.sogou.com")
            var result: ShellUtils.CommandResult =   ShellUtils.execCommand("tracepath www.baidu.com",false)
//           var result: ShellUtils.CommandResult =   ShellUtils().execCommand("ping -c 3 -w 100 www.baidu.com",false)
//            Log.d("feifei","result:${result.result},successMsg:${result.successMsg},errorMsg:${result.errorMsg}")
//
        })

    }


    fun queryDNS(){

        handler.post({
            Log.d("feifei","SimpleResolver")
            var resover = SimpleResolver("192.168.36.54")
//            Resolver resolver = new SimpleResolver("192.168.36.54");
            resover.setPort(53);
            var lookup = Lookup("www.sogou.com", Type.A)
//            Lookup lookup = new Lookup("www.test.com", Type.A);
//            lookup.setResolver(resover);
            lookup.run()
            if (lookup.getResult() == Lookup.SUCCESSFUL) {
                Log.d("feifei", lookup.getAnswers()[0].rdataToString());
            }
        })
    }


    private fun intToIp(i: Int): String {

        return (i and 0xFF).toString() + "." +
                (i shr 8 and 0xFF) + "." +
                (i shr 16 and 0xFF) + "." +
                (i shr 24 and 0xFF)
    }

    fun getIp4Wifi(){
        //获取wifi服务
        val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true)
        }
        val wifiInfo = wifiManager.getConnectionInfo()
        val ipAddress = wifiInfo.getIpAddress()
        val ip = intToIp(ipAddress)
        Log.d("feifei","LocalIP:"+ip)
    }

}
