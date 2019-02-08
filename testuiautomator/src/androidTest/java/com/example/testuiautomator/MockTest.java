package com.example.testuiautomator;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.security.acl.LastOwnerException;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MockTest {

    public static final String TAG = MockTest.class.getSimpleName();
    Person mPerson;

    @Before
    public void setup(){
        mPerson = mock(Person.class);
    }

    @Test
    public void testIsNotNull(){
        assertNotNull(mPerson);
    }

    @Test
    public void testPersionReturn(){
        //thenReturn(T value)	设置要返回的值
        when(mPerson.getName()).thenReturn("小明");
        //thenThrow(Throwable… throwables)	设置要抛出的异常
        when(mPerson.getSex()).thenThrow(new NullPointerException("滑稽,性别不明"));


        //doReturn 打桩方法时，语义为：以什么结果返回，当执行什么方法时
        doReturn("小小").when(mPerson).getName();

        //thenAnswer
        when(mPerson.eat(anyString())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] args =  invocation.getArguments();
                return args[0].toString()+",味道不错";
            }
        });

        Log.d(TAG,"mPerson.getName():"+mPerson.getName());

        //Log.d(TAG,"mPerson.getSex():"+mPerson.getSex());

        Log.d(TAG,"mPerson.eat():"+mPerson.eat("饺子"));

    }

    @Test
    public void testPersionVerifyAfter(){
        when(mPerson.getName()).thenReturn("feifei");
        Log.d(TAG,"Person.getName()1:"+mPerson.getName());
        Log.d(TAG,"Person.getName()2:"+mPerson.getName());
        Log.d(TAG,"Person.getName()3:"+mPerson.getName());
        Log.d(TAG,"stamp:"+System.currentTimeMillis());
        verify(mPerson,after(1000)).getName();
        Log.d(TAG,"stamp:"+System.currentTimeMillis());

        verify(mPerson,atLeast(2)).getName();

    }


    /**
     * verify(mPerson, atLeast(2))  - 验证方法至少验证2次
     */
    @Test
    public void testPersonVerifyAtLeast(){
    mPerson.getName();
    mPerson.getName();
    verify(mPerson,atLeast(2)).getName();
    }

    /**
     *     verify(mPerson, atMost(2))  - 验证方法之多调用2次
     */

    @Test
    public void testPersonVerifyAtMost(){
        mPerson.getName();
        //至多验证2次
        verify(mPerson, atMost(2)).getName();
    }

    /**
     * verify(mPerson, atMost(2)).getName(); - 验证方法调用2次
     */
    @Test
    public void testPersonVerifyTimes(){
        mPerson.getName();
        mPerson.getName();
        //验证方法调用2次
        verify(mPerson, atMost(2)).getName();
    }


    /**
     * 验证方法 在1秒内 执行了两次
     */
    @Test
    public void testPersionVerifyTimes(){
        mPerson.getName();
        mPerson.getName();
        verify(mPerson, timeout(100).times(2)).getName();
    }


    /**
     * any -- 匹配 任意指定 类型
     */
    @Test
    public void testPersionAny(){
        when(mPerson.eat(any(String.class))).thenReturn("米饭");
        Log.d(TAG,"mPerson.eat："+mPerson.eat("面条"));
    }


    /**
     * contains("面") - 匹配  包含**
     */
    @Test
    public void testPersionContains(){
     when(mPerson.eat(contains("面"))).thenReturn("面条条");
        Log.d(TAG,"mPerson.eat："+mPerson.eat("门面"));
    }


    /**
     *  argThat  - 匹配特定条件的参数
     */
    @Test
    public void testPersionArgThat(){
        when(mPerson.eat(argThat(new ArgumentMatcher<String>() {

            @Override
            public boolean matches(Object argument) {
                return ((String)argument).length()%2==0;
            }
        }))).thenReturn("面条");

        Log.d(TAG,"mPerson.eat："+mPerson.eat("abcd"));
    }


    @Test
    public void testPersonInOrder() throws Exception {
        mPerson.setSex(1);
        mPerson.setName("小明");


        InOrder mInOrder = inOrder(mPerson);
        mInOrder.verify(mPerson).setSex(1);
        mInOrder.verify(mPerson).setName("小明");



    }
}
