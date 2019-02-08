package com.example.testuiautomator;

public class Person {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) throws Exception{
        this.sex = sex;
    }


    public String eat(String food){
        return food;
    }
    private String name;
    private int sex;

}
