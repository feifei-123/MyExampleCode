package com.example.testkotlin2

import kotlin.reflect.KProperty

// 类委托
interface Base{
    fun print()
}

class BaseImpl(val x:Int):Base{
    override fun print() {
        println("hello :$x")
    }
}

class Derived(b:Base):Base by b


//属性委托

class Example {
    var p: String by DelegateP()
    val lazyValue: String by lazy ({
        println("feifei computed!")
        "feifei Hello"
    })



}

class DelegateP{

    operator fun getValue(thisRef:Any?,property:KProperty<*>):String{
        return "feifei $thisRef,这里委托了 ${property.name} 属性"
    }

    operator fun setValue(thisRef: Any?,property: KProperty<*>,value:String){
        println("feifei $thisRef 的 ${property.name} 属性赋值为 $value")
    }
}


