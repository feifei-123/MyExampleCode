package com.example.myviewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

public class MyViewModel extends ViewModel {
    private MutableLiveData<User> users;
    public LiveData<User> getUsers(){
        if(users == null){
            users = new MutableLiveData<>();
//            loadUsers();
        }
        return users;
    }

    public void loadUsers(){
        users.postValue(new User("feifei","18"));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("feifei","onCleared");
    }


}
