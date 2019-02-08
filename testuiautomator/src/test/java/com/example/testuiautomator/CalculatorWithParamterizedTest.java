package com.example.testuiautomator;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CalculatorWithParamterizedTest {

    public static final String TAG = CalculatorWithParamterizedTest.class.getSimpleName();
    private final int mOperandOne;
    private final int mOperandTwo;
    private final int mExpectedResult;

    private Calculator mCalculator;


    public CalculatorWithParamterizedTest(int a, int b, int expect){
        mOperandOne = a;
        mOperandTwo = b;
        mExpectedResult = expect;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> initData(){
        return Arrays.asList(new Object[][]{
                {0,0,0},
                {0,-1,-1},
                {2,2,4},
                {8,8,16},
                {32,0,32},
                {64,64,128}
        });
    }


    @Before
    public void setup(){
        Log.d(TAG,"@Before - setup");
        mCalculator = new Calculator();
    }
    @Test
    public void testAddTwoNumber(){
        Log.d(TAG,"@Test - testAddTwoNumber");
        int resultAdd = mCalculator.sum(mOperandOne,mOperandTwo);
        assertEquals(resultAdd,mExpectedResult);
    }
}
