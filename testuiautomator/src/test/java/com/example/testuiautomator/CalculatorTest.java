package com.example.testuiautomator;

import android.util.Log;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    private static final String TAG = CalculatorTest.class.getSimpleName();

    Calculator mCalculator;

    //@BeforeClass: 为测试类标识一个static方法，在测试之前只执行一次。
    @BeforeClass
    public static void before_class(){
        Log.d(TAG,"BeforeClass  before_class was called !");
    }

    //@AfterClass: 为测试类标识一个static方法，在所有测试方法结束之后只执行一次。
    @AfterClass
    public static void  after_class(){
        Log.d(TAG,"AfterClass  after_class was called !");
    }


    //test前 执行一次， 做一些准备工作
    @Before
    public void setUp() throws Exception {
        mCalculator = new Calculator();
        Log.d(TAG,"Before  setUp was called!");
    }

    //测试后 执行一次，做一些销毁操作
    @After
    public void tearDown() throws Exception {
        Log.d(TAG,"After - tearDown() was called!");
    }


    //测试体
    @Test
    public void sum() {
        Log.d(TAG,"Test - sum:");
        assertEquals(mCalculator.sum(3,4),7);
    }

    //测试体
    @Test
    public void sum2(){
        Log.d(TAG,"Test - sum2:");
        assertEquals(mCalculator.sum(3,4),7);
    }
}