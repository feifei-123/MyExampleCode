package com.example.mybinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.example.mybinder.aidlservice.AIDLService;
import com.example.mybinder.noaidlservice.NoAidlService;

public class MainActivity extends Activity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void testAIDL(){
        Intent intent = new Intent(this,AIDLService.class);
        bindService(intent,mAIDLConnection, Context.BIND_AUTO_CREATE);
    }


    private PlusAidlInterface mPlus;
    private ServiceConnection mAIDLConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
                mPlus = PlusAidlInterface.Stub.asInterface(service);
                try {
                    int result = mPlus.plus(100,200);
                    Log.d(TAG,"_result 100+200 = "+result);

                    User user = new User();
                    user.name = "nancy";
                    user.age = "19";
                    String evalate = mPlus.evalate(user);
                    Log.d(TAG,"evalate:"+evalate);

                }catch (Exception e){

                }finally {

                }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };



    /**
     * 测试 NoAIDL
     */
    public void testNoADILService(){
        Intent intent = new Intent(this,NoAidlService.class);
        bindService(intent,mNoAidlConnection, Context.BIND_AUTO_CREATE);
    }


    private ServiceConnection mNoAidlConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();

            try {
                int _result;
                _data.writeInterfaceToken("NoAidlService");
                _data.writeInt(100);
                _data.writeInt(200);
                service.transact(NoAidlService.TRANSATION_ADD,_data,_reply,0);
                _reply.readException();
                _result = _reply.readInt();
                Log.d(TAG,"_result:"+_result);

            }catch (Exception e){

            }finally {
                _reply.recycle();
                _data.recycle();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_adil:
                testAIDL();
                break;
            case R.id.tv_noadil:
                testNoADILService();
                break;

        }
    }
}
