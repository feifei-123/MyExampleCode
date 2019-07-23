package com.feifei.second.codeinject

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.gradle.api.Project

public class CodeInjects {
    private final static ClassPool pool =  ClassPool.getDefault();

    public static void inject(String path, Project project){

        //当前路径加入类池，不然找不到这个类
        pool.appendClassPath(path)

        //project.android.bootClasspath 加入android.jar，不然找不到android相关的所有类
        pool.appendClassPath(project.android.bootClasspath[0].toString())

        pool.importPackage("android.os.Bundle");
        pool.importPackage(" android.app.Activity")

        File dir = new File(path)
        if(dir.isDirectory()){
            //遍历目录
            dir.eachFileRecurse {File file->
                String filePath = file.absolutePath
                println("CodeInjects filePath:"+filePath)
                if(file.getName().equals("MainActivity.class")){

                    //获取MainActivity.class
                    CtClass ctClass = pool.getCtClass("com.sogou.teemo.test_use_gradle_plugin.MainActivity");
                    println("CodeInjects ctClass = "+ctClass)

                    if(ctClass.isFrozen()){
                        ctClass.defrost()
                    }

                    //获取到onCreate方法
                    CtMethod ctMethod = ctClass.getDeclaredMethod("onCreate");
                    println("CodeInjects 方法名 = " + ctMethod)

                    String insetBeforeStr = """ android.widget.Toast.makeText(this,"插件中自动生成的代码",android.widget.Toast.LENGTH_SHORT).show();
                                            """

                    ctMethod.insertAfter(insetBeforeStr)

                    ctClass.writeFile(path)

                    ctClass.detach()//释放

                }
            }
        }

    }


}