package com.sogou.teemo.mydictionary.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.sogou.teemo.mydictionary.MainActivity;
import com.sogou.teemo.mydictionary.db.TRDictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ParseUtils {
    public static final String TAG = ParseUtils.class.getSimpleName();


    public static List<TRDictionary> parseOrginDictFileFromAsset(Context context, String fileName,long maxCount){

        //将json数据变成字符串
        List<TRDictionary> wordList = new ArrayList<>();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            int count = 0;
            while ((line = bf.readLine()) != null) {
                String[] row  =  line.split("\t");
                if(row != null && row.length > 1){
                    String word = row[0];
                    String explation = row[1];
                    TRDictionary trDictionary = new TRDictionary();
                    trDictionary.from = "zh";
                    trDictionary.to = "en";
                    trDictionary.mWord = word;
                    trDictionary.mJson = explation;
                    wordList.add(trDictionary);
                    count++;
                    if(maxCount != 0){
                        if(count >= maxCount){
                            break;
                        }
                    }
//                    Log.d(TAG,"ParseUtils readline -word:"+word+",explation:"+explation);
                }
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
        return wordList;
    }
}
