package com.example.bill.testlib;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Bill on 2018/8/5.
 */

@Documented()
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface GenerateCode {
}
