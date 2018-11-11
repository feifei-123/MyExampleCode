package com.example.testkotlin2;

import android.util.Log;

public class TestExample {

    public static void main(String[]args){
        Boolean isEmpty1 = StringUtils.isEmpty("feifei");

        Boolean isEmpty2 = StringUtils.INSTANCE.isEmpty2("feifei");
        Log.d("feifei","isEmpty:"+isEmpty1+",isEmpty2:"+isEmpty2);

    }
}
