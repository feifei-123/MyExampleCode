package com.example.testkotlin2

class Person(private var name:String) {

    private var description:String? = null;

    init {
        name = "feifei";
    }

    constructor(name: String,description:String):this(name){
        this.description = description;
    }


    internal fun sayHello(){
        println("Hello $name")
    }

}


// 主构造函数 位于类头中
// 主构造函数 携带的参数 会自动声明为 public的属性
// 主构造函数 中不能有任何的代码实现,如果有额外的代码实现 需要在构造方法中执行
// 次级构造函数 可以声明多个，次级构造函数中声明的参数 不会自动声明为类内的属性


// internal 修饰符 (和 public/private/protected 一样都是类范围修饰符) 是模块级别的访问修饰符,
//  何为模块？ 我们称被遗弃编译的一些列kotlin 文件为一个模块。

//kotlin中的类 默认是public ;并且会为每个变量和方法添加 final修饰符（final 和open相对，表示不可被重载）




