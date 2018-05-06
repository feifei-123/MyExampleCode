package com.example.mybinder.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.mybinder.PlusAidlInterface;
import com.example.mybinder.User;

public class AIDLService extends Service {

    public AIDLService(){

    }

    private final PlusAidlInterface.Stub mBinder = new PlusAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int plus(int a, int b) throws RemoteException {
            return (a+b);
        }

        @Override
        public String evalate(User user){
            return  user.name+",age:"+user.age+":good";
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


}
