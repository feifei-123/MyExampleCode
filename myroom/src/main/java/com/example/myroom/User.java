package com.example.myroom;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.graphics.Bitmap;

import java.util.Date;

//Entity定义数据实体,tableName 指定表名
@Entity(tableName = "MyUser",indices = {@Index("first_name")})
public class User {

    //PrimaryKey指定主键
    @PrimaryKey(autoGenerate = true)
    private int uid;

    //ColumnInfo指定列名
    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    private int age;

    private String address;

    private Date birthday;

    //Ignore 忽略某个字段
    @Ignore
    private Bitmap bitmap;

    //提供getter和setter 供Room框架调用
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    @Override
    public String toString() {
        return "User firstName:" + firstName +",lastName:"+lastName+ ",Address:"+address+",birthday:"+birthday+", uid=" + uid;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (uid != user.uid) return false;
        return false;
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        return result;
    }



}
