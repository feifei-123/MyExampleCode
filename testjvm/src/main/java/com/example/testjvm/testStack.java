package com.example.testjvm;

public class testStack {

    static int count=0;
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            recursion();
        } catch (Throwable e) {
            // TODO: handle exception
            System.out.println("栈的深度是："+count);
            e.printStackTrace();
        }
    }

    public static void recursion()
    {
        count++;
        recursion();
    }

    public String aab = "aaa";
}
