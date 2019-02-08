package com.sogou.teemo.mydictionary.db

import com.google.gson.Gson
import java.util.*

class DictionaryExplain {
    var phonetic:List<ThePhonetic>?= null
    var keyword_score:Int? = null
    var word:String? = null
    var usual:List<TheUsual>? = null
    var exchange_info:Any? = null

   public  fun toJsonString():String{
       var arrayItem = Array<DictionaryExplain>(1,{this})
       var gson = Gson()
       return  gson.toJson(arrayItem);
    }
}

class ThePhonetic{
    var text:String? = null
    var type:String? = null
    var filename:String? = null
}

class TheUsual{
    var values:List<String>? = null
    var pos:String? = null
}


