package com.example.testjvm;

public class MainActivity {

    public static void main(String args[]){
        //字符串常量池
        System.out.println("Test.string_info == Test2.string_info:" + (Test.info_string == Test2.info_string));

        //int 常量池
        System.out.println("Test.info_int == Test2.info_int:" + (Test.info_int == Test2.info_int));
        System.out.println("Test.info_int1 == Test2.info_int1:" + (Test.info_int_1 == Test2.info_int_1));

        //float 和 double 没有实现常量池
        System.out.println("Test.info_float == Test2.info_float:" + (Test.info_float == Test2.info_float));

        System.out.println("new Test().tag == new Test2().tag:" + (new Test().tag == new Test2().tag));

        System.out.println("new Test().tag_int == new Test2().tag_int:" + (new Test().tag_int == new Test2().tag_int));

        System.out.println("new Test().tag_float == new Test2().tag_float):" + (new Test().tag_float == new Test2().tag_float));


        //对于局部变量: String 字面量 都会保存在运行时常池中, Integer [-128,127] 会保存在常量池中,float 和Double 两种浮点型数据的包装类 没有实现常量池
        Integer i1 = 40;
        Integer i2 = 40;
        System.out.println("i1==i2:" + (i1 == i2)); //true

        Integer i3 = 400;
        Integer i4 = 400;
        System.out.println("i3==i4:" + (i3 == i4));//false

        Float float5 = 4.0f;
        Float float6 = 4.0f;
        System.out.println("float5 == float6:" + (float5 == float6));//false

        Float d7 = 40.0f;
        Float d8 = 40.0f;
        System.out.println("d7==d8:" + (d7 == d8));//false

        Integer i9 = 40;
        Integer i10 = 40;
        Integer i11 = 0;
        System.out.println("i9==i10+i11:" + (i9 == i10 + i11));
        System.out.println("40==i10+i11:" + (40 == i10 + i11));
        //+操作不适用Integer对象,i10和i11进行自动茶香操作,进行数值相加，即i4==40，然后Integer对象无法与数值进行直接比较，所以i9自动拆箱转为int值40，最终这条语句转为40 == 40进行数值比较。


        Long l12 = 12L;
        Long l13 = 12L;
        System.out.println("l12==l13:" + (l12 == l13)); //true

        //字符串 //两个字符串变量 执行+号 拼接操作,实际上是调用了StringBuilder.append()方法和StringBuilder.toString()方法
        String str1 = "str";
        String str2 = "ing";
        String str3 = "str" + "ing";
        String str4 = str1 + str2;
        System.out.println("str3==str1+str2:" + (str3 == str1 + str2)); //true
        System.out.println("str3==str4:" + (str3 == str4)); //false
        String str5 = "string";
        System.out.println("str3==str5:" + (str3 == str5));//true

    }
}
