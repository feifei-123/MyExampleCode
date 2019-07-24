package com.example.myjavassist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class MainActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String name = "";
    }


    public static void main(String[] args)  {

//        testCreateClass();

//        testSetSuperClass();

        testInsertMethod();
    }
    public static void testCreateClass(){

        System.out.println("testCreateClass");
        //创建ClassPool
        ClassPool classPool = ClassPool.getDefault();

        //添加类路径
//        classPool.insertClassPath(new ClassClassPath(this.getClass()));
        classPool.insertClassPath(new ClassClassPath(String.class));
        //创建类
        CtClass stuClass = classPool.makeClass("com.feifei.Student");

        //加载类
        //classPool.get(className)
        try {
            //添加属性
            CtField idField = new CtField(CtClass.longType,"id",stuClass);
            stuClass.addField(idField);

            CtField nameField = new CtField(classPool.get("java.lang.String"),"name",stuClass);
            stuClass.addField(nameField);

            CtField ageField = new CtField(CtClass.intType,"age",stuClass);
            stuClass.addField(ageField);


            //添加方法
            CtMethod getMethod = CtMethod.make("public int getAge(){return this.age;}",stuClass);
            CtMethod setMethod = CtMethod.make("public void setAge(int age) { this.age = age;}",stuClass);

            stuClass.addMethod(getMethod);
            stuClass.addMethod(setMethod);

            //toClass 将CtClass 转换为java.lang.class
            Class<?>clazz = stuClass.toClass();
            System.out.println("testCreateClass clazz:"+clazz);

            System.out.println("testCreateClas ------ 属性列表 -----");
            Field[] fields = clazz.getDeclaredFields();
            for(Field field:fields){
                System.out.println("testCreateClass"+field.getType()+"\t"+field.getName());
            }

            System.out.println("testCreateClass ------ 方法列表 -----");

            Method[] methods = clazz.getDeclaredMethods();
            for(Method method:methods){
                System.out.println("feifei  "+method.getReturnType()+"\t"+method.getName()+"\t"+ Arrays.toString(method.getParameterTypes()));
            }

            stuClass.writeFile("/Users/feifei/Desktop/1");
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            //将stuClass 从ClassPool 移除
            if(stuClass != null){
                stuClass.detach();
            }
        }

    }

    public static void testSetSuperClass(){

        System.out.println("testSetSuperClass");
        //创建ClassPool
        ClassPool classPool = ClassPool.getDefault();


        try {
            //添加类路径
            classPool.insertClassPath(new ClassClassPath(String.class));
            classPool.insertClassPath(new ClassClassPath(Person.class));
            classPool.insertClassPath("/Users/feifei/Desktop/1");

            // 加载类
            //创建类
            CtClass stuClass = classPool.get("com.feifei.Student");
            CtClass personClass = classPool.get("com.example.myjavassist.Person");

            if(stuClass.isFrozen()){
                stuClass.freeze();
            }
            stuClass.setSuperclass(personClass);

            //toClass 将CtClass 转换为java.lang.class
            Class<?>clazz = stuClass.toClass();
            System.out.println("testSetSuperClass ------ 属性列表 -----");
            Field[] fields = clazz.getDeclaredFields();
            for(Field field:fields){
                System.out.println("testCreateClass"+field.getType()+"\t"+field.getName());
            }

            System.out.println("testSetSuperClass ------ 方法列表 -----");

            Method[] methods = clazz.getDeclaredMethods();
            for(Method method:methods){
                System.out.println("testSetSuperClass  "+method.getReturnType()+"\t"+method.getName()+"\t"+ Arrays.toString(method.getParameterTypes()));
            }

            stuClass.writeFile("/Users/feifei/Desktop/1");
            personClass.writeFile("/Users/feifei/Desktop/1");

        } catch (NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static void testInsertMethod(){

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = null;
        try {
            ctClass = pool.get("com.example.myjavassist.Calculator");

            //获取类中现有的方法
            String getSumName = "getSum";
            CtMethod methodOld = ctClass.getDeclaredMethod(getSumName);


            String methodNewName = getSumName+"$impl";
            //修改原有方法的方法名
            methodOld.setName(methodNewName);


            //创建一个新的方法getSumName,并将旧方法 复制成新方法中.
            CtMethod newMethod = CtNewMethod.copy(methodOld,getSumName,ctClass,null);

            //设置新newMethod的方法体
            StringBuffer body = new StringBuffer();
            body.append("{\nlong start = System.currentTimeMillis();\n");
            // 调用原有代码，类似于method();($$)表示所有的参数
            body.append(methodNewName + "($$);\n");
            body.append("System.out.println(\"Call to method " + methodNewName
                    + " took \" +\n (System.currentTimeMillis()-start) + " + "\" ms.\");\n");
            body.append("}");

            newMethod.setBody(body.toString());

            //为类新添加方法
            ctClass.addMethod(newMethod);

            Calculator calculator =(Calculator)ctClass.toClass().newInstance();
            calculator.getSum(10000);

            //将类输出到文件
            ctClass.writeFile("/Users/feifei/Desktop/1");

        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(ctClass!=null){
                ctClass.detach();
            }
        }
    }

    @Override
    public void onClick(View view) {
        testCreateClass();
    }


}
