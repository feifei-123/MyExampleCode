package com.example.testkotlin2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        testInline(10,{
//            Log.d("feifei","内联函数")
//            mytoast("这是测试扩展", Toast.LENGTH_LONG)
//        })
//

        load {
            Log.d("feifei","start load")
            delay(5,TimeUnit.SECONDS)
            Log.d("feifei","finish load")
            "this is a bitmap from alubm";
        } then{
            tv_info.text = it;
            Toast.makeText(this,it,Toast.LENGTH_LONG)
        }
    }

    override fun onResume() {
        super.onResume()


//        testSets();
//
//        testInline();

//        testTribleOperation();

//        testOutFun()

//        testIn()

//        test1()

//        testStatic();

//        testAudioConvert();

//        testDelete();

//        testFunction();

//        testOperator();

//        testCloure();

//        testSets11();

//        testCoroutine();

//        testCoroutines();

        testCoroutineOrginalLoad();
    }

    var job_background:Job? = null;
    var job_ui:Job? = null;
    fun testCoroutineOrginalLoad(){
        job_background = launch(Background) {
            Log.d("feifei_1","-- start loadInfo -- ")
            val info = loadInfo();
            job_ui = launch(UI) {
                tv_info.text = info;
                Toast.makeText(this@MainActivity,""+info,Toast.LENGTH_LONG);
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("feifei_1","-- onDestroy -- ")
        job_background?.also { if(!it.isCancelled){
            it.cancel()
            Log.d("feifei_1","-- cancel job_background -- ")
            }
        }

        job_ui?.also {
            if(!it.isCancelled){
                it.cancel();
                Log.d("feifei_1","-- cancel job_ui -- ")
            }
        }

    }
    override fun onClick(v: View?) {

        val intent = Intent(MainActivity@this,SecondActivity::class.java);
        startActivity(intent)
        finish()

    }

    fun testIn(){

        //遍历集合
        for(x in 1..5) {
            println("feifei x:" + x)
        }

        //判断 变量是否在 集合中
        val y = 5;
        if(y !in 1..4){
            println("feifei ${y} is not in 1..4")
        }else{
            println("feifei ${y} is in 1..4")
        }

        // is 关键字 - 判断一个对象是否为一个类的实例，可以使用is关键字

        val student:Student = Student("feifei",18);
        if(student is Person){
            Log.d("feifei","student is Person")
        }else{
            Log.d("feifei","student is not Person")
        }

        println("feifei getStringlength : ${getStringlength(123)}")


        var data:String? = null;

        //data 不为空时执行
        data?.let {
            println("feifei data was not null")
        }

        //data 为空时执行
        data?:let {
            println("feifei data was null")
        }




        //函数体中只有一条语句,则可以直接将改语句赋给该函数
        fun testFun(str:String = "feifei"):String = str;

//        fun testFun(str:String):String {
//            return str;
//        }

        //多行参数写法
        fun say(firstName:String = "fei",
                lastName:String = "bai"){

        }

        //使用varargs 声明变长函数写法
        fun hasEmpty(vararg strArray:String?):Boolean{
            for( str in strArray){
                if("".equals(str)|| str == null){
                    return true;
                }
            }
            return false;
        }

    }

    fun getStringlength(obj:Any):Int?{
        if(obj is String){
            return obj.length;
        }

        if(obj !is String){
            return -1;
        }
        return null;
    }


    fun testTribleOperation(){
        var b:ArrayList<Int>? = ArrayList(4)

        val result:Int = b?.size?:-1;
        Log.d("feifei","result:"+result);

    }

    inline fun testInline(times:Int,block:()->Unit){
        for(time in 1..times-1){
            block()
        }

    }

    fun testSets(){
        var datas = listOf(1,2,3,4,5)
        Log.d("feifei","dataSets:"+datas.toString())
        datas.map {
            it*2 }
        Log.d("feifei","dataSets*2:"+datas.toString())


    }


    fun testInline(){
        var l = ReentrantLock()
        lock(l){
            Log.d("feifei","测试内联函数")
        }
    }


    inline fun <T> lock(lock: Lock, body:()->T):T{
        lock.lock();
        try{
            return body()
        }finally {
            lock.unlock();
        }
    }



       class Student constructor(name:String,age:Int):Person(){
           var name:String?= null;
           var age:Int?=null;
           var sex:Int? = null;
           init {
               this.name = name;
               this.age = age;
           }

           constructor (name: String,age: Int,sex:Int):this(name,age){
               this.sex = sex;
           }
       }

      open class Person() {

      }




}

fun testOutFun(){
    Log.d("feifei","this is OutFun")
}


// -------------------------
fun test1(){
    val firstName = "雪飞"
    val lastName = null
    println("my name is ${getName(firstName,lastName)}")
}
fun hasEmpty(vararg strArray:String?):Boolean{
    for(str in strArray){
        str?: return true;
    }
    return false;
}

fun getName(firstName:String?,lastName:String?="unkonw"):String? {
    if(hasEmpty(firstName,lastName)){
     lastName?.let {
         return@getName "${checkName(firstName)} ${lastName}"
     }
        firstName?.let {
            return@getName "$firstName ${checkName(lastName)}"
        }
    }
    return "$firstName $lastName"
}


fun checkName(name:String?):String = name?:"unkonw"

// ----------

fun testStatic(){
    var isEmpty =  StringUtils.isEmpty("hello")
    var isEmpty2 = StringUtils.isEmpty2("world")
    println("feifei isEmpty：${isEmpty},isEmpty2:${isEmpty2}")
}

object StringUtils{
    @JvmStatic fun isEmpty(str:String):Boolean{
        return ""==str;
    }

    fun isEmpty2(str:String):Boolean{
        return "" == str;
    }
}


fun testAudioConvert() {
    var animal: InteligentConvert.Animal? = InteligentConvert.Dog()
    if (animal is InteligentConvert.Dog) {
        //在这里 animal 被当做 Dog 的对象来处理
        animal.bark()
    }
}

fun testHolder(){
    val age = Holder.age
    val name = Holder.getName();

}

//object 关键字修饰的类 是单例类。
//编译成java 后的代码:
//public final class Holder {
//    private static final int age = 18;
//    public static final Holder INSTANCE;
//
//    @NotNull
//    public final String getName() {
//        return "feifei";
//    }
//
//    public final int getAge() {
//        return age;
//    }
//
//    static {
//        Holder var0 = new Holder();
//        INSTANCE = var0;
//        age = 18;
//    }
//}


// kotlin中调用
//val age = Holder.age
//val name = Holder.getName();

//java 中调用
// Holder.INSTANCE.getAge();
//Holder.INSTANCE.getName();


object Holder{
    fun getName():String{
        return "feifei"
    }
    val age:Int = 18;
}


fun testDelete(){

    var b = BaseImpl(10);
    Derived(b).print()

    val e = Example();
    println(e.p)

    e.p = "Runoob"
    println(e.p)

    println("------")
    println(e.lazyValue)
    println("------")
    println(e.lazyValue)

}

fun testFunction(){
    val str = "hello";
    fun say(count:Int = 10){
        println(str)
        if(count > 0){
            say(count-1)
        }
    }

    say()
}

fun testOperator(){
    for( i in 1..100 step 20){
        println("feifei $i")
    }
}

fun testCloure(){
    printMsg.invoke("hello world")
    printMsg("hello !!!")

    log("welcome", printMsg)
    testClosureParam("china", printMsg)
}
val printMsg = { msg:String ->
    println("feifei-"+msg)
     msg;
}

val log = { str:String ,printLog:(String)->String ->{
    printMsg.invoke(str)
    }
}

fun testClosureParam(str:String,printLog:(String)->String){
    printLog(str)
}

fun testSets11(){

    //(1) kotlin为数组增加了一个Array类，为元素是基本类型的数组增加了xxArray类（其中xx也就是Byte，Short， Int等基本类型）
    //arrayOff 创建包含指定元素的数组(相当于java数组的静态初始化)
    var arr1 = arrayOf("java","kotlin");
    var arrInt = arrayOf(1,2,3)


    //创建指定长度的数组，元素初始化为null
    var arr2 = arrayOfNulls<String>(6);
    var arr2Double = arrayOfNulls<Double>(6)
    arr2.set(0,"java")
    arr2[1] = "Kotlin"

    arr2Double.set(0,10.0)
    arr2Double.set(1,2.0)


    //创建整型数组
    val arr_Int = intArrayOf(1,2);
    var arr_double = doubleArrayOf(1.0,2.0)


    arr_Int.forEach {
        Log.d("feifei","arr_Int:"+it)
    }

    //Array(size: Int,  init: (Int) -> T)
    fun arrInit():(Int)->Int = {it*2}
    var arrayInit = Array<Int>(5,arrInit())
    Log.d("feifei","arrayInit:"+arrayInit.toString())

    // 数组提供的工具方法
    //
    var arrInt12 = arrayOf(1,10,15)
    var all10 = arrInt12.all { it>10 } //all 方法 判断数组中所有元素是否都满足某个条件
    Log.d("feifei","all > 10:"+all10)

    var any10 = arrInt12.any { it > 10 }// any 方法 判断数组中 至少有一个元素满足某个条件
    Log.d("feifei","any > 10:"+any10)

    var arrMap = arrInt12.associate { it+2 to it+10 }

    arrInt12.fill(0,1,3)





    // --------------- Array 是数组、List是链表(有toString方法)

    //listOf 创建 只读集合
    val list1 = listOf<String>("feifei"
    ,"nancy","wangfeng"
    ,"zhengzi"
    ,"wanglili"
    ,"caojian"
    ,"dezi","feifei"
    )

    val count = list1.size;
    val isNull = list1.isEmpty();
    val isContains = list1.contains("feifei")

    //mutableListOf 创建可变的list
    val list2 = mutableListOf<String>("feifei","nancy")
    val isContainsAll = list1.containsAll(list2)
    val indesStr = list1.get(2);
    val index = list1.indexOf("wangfeng");
    val lastIndex = list1.lastIndexOf("feifei")

    val iterator = list1.iterator();//返回该只读集合的元素迭代器 Iterator
    val listIterator = list1.listIterator();//返回一个集合的迭代器 ListIterator
    val listIteratorIndex = list1.listIterator(2)//从指定位置开始返回一个集合的迭代器ListIterator



    Log.d("feifei","list1.toString:"+list1.toString()
        +"\n"
    +"list1.size:"+count+"\n"
    +"list1.isNull:"+isNull+"\n"
    +"list1.isContains(feifei):"+isContains+"\n"
    +"list1.isContainsAll(lis2):"+isContainsAll+"\n"
    +"indesStr:"+indesStr+"\n"
    +"indexOf(wangfeng):"+index+"\n"
    +"lastIndexOf(feifei):"+lastIndex
    )



    // mutableListOf 创建一个可变集合
    val mutableList = mutableListOf<String>(
            "feifei"
            ,"nancy","wangfeng"
            ,"zhengzi"
            ,"wanglili"
            ,"caojian"
            ,"dezi","feifei"
    )

    val isAddOk = mutableList.add("zhangjing") //添加一个元素，返回true 或者false
    Log.d("feifei","isAddOk："+isAddOk+"，toString:"+mutableList.toString())
    val isAddIndexOk = mutableList.add(2,"toAdd")
    Log.d("feifei","isAddIndexOk："+isAddIndexOk+"，toString:"+mutableList.toString())

    val isRemoveOk = mutableList.remove("toAdd");
    Log.d("feifei","isRemoveOk："+isRemoveOk+"，toString:"+mutableList.toString())


    val isRemoteAtOk = mutableList.removeAt(2);//移除指定位置元素
    Log.d("feifei","isRemoteAtOk:"+isRemoteAtOk+",toString:"+mutableList.toString())

    val isAddAllOk = mutableList.addAll(list2);//添加另一个集合，返回true 或者false
    Log.d("feifei","isAddAllOk:"+isAddAllOk+",toString:"+mutableList.toString())

    val isRemoveAll = mutableList.removeAll(list2);
    Log.d("feifei","isRemoveAll:"+isRemoveAll+",,toString:"+mutableList.toString())

    val isSetOK = mutableList.set(2,"德克萨斯")
    Log.d("feifei","isSetOK:"+isSetOK+",toString:"+mutableList)

    val isClearOk = mutableList.clear();
    Log.d("feifei","isClearOk:"+isClearOk+",toString:"+mutableList)


    val list4 = mutableList.toList();//toList 是一个扩展函数,返回一个只读list
    Log.d("feifei","list4:"+list4)




    // --- Map 是一个存放键和值的结合,每个元素都包含键和值.kotlin 中map 也分为只读和map两种
    val map = mapOf<Int,String>(
            1 to "feifei",2 to "nancy", 3 to "wanfenge",4 to "panxing"
    )

    val hashMap = hashMapOf<Int,String>(
            1 to "feifei",2 to "nancy", 3 to "wanfenge",4 to "panxing"
    )

    val countmap = map.size;
    val keys = map.keys; //返回 map对应的keys
    keys.forEach(::print)

    val values = map.values;// 返回 map 对应的values
    values.forEach(::print)

    val entrys = map.entries //返回 map 的entrys
    entrys.forEach{
        println("feifei key : ${it.key},value:${it.value}")
    }

    val isMapEmpty = map.isEmpty();
    val isContainsKey = map.containsKey(3); // 是否包含特定的key
    val isContainValue = map.containsValue("wangfeng")//是否包含特定的value
    val valueStr = map.get(2); //获取指定key的元素

    Log.d("feifei","isMapEmpty:"+isMapEmpty+",toString:"+map.toString()+"\n"
    +"isContainsKey:"+isContainsKey+"\n"
    +"isContainValue:"+isContainValue+"\n"
    +"valueStr get(2):"+valueStr
    )


    // mutatableMap 可变map
    val mutableMap = mutableMapOf<Int,String>()
    mutableMap.putAll(map);
    Log.d("feifei","mutableMap.putAll:"+mutableMap.toString())
    mutableMap.put(5,"拉克丝")
    Log.d("feifei","mutableMap.put(5,\"拉克丝\"):"+mutableMap.toString())
    mutableMap.remove(2);
    Log.d("feifei","mutableMap.remove(2):"+mutableMap.toString())
    mutableMap.clear();

    Log.d("feifei","mutableMap.clear():"+mutableMap.toString())



    //set 集合
    val set = setOf<String>("feifei","nancy")

    val setSize = set.size;
    val setIsEmpty = set.isEmpty()
    val setContain = set.contains("feifei")

    val mutableSet = mutableSetOf<String>("feifei","123")
    mutableList.addAll(set)
    mutableList.removeAt(1)
    mutableList.add(1,"wangfeng")


}


//测试协同
fun testCoroutine(){
    val job1 = launch(UI, CoroutineStart.LAZY) {
        var count = 0
        while (true){
            count ++;
            delay(500)
            println("feifei count:$count ${Thread.currentThread()}")
        }
    }

    val job2 = async(CommonPool) {
        job1.start();
        "feifei job2"
    }

    launch(UI) {
        delay(3000)
        job1.cancel()
        println(job2.await())
    }


}

fun testCoroutines(){

//    runBlocking {
//        val job = launch {
//            repeat(1000) {
//                println("feifei job sleeping $it ... CurrentThread: ${Thread.currentThread()}")
//                delay(500L)
//            }
//        }
//
//        val job1 = launch {
//            var nextTime = 0L;
//            var i = 0;
//            while (i < 20) {
//                var currentTIme = System.currentTimeMillis();
//                if (currentTIme >= nextTime) {
//                    println("feifei job1 sleeping ${i++} ... CurrentThread:${Thread.currentThread()}")
//                    nextTime = currentTIme + 500L;
//                }
//            }
//
//        }
//
//
//
//
//        delay(1900L)
//
//        println("feifei Job is alive: ${job.isActive}; Job iscompleted: ${job.isCompleted}")
//        println("feifei Job1 is alive: ${job1.isActive}; Job1 iscompleted: ${job1.isCompleted}")
//
//
//        val b1 = job.cancel()
//        val c1 = job1.cancel()
//
//        println("feifei job cancel:$b1 and job1 cancle: ${c1}")
//
//        delay(3000L)
//
//        val b2 = job.cancel()
//        val c2 = job1.cancel()
//
//        println("feifei job cancel:$b2 and job1 cancle: ${c2}")
//
//        println("feifei Job is alive: ${job.isActive}; Job iscompleted: ${job.isCompleted}")
//        println("feifei Job1 is alive: ${job1.isActive}; Job1 iscompleted: ${job1.isCompleted}")
//
//
//
//    }


//    runBlocking {
//        var c1 = launch (CommonPool){
//            delay(1000L)
//            println("feifei Coroutine 1")
//        }
//
//        var c2 = launch {
//            delay(1000L)
//            println("feifei Coroutine 2")
//        }
//
////        c1.join()
////        c2.join()
//        println("feifei the main")
//
//    }

//    runBlocking {
//        runBlocking {
//            val one = async(CommonPool){
//                job1();
//            }
//
//            val two = async(CommonPool) {
//                job2();
//            }
//
//            val value1 = one.await();
//            val value2 = two.await();
//
//            println("feifei 结果是 - one :${value1},two:${value2}")
//        }
//
//    }


    runBlocking {
        val jobs = arrayListOf<Job>();
        jobs+= async(Unconfined){
            println("feifei Unconfiged is worked in ${Thread.currentThread()}")
        }

        jobs+= async(coroutineContext) {//coroutineContext在主线程执行
            println("feifei coroutineContext is worked in ${Thread.currentThread()}")
        }

        jobs+= async(CommonPool){
            println("feifei CommonPoll is worked in ${Thread.currentThread()}")
        }

        jobs+= async(newSingleThreadContext("newThread")){
            println("feifei newThread is worked in ${Thread.currentThread()}")
        }

        jobs.forEach{
            it.join()
        }
    }



}
suspend fun job1():Int{
    return 1;
}

suspend fun job2():Int{
    return 2;
}




suspend fun loadInfo():String{
    delay(10,TimeUnit.SECONDS);
    return "happy moon's day"
}
// var job = launch(){} 创建一个协程 ()
// 挂起函数 : join 、delay
// join 函数 可以使主线程 等待协程执行完


// 两种组合Job-launch-Join 和 Deferred-async-await
//Deferred 继承自Job,但是它有一个返回值。调用deferred.await()挂起当前协程,等待另一个协程完成，并取得返回结果








