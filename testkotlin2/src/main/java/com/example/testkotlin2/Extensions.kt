package com.example.testkotlin2

import android.app.Activity
import android.widget.Toast

fun Activity.mytoast(message:String, duration:Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,message,duration).show()
}