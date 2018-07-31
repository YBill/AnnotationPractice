package com.bill.annotationpractice.fun;

/**
 * Created by Bill on 2018/7/31.
 */

@MethodInfo(id = 0, name = "class UserAnnotation")
public class UserAnnotation {

    @MethodInfo(id = 1, name = "private field")
    private int id;

    @MethodInfo(id = 2)
    String name;

    boolean sex;

    @MethodInfo(id = 3, name = "public Construction")
    public UserAnnotation() {

    }

    @MethodInfo(id = 4, name = "private Construction")
    private UserAnnotation(int id) {

    }

    @MethodInfo(id = 5, name = "public method")
    public void first() {

    }

    @MethodInfo(id = 5, name = "private method")
    private void second() {

    }


}
