package com.sogou.teemo.mydictionary.db


import android.arch.persistence.room.Room
import android.content.Context

class AppDataBaseHelper constructor(context:Context) {

//    val appDateBase = Room.databaseBuilder(context,AppDataBase::class.java,DB_NAME+DB_VERSION).build()
    val appDateBase = Room.databaseBuilder(context, AppDataBase::class.java,"dictionary.db").build()
    companion object {
        @Volatile
        private var INSTANCE:AppDataBaseHelper? = null

        fun getInstance(context: Context):AppDataBaseHelper{
            if(INSTANCE == null){
                synchronized(AppDataBaseHelper::class){
                    INSTANCE = AppDataBaseHelper(context.applicationContext)
                }
            }

            return INSTANCE!!
        }
    }




    // ---------------------

    fun insertAllDictionaryItems(dictItems: List<TRDictionary>):List<Long>{
        return appDateBase.trdictionaryDao().insertAllDictionaryItems(dictItems);
    }

    fun insertDictionaryItem(dict: TRDictionary):Long{
        return appDateBase.trdictionaryDao().insertDictionaryItem(dict)
    }

    fun checkItem(mWord:String):TRDictionary{
        return appDateBase.trdictionaryDao().checkItem(mWord)
    }

    fun checkItemLike(mWord:String):List<TRDictionary>{
        return appDateBase.trdictionaryDao().checkItemLike(mWord);
    }

}