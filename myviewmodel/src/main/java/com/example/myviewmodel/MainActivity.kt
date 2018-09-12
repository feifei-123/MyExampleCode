package com.example.myviewmodel

import android.app.Activity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.os.Bundle

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var intent = Intent(this,SecondActivity::class.java);
        startActivity(intent)

    }




}

//class MyViewModel():ViewModel(){
//    lateinit var users: MutableLiveData<List<User>>;
//    fun getUsers()->{
//
//    }
//}

data class User(var name:String,var age:String)
