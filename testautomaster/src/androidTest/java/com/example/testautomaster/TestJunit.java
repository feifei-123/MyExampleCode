package com.example.testautomaster;


import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.filters.RequiresDevice;
import android.support.test.filters.SdkSuppress;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestJunit {

    @Before
    public void testBefore() throws Exception{
        Log.d("JUnit","testBefore");
    }

    @Test
    public void userAppContext() throws Exception{

        Context appContext = InstrumentationRegistry.getTargetContext();
        Log.d("JUnit","testTEst");

    }

    @Test
    public void testTest() throws Exception{
        Log.d("Junit","testTest1");
    }

    @Test
    @SmallTest
    public void testTestSmallTest() throws Exception{
        Log.d("Junit","testTest2");
    }

    @SmallTest
    public void testSmallTest()throws Exception{
        Log.d("Junit","testSmallTest");
    }

    @MediumTest
    public void testMediumTest() throws Exception{
        Log.d("Junit","testMediumTest");
    }

    @MediumTest
    public void testLargeTest() throws Exception{
        Log.d("Junit","testLargeTest");
    }

    @RequiresDevice
    public void testReuiresDevices()throws Exception{
        Log.d("Junit","testRequiresDevice");
    }


    @SdkSuppress(minSdkVersion = 19)
    public void testSdkSuppress()throws Exception{
        Log.d("Junit","testSdkSuppress");
    }

    @After
    public void testAfter()throws Exception{
        Log.d("Junit","testAfter");
    }



}
