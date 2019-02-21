package com.sogou.teemo.mydictionary;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import net.sqlcipher.database.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sogou.teemo.mydictionary.db.AppDataBaseHelper;
import com.sogou.teemo.mydictionary.db.DictionaryExplain;
import com.sogou.teemo.mydictionary.db.TRDictionary;
import com.sogou.teemo.mydictionary.db.Word;
import com.sogou.teemo.mydictionary.encrypt.DictionaryHelper;
import com.sogou.teemo.mydictionary.encrypt.EncryperUtils;
import com.sogou.teemo.mydictionary.map.MapTest;
import com.sogou.teemo.mydictionary.tree.TernaryTreeUtils;
import com.sogou.teemo.mydictionary.tree.TrieUtils;
import com.sogou.teemo.mydictionary.utils.ParseUtils;
import com.sogou.teemo.mydictionary.utils.ThreadPoolUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.sogou.teemo.mydictionary.encrypt.DictionaryHelper.PASSWORD;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = MainActivity.class.getSimpleName();
    public EditText et_search;
    public TextView tv_search_result;

    public CheckBox box_db,box_map,box_pretree,box_ternarytree,box_en_zh,box_zh_en;
    public CheckBox box_should_encrypt;

    public int repeateTimes = 14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setupView();
        SQLiteDatabase.loadLibs(this);
    }



    @Override
    public void onClick(View view) {
     long id = view.getId();
     if(id == R.id.btn_generate){
         doGenerateDict();
     }else if(id == R.id.btn_search){
         doSearchWord(et_search.getText().toString());
     }else if(id == R.id.btn_random_search){
         doRandomSearch();
     }else if(id == R.id.btn_db_encrypt){
         testEncrypt();
     }else if(id == R.id.btn_db_decrypt){
         testDecypt();
     }else if(id == R.id.btn_random_search_like){
         doRandomSearchLike();
     }else {
         hideSoftKeyboard(this);
     }
    }

    public void testEncrypt(){
        Log.d(TAG,"testEncrypt begins");
        try {
            EncryperUtils.encrypt(this,"dictionary.db",PASSWORD);
            Log.d(TAG,"testEncrypt finish");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG,"testEncrypt exception:"+e.getLocalizedMessage());
        }
    }

    public void testDecypt(){
        Log.d(TAG,"testEncrypt begins");
        try {
            EncryperUtils.decrypt(this,"dictionary.db",PASSWORD);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"testEncrypt finish");

    }

    public void initView(){
        et_search = findViewById(R.id.et_search);
        tv_search_result = findViewById(R.id.tv_search_result);

        box_db = findViewById(R.id.box_db);
        box_map = findViewById(R.id.box_map);
        box_pretree = findViewById(R.id.box_pretree);
        box_ternarytree = findViewById(R.id.box_ternarytree);
        box_en_zh = findViewById(R.id.box_en_zh);
        box_zh_en  = findViewById(R.id.box_zh_en);
        box_should_encrypt = findViewById(R.id.box_should_encrypt);


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                doSearchWordLike(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setupView(){
        tv_search_result.setMovementMethod(ScrollingMovementMethod.getInstance());
    }






    public void doGenerateDict(){

        ThreadPoolUtils.excute(new Runnable() {
            @Override
            public void run() {
                if(box_db.isChecked()){
                    tryInsertEnglishWordsBatch();
                }else if(box_map.isChecked()){
                    tryGenerateMap();
                }else if(box_pretree.isChecked()){
                    try2GenarateDictionaryTree();
                }else if(box_ternarytree.isChecked()){
                    try2GenarateTernaryTree();
                }

            }
        });
    }

    public void doRandomSearch(){
        ThreadPoolUtils.excute(new Runnable() {

            @Override
            public void run() {

                String method = "box_db";
                int count = 100;
                int sumeTime = 0;
                Log.d(TAG,"doRandomSearch begins");
                List<TRDictionary> words =  getTestData();
                if(words == null){
                    return;
                }
                int length = words.size();
                Log.d(TAG,"doRandomSearch length:"+length);
                for(int i = 0 ;i< count;i++){
                    double randowm = Math.random();
                    Log.d(TAG,"doRandomSearch randowm:"+randowm);
                    int index = (int)(randowm*length);
                    index--;
                    Log.d(TAG,"doRandomSearch index :"+index);
                    if(index < 0){
                        index = 0;
                    }
                    String theword = words.get(index).mWord;

                    long speedtime = 0;
                    if(box_db.isChecked()){
                        method = "box_db";
                        speedtime = doSearchWordThroughDB(theword);
                    }else if(box_map.isChecked()){
                        method = "box_map";
                        speedtime = doSearchWordThroughMap(theword);
                    }else if(box_pretree.isChecked()){
                        method = "box_pretree";
                        speedtime = doSearchWordThroughTree(theword);
                    }else if(box_ternarytree.isChecked()){
                        method = "box_ternarytree";
                        speedtime = doSearchWordThroughTernaryTree(theword);
                    }

                    sumeTime+=speedtime;

                }
                Log.d(TAG,"doRandomSearch avergage speed:"+(sumeTime*1.0/count)+",method:"+method);
            }
        });
    }

    public void doRandomSearchLike(){
        ThreadPoolUtils.excute(new Runnable() {

            @Override
            public void run() {

                String method = "";
                int count = 100;
                int sumeTime = 0;
                Log.d(TAG,"doRandomSearchLike begins");
                List<TRDictionary> words =  getTestData();
                if(words == null){
                    return;
                }
                int length = words.size();
                Log.d(TAG,"doRandomSearchLike length:"+length);
                for(int i = 0 ;i< count;i++){
                    double randowm = Math.random();
                    Log.d(TAG,"doRandomSearchLike randowm:"+randowm);
                    int index = (int)(randowm*length);
                    index--;
                    Log.d(TAG,"doRandomSearchLike index :"+index);
                    if(index < 0){
                        index = 0;
                    }
                    String theword = words.get(index).mWord;
//                    String theword = getRandomEnglishChar();

                    long speedtime = 0;
                    if(box_db.isChecked()){
                        method = "box_db";
                        speedtime = doSearchLikeDB(theword);
                    }else if(box_map.isChecked()){
                        method = "box_map";
                        speedtime = doSearchWordThroughMap(theword);
                    }else if(box_pretree.isChecked()){
                        method = "box_pretree";
                        speedtime = doSearchLikeTree(theword);
                    }else if(box_ternarytree.isChecked()){
                        method = "box_ternarytree";
                        speedtime = doSearchLikeTernaryTree(theword);
                    }

                    sumeTime+=speedtime;

                }

                Log.d(TAG,"doRandomSearchLike avergage speed:"+(sumeTime*1.0/count));
            }
        });
    }


    List<TRDictionary> dictList = null;
    public List<TRDictionary> getOriginDictionData(){


        String fileName = "zhen_simple.dict";
        if(box_zh_en.isChecked()){
            fileName = "zhen_simple.dict";
        }else if(box_en_zh.isChecked()){
            fileName = "enzh_simple.dict";
        }
        List<TRDictionary> dictList = ParseUtils.parseOrginDictFileFromAsset(this,fileName,10000);
        return dictList;

    }

    public List<TRDictionary> getTestData(){
        String fileName = "zhen_simple.dict";
        if(box_zh_en.isChecked()){
            fileName = "zhen_simple.dict";
        }else if(box_en_zh.isChecked()){
            fileName = "enzh_simple.dict";
        }
        List<TRDictionary> dictList = ParseUtils.parseOrginDictFileFromAsset(this,fileName,1000);
        return dictList;
    }


    public List<Word>getTestWord(){
        List<Word> words = null;
        try {
            String jsonStr = getFromAssets("words.json");
            Gson gson = new Gson();
            words = gson.fromJson(jsonStr,new TypeToken<List<Word>>(){}.getType());

        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG,"getTestWord exception:"+e.getLocalizedMessage());
        }finally {

        }
        return words;
    }

    public void doSearchWord(final String words){

        ThreadPoolUtils.excute(new Runnable() {
            @Override
            public void run() {
               if(MainActivity.this.box_db.isChecked()){
                   doSearchWordThroughDB(words);
               }else if(MainActivity.this.box_pretree.isChecked()){
                   doSearchWordThroughTree(words);
               }else if(MainActivity.this.box_ternarytree.isChecked()){
                   doSearchWordThroughTernaryTree(words);
               }
               else if(MainActivity.this.box_map.isChecked()){
                   doSearchWordThroughMap(words);
               }
            }
        });

    }


    public void doSearchWordLike(final String words){
        ThreadPoolUtils.excute(new Runnable() {
            @Override
            public void run() {
                if(box_db.isChecked()){
                    doSearchLikeDB(words);
                }else if(box_pretree.isChecked()){
                    doSearchLikeTree(words);
                }else if(box_ternarytree.isChecked()){
                    doSearchLikeTernaryTree(words);
                }
            }
        });

    }


    public DictionaryExplain DictionaryExplain(String jsonString){
        DictionaryExplain result = null;
        long start = System.currentTimeMillis();
        Gson gson = new Gson();
        if(TextUtils.isEmpty(jsonString)){
            result = null;
        }else {
            List<DictionaryExplain> explains =  gson.fromJson(jsonString,new TypeToken<List<DictionaryExplain>>(){}.getType());
            result =   explains.get(0);
        }

        return  result;
    }


   public void tryInsertEnblighsWords(){
       Log.d(TAG,"tryInsertEnblighsWords - begin");
       try {
           String jsonStr = getFromAssets("words.json");
           Gson gson = new Gson();
           List<Word> words = gson.fromJson(jsonStr,new TypeToken<List<Word>>(){}.getType());
           for(int i = 0 ;i< words.size() ;i++){
               Log.d(TAG,"tryInsertEnblighsWords - index:"+i);
               String source = words.get(i).en_word;
               doInsertEnglish(source);
               doInsertEnglish(source);
               doInsertEnglish(source);
               doInsertEnglish(source);
               doInsertEnglish(source);
               doInsertEnglish(source);
               doInsertEnglish(source);
           }
           Log.d(TAG,"tryInsertEnblighsWords - end:"+words.size());
       }catch (Exception e){
           e.printStackTrace();
           Log.d(TAG,"tryInsertEnblighsWords exception:"+e.getLocalizedMessage());
       }finally {

       }
   }





    //------------------ 前缀树 ------------
    TrieUtils trieUtils = new TrieUtils();

    //(1)插入
    public void try2GenarateDictionaryTree(){


       long stamp = System.currentTimeMillis();
       Log.d(TAG,"try2GenarateDictionaryTree - begin");
       try {
           String jsonStr = getFromAssets("words.json");
           Gson gson = new Gson();
//           List<Word> words = gson.fromJson(jsonStr,new TypeToken<List<Word>>(){}.getType());
           List<TRDictionary>words = getOriginDictionData();
           for(int i = 0 ;i< words.size() ;i++){
               trieUtils.insertNode(words.get(i).mWord,words.get(i).mJson);
           }

           Log.d(TAG,"try2GenarateDictionaryTree - end:"+words.size()+"NodeCount:"+trieUtils.nodeCount+",interval:"+(System.currentTimeMillis() - stamp));
       }catch (Exception e){
           e.printStackTrace();
           Log.d(TAG,"try2GenarateDictionaryTree exception:"+e.getLocalizedMessage());
       }finally {

       }

    }

//   public void tryinsertTreeNode(String source){
//       trieUtils.insertNode(source,getExplation(source));
//       for(int i = 0;i< repeateTimes;i++){
//           String tmpSource = source+getRandomEnglishChar();
//           trieUtils.insertNode(tmpSource,getExplation(tmpSource));
//       }
//   }

    //(2)搜索
    public long doSearchWordThroughTree(final String words){
        long stamp = System.currentTimeMillis();
        Log.d(TAG,"doSearchWordThroughTree - begin:"+words);
        //Boolean result =  trieTest.search(words);
        String searchResult = trieUtils.search(words);
        long stamp2 = System.currentTimeMillis();
        Log.d(TAG,"doRandomSearch doSearchWordThroughTree - end - result: "+searchResult+" - interval:"+(stamp2-stamp));

        return (stamp2-stamp);
    }


    //(3)模糊搜索
    public long doSearchLikeTree(final String words){

        long startStamp = System.currentTimeMillis();
        Log.d(TAG,"doSearchLikeTree:- begin - "+words);
        List<TrieUtils.TreeNode> results = trieUtils.searchLike(words);
        long endStamp = System.currentTimeMillis();
        Log.d(TAG,"doSearchLikeTree:- end - "+words+",interval:"+(endStamp - startStamp));
        final StringBuffer stringBuffer = new StringBuffer();

        if(results == null){
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_search_result.setText("");
                }
            });
        }else {
            for(int i = 0 ;i < results.size() ;i++){
                String tmp  = results.get(i).getWholeWord()+"\n";
                stringBuffer.append(tmp);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_search_result.setText(stringBuffer.toString());
                    }
                });
            }
        }

        return (endStamp - startStamp);
    }

   String explainJson = "[{\"phonetic\": [{\"text\": \"ə\", \"type\": \"uk\", \"filename\": \"http://dlweb.sogoucdn.com/phonetic/sogouvoices/5_a_uk.mp3\"}, {\"text\": \"eɪ\", \"type\": \"usa\", \"filename\": \"http://dlweb.sogoucdn.com/phonetic/sogouvoices/5_a_usa.mp3\"}], \"keyword_score\": -1, \"word\": \"a\", \"usual\": [{\"values\": [\"每一（个）;任一（个）;一（个）\"], \"pos\": \"art.\"}, {\"values\": [\"英文字母表的第一字母\"], \"pos\": \"n.\"}], \"exchange_info\": {}}]";

   public String getExplation(String source){
       String result = source+"_target";
       result = explainJson;
       return result;
   }




   //------------------------ 三元查找树 --------
   TernaryTreeUtils ternaryTreeUtils = new TernaryTreeUtils();

   //(1)插入
   public void try2GenarateTernaryTree(){
       long stamp = System.currentTimeMillis();
       Log.d(TAG,"try2GenarateTernaryTree - begin");
       try {
           String jsonStr = getFromAssets("words.json");
           Gson gson = new Gson();
//           List<Word> words = gson.fromJson(jsonStr,new TypeToken<List<Word>>(){}.getType());
           List<TRDictionary> words = getOriginDictionData();
           for(int i = 0 ;i< words.size() ;i++){
//               String source = words.get(i).mWord;
               ternaryTreeUtils.root =  ternaryTreeUtils.insertNode(ternaryTreeUtils.root,"",words.get(i).mWord,words.get(i).mJson);
           }
           Log.d(TAG,"try2GenarateTernaryTree - end:"+words.size()+",actial count:"+ternaryTreeUtils.nodeCount+",interval:"+(System.currentTimeMillis() - stamp));
       }catch (Exception e){
           e.printStackTrace();
           Log.d(TAG,"try2GenarateTernaryTree exception:"+e.getLocalizedMessage());
       }finally {

       }
   }

