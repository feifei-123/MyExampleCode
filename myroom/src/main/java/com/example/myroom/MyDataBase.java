package com.example.myroom;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {User.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
//(1)继承自RoomDatabase，是一个单例模式
public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase sInstance;
    private static final String TAG = MyDataBase.class.getSimpleName();

    public static MyDataBase getsInstance(Context context){
         if(sInstance == null){
             synchronized (MyDataBase.class){
                 //(3)Room.databaseBuilder 进行DataBase实例化,指定数据库保存的文件名,创建的DataBase的开销较大,建议涉及成单例模式.
                 sInstance = Room.databaseBuilder(context.getApplicationContext(),MyDataBase.class,"sampleDb")
                         .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                         .build();
             }
         }
     return sInstance;
    }
    //(2)必须定义哥无法方法,返回DAO数据库操作类
    abstract UserDao userDao();
    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE MyUser"
            +" ADD COLUMN  address TEXT ");
            Log.d(TAG,"MIGRATION_1_2 add address");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

                database.execSQL("ALTER TABLE MyUser"
                        +" ADD COLUMN  birthday INTEGER ");
                Log.d(TAG,"MIGRATION_2_3 add birthday");
        }
    };
}
