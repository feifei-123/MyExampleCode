package com.example.myroom;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MyViewModel extends AndroidViewModel {


    private final UserDao mUserDao;
    private static final String TAG = "MainViewModel";

    private final LiveData<List<User>> mUsers;
    public MyViewModel(@NonNull Application application) {
        super(application);

        mUserDao = MyDataBase.getsInstance(application).userDao();
        mUsers = mUserDao.users();

    }

    public UserDao getUserDao(){
        return mUserDao;
    }

    public LiveData<List<User>> getmUsers() {
        return mUsers;
    }
}
