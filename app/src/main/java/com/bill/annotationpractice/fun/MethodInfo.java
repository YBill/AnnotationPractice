package com.bill.annotationpractice.fun;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Bill on 2018/7/31.
 */

@Documented
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD}) // 类、构造方法、属性、方法
@Retention(RetentionPolicy.RUNTIME) // 运行时注解
@Inherited // 可以被继承
public @interface MethodInfo {

    int id();

    String name() default "Bill";
}
