package com.example.testjvm;

public class TestRunTime {
    public static final String tag ="feifei_tag";
    public String name = "feifei";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        TestRunTime testRunTime = new TestRunTime();
    }
}
