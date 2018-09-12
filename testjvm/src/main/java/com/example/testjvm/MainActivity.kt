package com.example.testjvm

import android.app.Activity
import android.os.Bundle

import com.example.testjvm.JvmMaxHeapMemory.jvmInfo

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testMaxHeapMemory()
    }

    fun testMaxHeapMemory() {
        val b1 = ByteArray(1 * 1024 * 1024)
        System.out.println("分配了1M内存")
        jvmInfo()
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val b2 = ByteArray(6 * 1024 * 1024)
        System.out.println("分配了4M内存")
        jvmInfo()
    }

    companion object {

        internal var count = 0
        val A: String
        val B: String

        init {
            A = "ab"
            B = "cd"
        }

        fun main(args: Array<String>) {
            //        // TODO Auto-generated method stub
            //        try {
            //            recursion();
            //        } catch (Throwable e) {
            //            // TODO: handle exception
            //            System.out.println("栈的深度是："+count);
            //            e.printStackTrace();
            //        }

            //        String a = new Test().aaa;
            //        String b = new testStack().aab;
            //        System.out.print("a==b?"+(a==b));


            //final 修饰的静态变量、属性、和局部变量 都是常量(一旦赋值就无法更改),它们会统统缓存到运行时常量池中
            //类属性和对象属性所有的字面量:"info"、1、2L、3.0f、4.0d  都会保存在运行时长常量池中。
            //局部变量中的 int 仅仅[-128,127] 中的值 才会缓存到运行时常量池中;float 和double 均未实现运行时常量池

            //凡是 在编辑期间能够确定的值(String)，都可以视为一个常量,会被保存在运行时常量池中: 如: 字面量 和 被final 修饰的 类属性、对象属性、局部变量(必须被赋予初始值)


            //字符串常量池
            System.out.println("Test.string_info == Test2.string_info:" + (Test.info_string === Test2.info_string))//true

            //int 常量池
            System.out.println("Test.info_int == Test2.info_int:" + (Test.info_int == Test2.info_int))//true
            System.out.println("Test.info_int1 == Test2.info_int1:" + (Test.info_int_1 == Test2.info_int_1))//true

            //float 常量池
            System.out.println("Test.info_float == Test2.info_float:" + (Test.info_float == Test2.info_float))

            System.out.println("new Test().tag == new Test2().tag:" + (Test().tag === Test2().tag))

            System.out.println("new Test().tag_int == new Test2().tag_int:" + (Test().tag_int == Test2().tag_int))

            System.out.println("new Test().tag_float == new Test2().tag_float):" + (Test().tag_float == Test2().tag_float))


            //对于局部变量: String 字面量 都会保存在运行时常池中, Integer [-128,127] 会保存在常量池中,float 和Double 两种浮点型数据的包装类 没有实现常量池
            val i1 = 40
            val i2 = 40
            System.out.println("i1==i2:" + (i1 === i2)) //true

            val i3 = 400
            val i4 = 400
            System.out.println("i3==i4:" + (i3 === i4))//false

            val float5 = 4.0f
            val float6 = 4.0f
            System.out.println("float5 == float6:" + (float5 === float6))//false

            val d7 = 40.0
            val d8 = 40.0
            System.out.println("d7==d8:" + (d7 === d8))//false

            val i9 = 40
            val i10 = 40
            val i11 = 0
            System.out.println("i9==i10+i11:" + (i9 == i10 + i11))
            System.out.println("40==i10+i11:" + (40 == i10 + i11))
            //+操作不适用Integer对象,i10和i11进行自动茶香操作,进行数值相加，即i4==40，然后Integer对象无法与数值进行直接比较，所以i9自动拆箱转为int值40，最终这条语句转为40 == 40进行数值比较。


            val l12 = 12L
            val l13 = 12L
            System.out.println("l12==l13:" + (l12 === l13)) //true

            //字符串 //两个字符串变量 执行+号 拼接操作,实际上是调用了StringBuilder.append()方法和StringBuilder.toString()方法
            val str1 = "str"
            val str2 = "ing"
            val str3 = "str" + "ing"
            val str4 = str1 + str2
            System.out.println("str3==str1+str2:" + (str3 === str1 + str2)) //true
            System.out.println("str3==str4:" + (str3 === str4)) //false
            val str5 = "string"
            System.out.println("str3==str5:" + (str3 === str5))//true


            val s = A + B
            val t = "abcd"
            System.out.println("s == t :" + (s === t)) //false


        }

        fun recursion() {
            count++
            recursion()
        }
    }
}
