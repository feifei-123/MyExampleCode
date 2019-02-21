package com.sogou.teemo.mydictionary.encrypt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.sogou.teemo.mydictionary.db.TRDictionary;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DictionaryHelper {

    public static String PASSWORD = "123";
    private DBHelper mHelper;
    private static SQLiteDatabase database;

    public static DictionaryHelper mInstance ;
    public static DictionaryHelper getInstance(Context context){
            synchronized (DictionaryHelper.class){
                if(mInstance == null){
                    mInstance = new DictionaryHelper(context);
                }
            }
            return  mInstance;
    }

    public DictionaryHelper(Context context){
        mHelper = new DBHelper(context);
        database = mHelper.getWritableDatabase(PASSWORD);
    }


    public void insert(List<TRDictionary> dictionaryList){
        database.beginTransaction();
        for(int i = 0;i< dictionaryList.size();i++){
            TRDictionary dictionary = dictionaryList.get(i);
            ContentValues values = new ContentValues();
            values.put("mWord",dictionary.mWord);
            values.put("mJson",dictionary.mJson);
            database.insert("dictionary",null,values);
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public TRDictionary queryDictionary(String words){

        String sql = "select * from dictionary where mWord = \""+words+"\"";
        Cursor c = database.rawQuery(sql,null);

        TRDictionary dictionary = null;
        if(c!=null){
            while(c.moveToNext()){
                dictionary = new TRDictionary();
                dictionary.id = c.getInt(c.getColumnIndex("id"));
                dictionary.mWord = c.getString(c.getColumnIndex("mWord"));
                dictionary.mJson = c.getString(c.getColumnIndex("mJson"));
            }

            c.close();
        }

        return dictionary;

    }

    public List<TRDictionary> queryDictionaryLike(String words){

        String sql = "select * from dictionary where mWord like \""+words+"%\"  ORDER BY mWord DESC limit 20";
        Cursor c = database.rawQuery(sql,null);

        List<TRDictionary> dictList  = new ArrayList<>();
        if(c!=null){
            while(c.moveToNext()){
                TRDictionary  dictionary = new TRDictionary();
                dictionary.id = c.getInt(c.getColumnIndex("id"));
                dictionary.mWord = c.getString(c.getColumnIndex("mWord"));
                dictionary.mJson = c.getString(c.getColumnIndex("mJson"));
                dictList.add(dictionary);
            }
            c.close();
        }

        return dictList;

    }

}
