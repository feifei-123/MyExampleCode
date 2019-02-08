package com.sogou.teemo.mydictionary.db;

public class Word {
	public int _id;

	public String en_word;
	public String bn_word;
	public String status;
	
	public Word(String english, String bangla) {
		this.en_word = english;
		this.bn_word = bangla;
	}
	
	public Word(int id, String english, String bangla, String status) {
		this._id = id;
		this.en_word = english;
		this.bn_word = bangla;
		this.status = status;
	}

	@Override
	public String toString() {
		return "(english=" + en_word + ", bangla=" + bn_word + ")";
	}
}
