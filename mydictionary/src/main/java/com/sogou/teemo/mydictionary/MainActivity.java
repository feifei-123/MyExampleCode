package com.sogou.teemo.mydictionary;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sogou.teemo.mydictionary.db.AppDataBaseHelper;
import com.sogou.teemo.mydictionary.db.DictionaryExplain;
import com.sogou.teemo.mydictionary.db.TRDictionary;
import com.sogou.teemo.mydictionary.db.Word;
import com.sogou.teemo.mydictionary.utils.ThreadPoolUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = MainActivity.class.getSimpleName();
    public EditText et_search;
    public TextView tv_search_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setupView();
    }

    @Override
    public void onClick(View view) {
     long id = view.getId();
     if(id == R.id.btn_generate){
         doGenerateDict();
     }else if(id == R.id.btn_search){
         doSearchWord(et_search.getText().toString());
     }else{
         hideSoftKeyboard(this);
     }
    }


    public void initView(){
        et_search = findViewById(R.id.et_search);
        tv_search_result = findViewById(R.id.tv_search_result);
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
                tryInsertEnglishWordsBatch();
            }
        });

    }

    public void doSearchWord(final String words){

        ThreadPoolUtils.excute(new Runnable() {
            @Override
            public void run() {
                long startStamp = System.currentTimeMillis();
                Log.d(TAG,"doSearchWord - begin - "+words);
                TRDictionary trDictionary =  AppDataBaseHelper.Companion.getInstance(MainActivity.this).checkItem(words);
                long stamp2 = System.currentTimeMillis();
                Log.d(TAG,"doSearchWord - finish - spent:"+(stamp2-startStamp));

                final String result ;
                if(trDictionary == null){
                    result = null;
                }else {
                    DictionaryExplain explain = DictionaryExplain(trDictionary.getMJson());
                    result  = explain.getWord();
                }
                Log.d(TAG,"doSearchWord  - convert explain spent:"+(System.currentTimeMillis()-stamp2));

                long endStamp = System.currentTimeMillis();
                Log.d(TAG,"doSearchWord:- end - "+result+" - total interval:"+(endStamp - startStamp));
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_search_result.setText(result);
                    }
                });
            }
        });

    }


    public void doSearchWordLike(final String words){
        ThreadPoolUtils.excute(new Runnable() {
            @Override
            public void run() {
                long startStamp = System.currentTimeMillis();
                Log.d(TAG,"doSearchWordLike:- begin - "+words);
                List<TRDictionary> results = AppDataBaseHelper.Companion.getInstance(MainActivity.this).checkItemLike(words);
                long endStamp = System.currentTimeMillis();
                Log.d(TAG,"doSearchWordLike:- end - "+words+",interval:"+(endStamp - startStamp));
                final StringBuffer stringBuffer = new StringBuffer();

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

//        Log.d(TAG,"DictionaryExplain:"+(System.currentTimeMillis() - start));
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

   public void tryInsertEnglishWordsBatch(){
       long stamp = System.currentTimeMillis();
       Log.d(TAG,"tryInsertEnglishWordsBatch - begin");
       String json = "[{\"phonetic\": [{\"text\": \"ə\", \"type\": \"uk\", \"filename\": \"http://dlweb.sogoucdn.com/phonetic/sogouvoices/5_a_uk.mp3\"}, {\"text\": \"eɪ\", \"type\": \"usa\", \"filename\": \"http://dlweb.sogoucdn.com/phonetic/sogouvoices/5_a_usa.mp3\"}], \"keyword_score\": -1, \"word\": \"a\", \"usual\": [{\"values\": [\"每一（个）;任一（个）;一（个）\"], \"pos\": \"art.\"}, {\"values\": [\"英文字母表的第一字母\"], \"pos\": \"n.\"}], \"exchange_info\": {}}]";
       try {
           String jsonStr = getFromAssets("words.json");
           Gson gson = new Gson();
           List<Word> words = gson.fromJson(jsonStr,new TypeToken<List<Word>>(){}.getType());
           List<TRDictionary> dictList = new ArrayList<>();
           for(int i = 0 ;i< words.size() ;i++){
               Log.d(TAG,"tryInsertEnblighsWords - index:"+i);
               String source = words.get(i).en_word;
               String thWords = source + getRandomEnglishChar();
               String target = json;
               TRDictionary trDictionary = new TRDictionary();
               trDictionary.setMWord(thWords);
               trDictionary.setMJson(target);
               trDictionary.setFrom("en");
               trDictionary.setTo("cn");
               dictList.add(trDictionary);
           }
           doInsertEnglishBatch(dictList);
           Log.d(TAG,"tryInsertEnglishWordsBatch - end:"+words.size()+",interval:"+(System.currentTimeMillis() - stamp));
       }catch (Exception e){
           e.printStackTrace();
           Log.d(TAG,"tryInsertEnblighsWords exception:"+e.getLocalizedMessage());
       }finally {

       }
   }


//    public void doinsertDB(){
//        ThreadPoolUtils.excute(new Runnable() {
//            @Override
//            public void run() {
//                doInsert();
//            }
//        });
//    }

//    public void doInsert(){
//
//        String key1 = "滋味";
//        String value1 = "[{\"phonetic\": [{\"text\": \"zī wèi\"}], \"word\": \"滋味\", \"usual\": [{\"values\": [\"taste; relish; tang; flavour\"], \"pos\": \"n.\"}]}]";
//
//        String key2 = "身体";
//        String value2 = "[{\"phonetic\": [{\"text\": \"shēn tǐ\"}], \"word\": \"身体\", \"usual\": [{\"values\": [\"body\"], \"pos\": \"n.\"}, {\"values\": [\"health; corporeity\"], \"pos\": \"mix.\"}]}]";
//
//        String key3 = "解释";
//        String value3 = "[{\"phonetic\": [{\"text\": \"jiě shì\"}], \"word\": \"解释\", \"usual\": [{\"values\": [\"make clear\"], \"pos\": \"mix.\"}, {\"values\": [\"interpretation; explanation\"], \"pos\": \"n.\"}, {\"values\": [\"explain; expound; interpret\"], \"pos\": \"v.\"}]}]";
//
//        String key4 = "处理";
//        String value4 = "[{\"phonetic\": [{\"text\": \"chǔ lǐ\"}], \"word\": \"处理\", \"usual\": [{\"values\": [\"handle; dispose; manage; conduct\"], \"pos\": \"v.\"}, {\"values\": [\"deal with; processing; handling; process\"], \"pos\": \"mix.\"}]}]";
//
//        String key5 = "开始";
//        String value5 = "[{\"phonetic\": [{\"text\": \"kāi shǐ\"}], \"word\": \"开始\", \"usual\": [{\"values\": [\"initiation\"], \"pos\": \"n.\"}, {\"values\": [\"begin; start; initiate\"], \"pos\": \"v.\"}, {\"values\": [\"beginning; initial\"], \"pos\": \"mix.\"}]}]";
//
//        String key6 = "单词";
//        String value6 = "[{\"phonetic\": [{\"text\": \"dān cí\"}], \"word\": \"单词\", \"usual\": [{\"values\": [\"word\"], \"pos\": \"n.\"}, {\"values\": [\"【语】individual word\"], \"pos\": \"mix.\"}]}]";
//
//        String key7 = "可能";
//        String value7 = "[{\"phonetic\": [{\"text\": \"kě néng\"}], \"word\": \"可能\", \"usual\": [{\"values\": [\"possible\"], \"pos\": \"adj.\"}, {\"values\": [\"probably; be likely to; probable; possibility\"], \"pos\": \"mix.\"}]}]";
//
//        String key8 = "简单";
//        String value8 = "[{\"phonetic\": [{\"text\": \"jiǎn dān\"}], \"word\": \"简单\", \"usual\": [{\"values\": [\"simple; not complicated; simplicity\"], \"pos\": \"mix.\"}, {\"values\": [\"briefness; simpleness\"], \"pos\": \"n.\"}]}]";
//
//        String key9 = "统计";
//        String value9 = "[{\"phonetic\": [{\"text\": \"tǒng jì\"}], \"word\": \"统计\", \"usual\": [{\"values\": [\"statistics; count; census\"], \"pos\": \"mix.\"}]}]";
//
//        String key10 = "范围";
//        String value10 = "[{\"phonetic\": [{\"text\": \"fàn wéi\"}], \"word\": \"范围\", \"usual\": [{\"values\": [\"extent; purview\"], \"pos\": \"mix.\"}, {\"values\": [\"range; scope\"], \"pos\": \"n.\"}]}]";
//
//        Map<String,String> map = new HashMap<>();
//        for(int i = 0 ; i< 10000;i++){
//            map.put(key1+getRandomChar(),value1);
//            map.put(key2+getRandomChar(),value2);
//            map.put(key3+getRandomChar(),value3);
//            map.put(key4+getRandomChar(),value4);
//            map.put(key5+getRandomChar(),value5);
//            map.put(key6+getRandomChar(),value6);
//            map.put(key7+getRandomChar(),value7);
//            map.put(key8+getRandomChar(),value8);
//            map.put(key9+getRandomChar(),value9);
//            map.put(key10+getRandomChar(),value10);
//        }
//
//        Log.d(TAG,"test dictionary - 开始插入 ");
//        int index = 0;
//        for(String key:map.keySet()){
//            index++;
//            TRDictionary trDictionary = new TRDictionary();
//            trDictionary.setMWord(key);
//            trDictionary.setMJson(map.get(key));
//            Log.d(TAG,"test dictionary - 插入 - key:"+key+",index:"+index);
//            AppDataBaseHelper.Companion.getInstance(this).insertDictionaryItem(trDictionary);
//        }
//        Log.d(TAG,"test dictionary - 插入结束");
//    }
//
//    public static char getRandomChar() {
//        return (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
//    }
//
//
    public String getFromAssets(String fileName){
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
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


    public void doInsertEnglishBatch(List<TRDictionary> listDict){
        long start = System.currentTimeMillis();
        Log.d(TAG,"doInsertEnglishBatch begin:"+listDict.size());
        List<Long> results =AppDataBaseHelper.Companion.getInstance(this).insertAllDictionaryItems(listDict);
        Log.d(TAG,"doInsertEnglishBatch end - interval:"+(System.currentTimeMillis() - start)+",result.size:"+(results.size()));
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
        int length = (int)(Math.random()*10);
        Random rd = new Random();
        String str = "";
        for (int i = 0; i < length; i++) {
// 你想生成几个字符的，就把9改成几，如果改成１,那就生成一个随机字母．
            str = str + (char) (Math.random() * 26 + 'a');
        }
        return str;
    }


    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
