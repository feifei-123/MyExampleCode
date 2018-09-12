package com.example.myroom;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public static Date fromTimesStamp(Long value){
        return value == null?null:new Date(value);
    }
    @TypeConverter
    public static Long dataToTimeStamp(Date date){
        return ((date == null?0:date.getTime()));

    }

}
