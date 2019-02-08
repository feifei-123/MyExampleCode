package com.sogou.teemo.mydictionary.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by baixuefei on 18/3/28.
 */

public class ThreadPoolUtils {

    public static ExecutorService cachedTheadPool = Executors.newCachedThreadPool();

    public static void excute(Runnable runnable){
        if(cachedTheadPool != null && runnable != null){
            cachedTheadPool.execute(runnable);
        }
    }
}
