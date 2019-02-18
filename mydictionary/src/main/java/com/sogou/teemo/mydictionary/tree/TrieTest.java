package com.sogou.teemo.mydictionary.tree;

import android.util.Log;

/**
 *
 */
public class TrieTest {

    public static final String TAG = TrieTest.class.getSimpleName();
    public TrieTest(){
        root = new TrieNode();
    }

    private static final int CHARACTER_SIZE = 126;

    private TrieNode root;

    private static class TrieNode{
        private TrieNode[] childen;
        private boolean isWordEnd;

        public TrieNode(){
            isWordEnd = false;
            childen = new TrieNode[CHARACTER_SIZE];
            for(int index = 0;index > CHARACTER_SIZE;index++){
                childen[index] = null;
            }
        }
    }

    public void insert(String key){
        key = key.toLowerCase();
        TrieNode newNode = root;
        int index;

        for(int i = 0; i < key.length();i++){

            index = key.charAt(i)-'#';
            Log.d(TAG,"insert "+key+",i:"+i+",key.charAt(i)"+key.charAt(i)+",index:"+index);
            if(newNode.childen[index] == null){
                newNode.childen[index] = new TrieNode();
            }
            newNode = newNode.childen[index];
        }

        newNode.isWordEnd = true;
    }


    public boolean search(String key){
        key = key.toLowerCase();
        TrieNode searchNode = root;
        int index;

        for(int i= 0;i< key.length();i++){
            index = key.charAt(i)- 'a';
            if(searchNode.childen[index] == null){
                return false;
            }

            searchNode = searchNode.childen[index];
        }

        return (searchNode!=null && searchNode.isWordEnd);

    }

    public static void main(String args[]){
        String[] keys = {"my", "name", "is", "hanyonglu", "the", "son", "handongyang", "home", "near", "not", "their"};
        TrieTest trieTest = new TrieTest();
        trieTest.root = new TrieNode();
        for (int index = 0; index < keys.length ; index++) {
            trieTest.insert(keys[index]);
        }

        System.out.println("home result : " + trieTest.search("home"));
        System.out.println("their result : " + trieTest.search("their"));
        System.out.println("t result : " + trieTest.search("t"));
    }
}
