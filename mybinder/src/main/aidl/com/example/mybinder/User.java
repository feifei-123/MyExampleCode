package com.example.mybinder;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{


    public String name = "feifei";
    public String age = "18";
    public String value ="";

    public User(){

    }
    protected User(Parcel in) {
        name = in.readString();
        age = in.readString();
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(age);
        dest.writeString(value);
    }


    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        value = dest.readString();
        age = dest.readString();
        name = dest.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
