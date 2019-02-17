package com.sogou.teemo.mydictionary.tree;

import android.arch.persistence.room.util.StringUtil;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrieUtils {

    public static final String TAG = "TrieUtils_";

    public  TrieUtils(){
        root = new TreeNode();
    }

    public TreeNode root;


    public static class TreeNode{
        public char label; //节点的名称,在钱准树里是单个字母
        public HashMap<Character,TreeNode> sons = null;//使用哈希映射存放子节点。哈希便于确认是否已经添加过某个字母对应的节点
        String prefix = null;
        String explanation = null;

        public TreeNode(){
                label = ' ';
                prefix = "";
                explanation = "";
                sons = new HashMap<>();

        }
        public TreeNode(char l,String pre,String exp){
            label = l;
            prefix = pre;
            explanation = exp;
            sons = new HashMap<>();
        }

        @Override
        public String toString(){
            String info = "";
            info = "prefix:"+prefix+",label:"+label+",explation:"+explanation+",explation.size:"+explanation.length();
            return info;
        }

        public String getWholeWord(){
            return prefix+label;
        }
    }


    public long nodeCount = 0;
    public void insertNode(final String word,String explation){

        if(word == null || word.equals("")){
            return ;
        }

        TreeNode curNode = root;

        System.out.println(TAG+"_"+word+":"+word.length());

        for(int i = 0;i< word.length();i++){
            System.out.println(TAG+"_"+word+" word.charAt("+i+"):"+word.charAt(i));
            Character character = word.charAt(i);
            if(curNode.sons.containsKey(character)){
                curNode = curNode.sons.get(character);
            }else {
                TreeNode son = new TreeNode();
                son.label = character;
                son.prefix = curNode.prefix+curNode.label;
                curNode.sons.put(character,son);
                curNode = son;
                nodeCount++;
            }
            if(i == word.length() -1){
                curNode.explanation = explation;
                System.out.println(TAG+"_insertNode > "+curNode.toString());
            }
        }
    }

    public String search(String word){

        if(word == null || word.equals("")){
            return null;
        }

        TreeNode curNode = root;

        for(int i = 0 ;i < word.length() ;i++){
            Character character = word.charAt(i);
            if(curNode.sons.containsKey(character)){
                curNode = curNode.sons.get(character);
            }else { //匹配失败
                return  null;
            }
        }

        //for 循环顺利退出,说明所有单词都匹配成功了. 此时有两种情况，匹配出来的单词是一个完整单词,匹配出来的单词word 是一个单词的一部分。此时可根据explatation来判断这两种情况
        if(!isEmpty(curNode.explanation)){ //匹配成功，且是一个单词

            return curNode.explanation;
        }else {//匹配成功，但是 不是已录入的单词
            return  null;
        }
    }

    public List<TreeNode> searchLike(String word){

        List<TreeNode> result = new ArrayList<>();

        if(word == null || word.equals("")){
            return null;
        }

        TreeNode curNode = root;

        for(int i = 0 ;i < word.length() ;i++){
            Character character = word.charAt(i);
            if(curNode.sons.containsKey(character)){
                curNode = curNode.sons.get(character);
            }else { //匹配失败
                return  null;
            }
        }

        //for 循环顺利退出,说明所有单词都匹配成功了. 此时有两种情况，匹配出来的单词是一个完整单词,匹配出来的单词word 是一个单词的一部分。此时可根据explatation来判断这两种情况
        traverseTreeNode(curNode,result);
        return result;
    }

    /**
     * 遍历以root为根节点的树,并收集遍历过程中找到的单词
     * @param root
     */
    public void traverseTreeNode(TreeNode root,List<TreeNode>collection){
        if(collection == null){
            System.out.println(TAG+"traverseTreeNode - collection was null");
            return;
        }

        if(isWord(root)){ //当前节点是一个单词
            System.out.println(TAG+"traverseTreeNode add "+root.getWholeWord());
            collection.add(root);
        }

        if(root.sons == null || root.sons.size() == 0){ // 没有儿子节点了，则停止
            return;
        }else {
            for(Character character: root.sons.keySet()){
                TreeNode node = root.sons.get(character);
                traverseTreeNode(node,collection);
            }
        }
    }


    public boolean isEmpty(String str){
        return str == null || str.equals("");
    }

    public boolean isWord(TreeNode treeNode){
        if(treeNode != null && !isEmpty(treeNode.explanation)){
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[]args){

        ArrayList<String>words = new ArrayList<String>();
        words.add("w");
        words.add("we");
        words.add("wear");
        words.add("were");
        words.add("well");
        words.add("weahter");
        words.add("weird");
        words.add("a");
        words.add("today");
        words.add("is");
        words.add("a");
        words.add("good");

        TrieUtils trieUtils = new TrieUtils();
        for(int i = 0; i< words.size();i++){
            System.out.println(TAG+"_insert:"+words.get(i));
            trieUtils.insertNode(words.get(i),words.get(i)+"_tagert");
        }

//        System.out.println(TAG+"+search we:"+trieUtils.search("we"));
//
//        System.out.println(TAG+"search family:"+trieUtils.search("family"));
//
//        System.out.println(TAG+"search hello:"+trieUtils.search("hello"));

        trieUtils.searchLike("wea");

    }

}
