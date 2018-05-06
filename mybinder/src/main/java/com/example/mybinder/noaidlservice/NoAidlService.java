package com.example.mybinder.noaidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class NoAidlService extends Service {

    public static final int TRANSATION_ADD = 0x001;
    public static final String DESCRIPTOR = "NoAidlService";
    private Binder mNoAidlBinder = new NoAidlBinder();
    public NoAidlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return  mNoAidlBinder;
    }



    private class NoAidlBinder extends Binder{
        @Override
        protected boolean onTransact(int code, Parcel data,  Parcel reply, int flags) throws RemoteException {
            switch (code){
                case TRANSATION_ADD:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();

                    int _arg1 = data.readInt();
                    int _result = _arg0+_arg1;
                    reply.writeNoException();
                    reply.writeInt(_result);

                    return true;
            }

            return super.onTransact(code, data, reply, flags);
        }
    }
}
