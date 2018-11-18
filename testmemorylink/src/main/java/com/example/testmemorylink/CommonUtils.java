package com.example.testmemorylink;

import android.content.Context;

public class CommonUtils {
    private static CommonUtils instance;
    private Context context;

    private CommonUtils(Context context) {
        this.context = context;
    }

    public static CommonUtils getInstance(Context context) {
        if (instance == null) {
            instance = new CommonUtils(context);
        }
        return instance;
    }

}
