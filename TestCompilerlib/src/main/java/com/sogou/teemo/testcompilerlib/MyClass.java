package com.sogou.teemo.testcompilerlib;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

//注册成为Processor
@AutoService(Processor.class)
//指定编译的ava版本
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("com.sogou.teemo.annotationlib.Test")
public class MyClass extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        //创建方法
        MethodSpec main = MethodSpec.methodBuilder("main").addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .addParameter(String[].class,"args")
                .addStatement("$T.out.println($S)",System.class,"Hello word")
                .build();
        //创建类
        TypeSpec typeSpec = TypeSpec.classBuilder("HelloWord")
                .addModifiers(Modifier.PUBLIC,Modifier.FINAL)
                .addMethod(main)
                .build();

        //创建java文件
        JavaFile file = JavaFile.builder("com.example.test",typeSpec).build();

        try {
            file.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
