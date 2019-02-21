package com.sogou.teemo.mydictionary.encrypt;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.io.IOException;

public class EncryperUtils {

    public static void encrypt(Context ctxt, String dbName,
                               String passphrase) throws IOException {

        File originalFile = ctxt.getDatabasePath(dbName);
        if (originalFile.exists()) {
            Log.d("feifei","数据库文件存在,现在进行加密");

            File newFile =
                    File.createTempFile("sqlcipherutils", "tmp",
                            ctxt.getCacheDir());
            SQLiteDatabase db =
                    SQLiteDatabase.openDatabase(originalFile.getAbsolutePath(),
                            "", null,
                            SQLiteDatabase.OPEN_READWRITE);

            db.rawExecSQL(String.format("ATTACH DATABASE '%s' AS encrypted KEY '%s';",
                    newFile.getAbsolutePath(), passphrase));
            db.rawExecSQL("SELECT sqlcipher_export('encrypted')");
            db.rawExecSQL("DETACH DATABASE encrypted;");

            int version = db.getVersion();

            db.close();

            db =
                    SQLiteDatabase.openDatabase(newFile.getAbsolutePath(),
                            passphrase, null,
                            SQLiteDatabase.OPEN_READWRITE);
            db.setVersion(version);
            db.close();

            originalFile.delete();
            newFile.renameTo(originalFile);
        }else {
            Log.d("feifei","数据库文件不存在");
        }
    }

    public static void decrypt(Context ctxt,String dbName,
                               String passphrase) throws IOException {

        File originalFile = ctxt.getDatabasePath(dbName);
        if (originalFile.exists()) {
            Log.d("feifei","数据库文件存在,现在进行加密");

            File newFile = File.createTempFile("sqlcipherutils", "decrypt",
                            ctxt.getCacheDir());
            SQLiteDatabase db = SQLiteDatabase.openDatabase(originalFile.getAbsolutePath(),
                            passphrase, null,
                            SQLiteDatabase.OPEN_READWRITE);

            db.rawExecSQL(String.format("ATTACH DATABASE '%s' AS decrypted KEY '';",
                    newFile.getAbsolutePath(), passphrase));
            db.rawExecSQL("SELECT sqlcipher_export('decrypted')");
            db.rawExecSQL("DETACH DATABASE decrypted;");

            int version = db.getVersion();

            db.close();

            db = SQLiteDatabase.openDatabase(newFile.getAbsolutePath(),
                            "", null,
                            SQLiteDatabase.OPEN_READWRITE);
            db.setVersion(version);
            db.close();

            originalFile.delete();
            newFile.renameTo(originalFile);
        }else {
            Log.d("feifei","数据库文件不存在");
        }
    }

}
