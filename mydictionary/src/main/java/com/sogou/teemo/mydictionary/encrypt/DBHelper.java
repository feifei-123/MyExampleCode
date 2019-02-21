package com.sogou.teemo.mydictionary.encrypt;

import android.content.Context;

import net.sqlcipher.DatabaseErrorHandler;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import net.sqlcipher.database.SQLiteOpenHelper;


public class DBHelper  extends SQLiteOpenHelper {

    public static final String DB_NAME = "TRDictionary";
    public DBHelper(Context context) {
        super(context ,DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table dictionary(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT" +
                ",mWord text" +
                ",mJson text)"
        );
        sqLiteDatabase.execSQL("create index wordindex on dictionary(mWord)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
