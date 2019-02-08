package com.example.testuiautomator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.PreferenceMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Calendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class SharedPreferencesHelperTest {

    private static final String TEST_NAME = "Test_name";
    private static final String TEST_EMAIL = "feifei@sogou-inc.com";
    private static final Calendar TEST_DATE_OF_BIRTH = Calendar.getInstance();

    private SharedPreferenceEntry mSharedPreferenceEntry;

    private SharedPreferencesHelper mSharedPreferencesHelper;

    private SharedPreferences mSharePreferences;

    /** 上下文 */
    private Context mContext;

    /** 如果需要扩展类的行为，可以通过mock来实现 */
    private SharedPreferencesHelper mMockSharedPreferencesHelper;

    /** mock操作，用于模拟失败的操作 */
    @Mock
    SharedPreferences mMockSharePreferences;

    @Mock
    SharedPreferences.Editor mMockBrokenEditor;

    @Before
    public void setUp(){

        //获取application的Context
        mContext = InstrumentationRegistry.getTargetContext();

        //实例化SharePreferences
        mSharePreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        mSharedPreferenceEntry = new SharedPreferenceEntry(TEST_NAME,TEST_DATE_OF_BIRTH,TEST_EMAIL);
        //初始化SharedPreferencesHelper,依赖注入SharePreferences
        mSharedPreferencesHelper = new SharedPreferencesHelper(mSharePreferences);


        //mock 模仿的对象
        mMockSharePreferences = Mockito.mock(SharedPreferences.class);
        mMockBrokenEditor = Mockito.mock(SharedPreferences.Editor.class);

        when(mMockSharePreferences.edit()).thenReturn(mMockBrokenEditor);
        when(mMockBrokenEditor.commit()).thenReturn(false); // 模拟 commit失败的情况

        //创建一个 mock 模拟 SharedPreferencesHelper
        mMockSharedPreferencesHelper = new SharedPreferencesHelper(mMockSharePreferences);


    }


    /**
     * 测试保存数据是否成功
     */
    @Test
    public void sharedPreferencesHelper_SavePersionInfomation(){
        assertThat(mMockSharedPreferencesHelper.savePersonalInfo(mSharedPreferenceEntry),is(true));
    }

    /**
     * 该方法的实测需要修改SharedPreference的部分行为,所以需要mock
     */
    @Test
    public void sharedPreferenceHelper_SaveAndReadPersonalInfomation(){
        mSharedPreferencesHelper.savePersonalInfo(mSharedPreferenceEntry);
        SharedPreferenceEntry sharedPreferenceEntry = mSharedPreferencesHelper.getPersonalInfo();

        assertThat(isEquals(mSharedPreferenceEntry,sharedPreferenceEntry),is(true));
    }

    private boolean isEquals(SharedPreferenceEntry sharedPreferenceEntry, SharedPreferenceEntry target){
        return sharedPreferenceEntry.getName().equals(target.getName())&&sharedPreferenceEntry.getEmail().equals(target.getEmail())&&sharedPreferenceEntry.getDateOfBirth().equals(target.getDateOfBirth());
    }
}