//    public void tryinsertTernaryTreeNode(String source){
//       ternaryTreeUtils.root =  ternaryTreeUtils.insertNode(ternaryTreeUtils.root,"",source,getExplation(source));
//       for(int i = 0;i< repeateTimes;i++){
//            String tmpSource = source+getRandomEnglishChar();
//           ternaryTreeUtils.root =  ternaryTreeUtils.insertNode(ternaryTreeUtils.root,"",tmpSource,getExplation(tmpSource));
//        }
//    }


    //(2)搜索
    public long doSearchWordThroughTernaryTree(final String words){
        long stamp = System.currentTimeMillis();
        Log.d(TAG,"doSearchWordThroughTernaryTree - begin:"+words);
        //Boolean result =  trieTest.search(words);
        String searchResult = ternaryTreeUtils.search(words);
        long stamp2 = System.currentTimeMillis();
        Log.d(TAG,"doRandomSearch doSearchWordThroughTernaryTree - end - result: "+searchResult+" - interval:"+(stamp2-stamp));
        return (stamp2-stamp);
    }

    //(3)模糊搜索
    public long doSearchLikeTernaryTree(final String words){

        long startStamp = System.currentTimeMillis();
        Log.d(TAG,"doSearchLikeTernaryTree:- begin - "+words);
        List<TernaryTreeUtils.TNode> results = ternaryTreeUtils.searchLike(ternaryTreeUtils.root,words);
        long endStamp = System.currentTimeMillis();

        final StringBuffer stringBuffer = new StringBuffer();

        if(results == null){
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_search_result.setText("");
                }
            });
        }else {
            for(int i = 0 ;i < results.size() ;i++){
                String tmp  = results.get(i).getWholeWord()+"\n";
                stringBuffer.append(tmp);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_search_result.setText(stringBuffer.toString());
                    }
                });
            }
        }

        Log.d(TAG,"doSearchLikeTernaryTree:- end - "+words+",interval:"+(endStamp - startStamp)+",result:"+stringBuffer.toString());

        return (endStamp - startStamp);
    }



    //------------------------ hashMap --------

   MapTest mapTest = new MapTest();

   //(1)插入
   public void tryGenerateMap(){
       long stamp = System.currentTimeMillis();
       Log.d(TAG,"tryGenerateMap - begin");
       try {
//           String jsonStr = getFromAssets("words.json");
//           Gson gson = new Gson();
//           List<Word> words = gson.fromJson(jsonStr,new TypeToken<List<Word>>(){}.getType());
           List<TRDictionary> words = getOriginDictionData();
           for(int i = 0 ;i< words.size() ;i++){
               mapTest.insert(words.get(i).mWord,words.get(i).mJson);
           }
           Log.d(TAG,"tryGenerateMap - end:"+words.size()+",actial size:"+mapTest.getSize()+",interval:"+(System.currentTimeMillis() - stamp));
       }catch (Exception e){
           e.printStackTrace();
           Log.d(TAG,"tryGenerateMap exception:"+e.getLocalizedMessage());
       }finally {

       }

   }

