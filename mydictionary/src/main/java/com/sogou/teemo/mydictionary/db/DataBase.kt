package com.sogou.teemo.mydictionary.db
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities =  [(TRDictionary::class)],version = 1,exportSchema = false)
abstract class AppDataBase:RoomDatabase(){
    abstract fun trdictionaryDao():TRDictionaryDAO
}
