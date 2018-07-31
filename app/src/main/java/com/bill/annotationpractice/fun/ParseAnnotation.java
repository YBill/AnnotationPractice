package com.bill.annotationpractice.fun;

import com.bill.annotationpractice.MLog;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Bill on 2018/7/31.
 */

public class ParseAnnotation {

    /**
     * 属性
     */
    public static void parseFieldAnnotation() {
        Field[] fields = UserAnnotation.class.getDeclaredFields();
        for (Field field : fields) {
            boolean hasAnnotation = field.isAnnotationPresent(MethodInfo.class);
            if (hasAnnotation) {
                MethodInfo annotation = field.getAnnotation(MethodInfo.class);
                MLog.log("field = " + field.getName() + " | id = "
                        + annotation.id() + " | name = " + annotation.name());
            }
        }
    }

    /**
     * 方法
     */
    public static void parseMethodAnnotation() {
        Method[] methods = UserAnnotation.class.getDeclaredMethods();
        for (Method method : methods) {
            boolean hasAnnotation = method.isAnnotationPresent(MethodInfo.class);
            if (hasAnnotation) {
                MethodInfo annotation = method.getAnnotation(MethodInfo.class);
                MLog.log("method = " + method.getName() + " | id = "
                        + annotation.id() + " | name = " + annotation.name());
            }
        }
    }

    /**
     * 构造方法
     */
    public static void parseConstructAnnotation() {
        Constructor[] constructors = UserAnnotation.class.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            boolean hasAnnotation = constructor.isAnnotationPresent(MethodInfo.class);
            if (hasAnnotation) {
                MethodInfo annotation = (MethodInfo) constructor.getAnnotation(MethodInfo.class);
                MLog.log("constructor = " + constructor.getName() + " | id = "
                        + annotation.id() + " | name = " + annotation.name());
            }
        }
    }

    /**
     * 类
     */
    public static void parseTypeAnnotation() {
        /*Class clazz = null;
        try {
            clazz = Class.forName("com.bill.annotationpractice.fun.UserAnnotation");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        Class clazz = UserAnnotation.class;

        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation a : annotations) {
            MethodInfo annotation = (MethodInfo) a;
            MLog.log("class = " + clazz.getName() + " | id = "
                    + annotation.id() + " | name = " + annotation.name());
        }
    }

}
