package com.sogou.teemo.mydictionary.map;

import java.util.HashMap;

public class MapTest {

    HashMap<String,String> dictMap = new HashMap<>();
    public void insert(String word,String explain){
        dictMap.put(word,explain);
    }

    public String search(String searchWord){
       return dictMap.get(searchWord);
    }

    public int getSize(){
        return  dictMap.size();
    }
}
