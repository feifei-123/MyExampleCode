package com.example.testjvm;

import java.text.DecimalFormat;

public class JvmMaxHeapMemory {

    /**
     * 设置最大堆内存
     * 参数： -Xms5m -Xmx20m -XX:+PrintGCDetails -XX:+UseSerialGC -XX:+PrintCommandLineFlags
     */
    public static void main(String[] args) throws InterruptedException {
        byte[] b1 = new byte[1 * 1024 * 1024];
        System.out.println("分配了1M内存");
        jvmInfo();
        Thread.sleep(3000);
        byte[] b2 = new byte[4 * 1024 * 1024];
        System.out.println("分配了4M内存");
        jvmInfo();
    }

    //获取当前jvm的参数信息
    static void jvmInfo(){
        //最大内存
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("最大内存:"+maxMemory+"KB,转换为M："+toM(maxMemory));
        //当前可用内存
        long freeMemory = Runtime.getRuntime().freeMemory();
        System.out.println("当前可用内存:"+freeMemory+"KB,转换为M："+toM(freeMemory));
        //当前已用内存
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println("当前已用内存:"+totalMemory+"KB,转换为M："+toM(totalMemory));
    }

    /**
     * 将kb转换成M
     * @param kb
     * @return
     */
    static String toM(long kb){
        float num = (float) kb / (1024 * 1024);
        //格式化小数
        DecimalFormat df = new DecimalFormat("0.00");
        //返回的是String类型
        String str = df.format(num);
        return str;

    }
}
