package com.sogou.teemo.mydictionary.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface TRDictionaryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDictionaryItem(dict: TRDictionary):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDictionaryItems(dictItems: List<TRDictionary>):List<Long>

    @Query("SELECT * FROM dictionary where mWord =:mWord")
    fun checkItem(mWord:String):TRDictionary

    @Query("SELECT * FROM dictionary where mWord like :mWord||'%'  ORDER BY mWord DESC limit 20 ")
    fun checkItemLike(mWord:String):List<TRDictionary>

    @Query("DELETE  FROM dictionary")
    fun clearItem()


}