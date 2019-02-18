package com.sogou.teemo.mydictionary.tree;

import android.support.annotation.NonNull;

import com.sogou.teemo.mydictionary.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TernaryTreeUtils {

    public static final String TAG = "TernaryTreeUtils_";

    public static class TNode implements Comparable<TNode>{
        Character splitchar;
        TNode lokid;
        TNode eqkid;
        TNode hikid;
//        boolean isWord = false;
        public String prefix;
        public String explation;

        public String getWholeWord(){
            return prefix+splitchar;
        }

        @Override
        public int compareTo(@NonNull TNode tNode) {
            int value =  this.splitchar - tNode.splitchar;
            return value;
        }

        public boolean isWord(){
            return explation != null && explation.length()>0;
        }
    }

    public TNode root ;

    public TernaryTreeUtils(){
        System.out.println("TernaryTreeUtils created()");
    }

//    public void insertNode(String words,String explation){
//        TNode curNode = root;
//        System.out.println(TAG+"root Node is:"+(root == null?"":root.splitchar));
//        if(Utils.isEmpty(words)){
//            return;
//        }
//
//        int charIndex = 0;
//        while (charIndex <= words.length()-1){
//
//            Character character = words.charAt(charIndex);
//            if(curNode == null){
//                    curNode = new TNode();
//                    curNode.splitchar = character;
//                    System.out.println(TAG+" create Node:"+curNode.splitchar);
//            }
//
//            if(character.compareTo(curNode.splitchar) == 0){
//                if(charIndex == words.length()-1){ //如果是最后一个单词了
//                    curNode.isWord = true;
//                    curNode.explation = explation;
//                }else { //不是最后一个单词,则继续
//                    curNode = curNode.eqkid;
//                }
//                charIndex++;
//            }else if(character.compareTo(curNode.splitchar)>0){ //大于 : higher Node
//                curNode = curNode.hikid;
//            }else { //小于 :lower Node
//                curNode = curNode.lokid;
//            }
//        }
//    }


    public TNode insertNode(TNode p,String prefix,String words,String explataion){

        if(Utils.isEmpty(words)){
            return null;
        }

        Character character = words.charAt(0);

        if(p == null){
            p = new TNode();
            p.splitchar = character;
            p.prefix = prefix;
            System.out.println(TAG+"insertNode:"+p.splitchar+",prefix:"+p.prefix);
        }

        if(character.compareTo(p.splitchar) < 0){ //character < p.splitchar
            p.lokid = insertNode(p.lokid,prefix,words,explataion);
        }else if(character.compareTo(p.splitchar) ==0){ //character == p.splitchar
            if(words.length()==1){//只有一个单词
                p.explation = explataion;
                System.out.println(TAG+"insertNode 完成:"+explataion);
            }else { //还有其他要匹配
                String newprefix  = p.getWholeWord();
                p.eqkid = insertNode(p.eqkid,newprefix,words.substring(1),explataion);
            }
        }else { //character > p.splitchar
            p.hikid = insertNode(p.hikid,prefix,words,explataion);
        }

        return p;
    }

    /**
     * 精确搜索
     * @param words
     * @return
     */
    public String search(String words){
        TNode curNode = root;
        if(Utils.isEmpty(words)){
            return null;
        }

        int charIndex = 0;
        while(charIndex <= words.length()-1){
            if(curNode == null){
                break;
            }
            Character character = words.charAt(charIndex);
            if(character.compareTo(curNode.splitchar)>0){ //character 比 curNode 大
                curNode = curNode.hikid;
            }else if(character.compareTo(curNode.splitchar) ==0){ //character 与 curNode相等
                if(charIndex < words.length()-1){
                    curNode = curNode.eqkid;
                }else { //找到最后一个字符了. 此时查找到了整个单词
                    if(curNode.isWord()){
                        System.out.println(TAG+"找到了单词:"+words);
                    }
                }
                charIndex++;

            }else { //character 比 curNode 小
                curNode = curNode.lokid;
            }
        }

        if(curNode != null){
            return curNode.explation;
        }else {
            return  null;
        }


    }

    public List<TNode>searchLike(TNode root,String words){

        List<TNode> collections = new ArrayList<>();
        TNode curNode = root;
        if(Utils.isEmpty(words)){
            return null;
        }

        int charIndex = 0;
        while(charIndex <= words.length()-1){
            if(curNode == null){ //未找到 单词中 某个字母 最终退出。
                break;
            }
            Character character = words.charAt(charIndex);
            if(character.compareTo(curNode.splitchar)>0){ //character 比 curNode 大
                curNode = curNode.hikid;
            }else if(character.compareTo(curNode.splitchar) ==0){ //character 与 curNode相等
                if(charIndex < words.length()-1){
                    curNode = curNode.eqkid;
                }else { //找到最后一个字符了. 此时查找到了整个单词
                    //找到了字母组合，但是却未必是完整单词。
                    System.out.println(TAG+"找到了字母组合:"+words);
                    if(curNode.isWord()){
                        collections.add(curNode);
                        System.out.println(TAG+"searchLike add "+curNode.explation);
                    }
                    traverseTreeNode(curNode.eqkid,collections);
                }
                charIndex++;

            }else { //character 比 curNode 小
                curNode = curNode.lokid;
            }
        }

        return collections;
    }


    public void traverseTreeNode(TNode root, List<TNode> collection){
        if(collection == null){
            System.out.println(TAG+"traverseTreeNode - collection was null");
            return;
        }

        if(root == null){
            return;
        }

        if(root.isWord()){ //当前节点是一个单词
            System.out.println(TAG+"traverseTreeNode add "+root.explation);
            collection.add(root);
        }

        traverseTreeNode(root.lokid,collection);
        traverseTreeNode(root.eqkid,collection);
        traverseTreeNode(root.hikid,collection);



    }

    public boolean isWord(TNode treeNode){
        if(treeNode != null && !Utils.isEmpty(treeNode.explation)){
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[]args){
        ArrayList<String> words = new ArrayList<String>();
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
        words.add("ab");
        words.add("good");
        words.add("abc");
        words.add("abcd");
        words.add("abd");
        words.add("e");


        TernaryTreeUtils trieUtils = new TernaryTreeUtils();
        for(int i = 0; i< words.size();i++){
            System.out.println(TAG+"_insert:"+words.get(i));
            trieUtils.root = trieUtils.insertNode(trieUtils.root,"",words.get(i),words.get(i));
        }

//        System.out.println(TAG+"search weahter:"+trieUtils.search("weahter"));
//        System.out.println(TAG+"search today:"+trieUtils.search("today"));
//        System.out.println(TAG+"search good:"+trieUtils.search("good"));

        List<TNode> collections = trieUtils.searchLike(trieUtils.root,"a");
        if(collections != null){
            for(int i = 0;i< collections.size();i++){
                TNode node = collections.get(i);
                System.out.println("模糊搜索结果:"+node.explation);
            }
        }
    }

    public void preSearch(TNode p,String words){
        if(p == null){
            return;
        }

        Character character = words.charAt(0);
        if(character.compareTo(p.splitchar) < 0 ){
            preSearch(p.lokid,words);
        }else if(character.compareTo(p.splitchar) > 0){
            preSearch(p.hikid,words);
        }else {
            if(words.length() ==1){//最后一个单词
                traverse(p.eqkid);
                return;
            }else {
                preSearch(p.eqkid,words.substring(1));
            }
        }

    }
    /**
     * 遍历节点中的所有 叶子节点
     * @param p
     */
    public void traverse(TNode p){
        if(p == null){
            return;
        }
        traverse(p.lokid);

        if(p.isWord()){
            System.out.println(TAG+"traverse 找到根节点");
        }else {
            traverse(p.eqkid);
        }

        traverse(p.hikid);


    }
}
