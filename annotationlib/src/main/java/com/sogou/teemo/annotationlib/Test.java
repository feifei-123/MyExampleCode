package com.sogou.teemo.annotationlib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) //注解作用在类上
@Retention(RetentionPolicy.CLASS)
public @interface Test {
    String path();
}
