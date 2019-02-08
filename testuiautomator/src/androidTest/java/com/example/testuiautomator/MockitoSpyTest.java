package com.example.testuiautomator;

import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static junit.framework.Assert.assertNotNull;

public class MockitoSpyTest {
    public static final String TAG = MockitoJUnit.class.getSimpleName();

    @Mock
    Person mPerson;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testIsNotNull(){
        assertNotNull(mPerson);
    }

    @Test
    public void testPersionSpy(){
        Log.d(TAG,"mPersion.getName():"+mPerson.getName());
    }
}