//   public void doInsertMap(String source){
//       mapTest.insert(source,getExplation(source));
//       for(int i = 0;i< repeateTimes;i++){
//           String tmpSource = source+getRandomEnglishChar();
//           mapTest.insert(tmpSource,getExplation(source));
//       }
//   }

   //(2)搜索
    public long doSearchWordThroughMap(final String words){
        long stamp = System.currentTimeMillis();
        Log.d(TAG,"doSearchWordThroughMap - begin:"+words);
        final String result =  mapTest.search(words);
        long stamp2 = System.currentTimeMillis();
        Log.d(TAG,"doRandomSearch doSearchWordThroughMap - end interval:"+(stamp2-stamp)+",result:"+result);
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(result != null){
                    tv_search_result.setText(result);
                }
            }
        });
        return (stamp2-stamp);
    }


    //--------------------- DB ------------------

    boolean insertNature = true;

    public void tryInsertEnglishWordsBatch(){
        long stamp = System.currentTimeMillis();
        Log.d(TAG,"tryInsertEnglishWordsBatch - begin insertNature:"+insertNature);

        try {
            List<TRDictionary> dictList = getOriginDictionData();
            doInsertEnglishBatch(dictList);
            Log.d(TAG,"tryInsertEnglishWordsBatch - end -actial size:"+dictList.size()+",interval:"+(System.currentTimeMillis() - stamp));
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG,"tryInsertEnblighsWords exception:"+e.getLocalizedMessage());
        }finally {

        }

        insertNature = false;



    }

    public String getFromAssets(String fileName){
        //将json数据变成字符串
        StringBuffer stringBuilder = new StringBuffer();
        try {
            //获取assets资源管理器
            AssetManager assetManager = MainActivity.this.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    //(1)插入
    public void doInsertEnglishBatch(List<TRDictionary> listDict){
        long start = System.currentTimeMillis();
        Boolean shouldEncrypt = box_should_encrypt.isChecked();
        Log.d(TAG,"doInsertEnglishBatch:"+shouldEncrypt);
        if(shouldEncrypt){
            DictionaryHelper.getInstance(this).insert(listDict);
        }else {
            List<Long> results =AppDataBaseHelper.Companion.getInstance(this).insertAllDictionaryItems(listDict);
        }
    }

    public void doInsertEnglish(String source){
        String words = source + getRandomEnglishChar();
//        String words = source;
        String target = words+"_target";
        TRDictionary trDictionary = new TRDictionary();
        trDictionary.setMWord(words);
        trDictionary.setMJson(target);
        trDictionary.setFrom("en");
        trDictionary.setTo("cn");
        Log.d(TAG,"test dictionary - 插入 - key:"+words);
        AppDataBaseHelper.Companion.getInstance(this).insertDictionaryItem(trDictionary);

    }
    public String getRandomEnglishChar(){
        int length = (int)(Math.random()*5);
        Random rd = new Random();
        String str = "";
        for (int i = 0; i < length; i++) {
// 你想生成几个字符的，就把9改成几，如果改成１,那就生成一个随机字母．
            str = str + (char) (Math.random() * 26 + 'a');
        }
        return str;
    }


    //(2)搜索
    public long  doSearchWordThroughDB(final String words){

        long stamp1 = System.currentTimeMillis();
        TRDictionary trDictionary = null;
        Boolean shouldEncrypt = box_should_encrypt.isChecked();
         if(shouldEncrypt){
             trDictionary  = DictionaryHelper.getInstance(this).queryDictionary(words);
         }else {
             trDictionary =  AppDataBaseHelper.Companion.getInstance(MainActivity.this).checkItem(words);
         }
        long stamp2 = System.currentTimeMillis();
        Log.d(TAG,"doRandomSearch shouldEncrypt:"+shouldEncrypt+",time:"+(stamp2-stamp1)+",word:"+words+",dictionry result:"+(trDictionary == null?"":trDictionary.getMWord()));

        final String result ;
        if(trDictionary == null){
            result = null;
        }else {
            DictionaryExplain explain = DictionaryExplain(trDictionary.getMJson());
            result  = explain.getWord();
        }

        long endStamp = System.currentTimeMillis();
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_search_result.setText(result);
            }
        });
        return (stamp2-stamp1);
    }

    //(3)模糊搜索
    public long doSearchLikeDB(final String words){
        long startStamp = System.currentTimeMillis();
//        Log.d(TAG,"doSearchLikeDB:- begin - "+words);
        Boolean shouldEncrypt = box_should_encrypt.isChecked();
        List<TRDictionary> results = null;
        if(shouldEncrypt){
            results = DictionaryHelper.getInstance(this).queryDictionaryLike(words);
        }else {
            results = AppDataBaseHelper.Companion.getInstance(MainActivity.this).checkItemLike(words);
        }

        long endStamp = System.currentTimeMillis();
        Log.d(TAG,"doSearchLikeDB :"+(endStamp - startStamp)+",words:"+words);
        final StringBuffer stringBuffer = new StringBuffer();
//
        for(int i = 0 ;i < results.size() ;i++){
            String tmp  = results.get(i).getMWord()+"\n";
            stringBuffer.append(tmp);
        }
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_search_result.setText(stringBuffer.toString());
            }
        });
        long endStamp2 = System.currentTimeMillis();
//        Log.d(TAG,"doSearchLikeDB  Whole :"+(endStamp - startStamp)+",words:"+words+"，reuslt:"+stringBuffer.toString());
        return (endStamp -startStamp);
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
