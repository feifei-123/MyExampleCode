package com.sogou.teemo.mydictionary.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "dictionary",indices ={@Index("mWord")} )
public class TRDictionary {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    public String mWord;
    public String mJson;
    public String from;
    public String to;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMWord() {
        return mWord;
    }

    public void setMWord(String mWord) {
        this.mWord = mWord;
    }

    public String getMJson() {
        return mJson;
    }

    public void setMJson(String mJson) {
        this.mJson = mJson;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
