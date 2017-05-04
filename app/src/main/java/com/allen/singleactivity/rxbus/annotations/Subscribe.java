package com.allen.singleactivity.rxbus.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Allen on 2016/12/2.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Subscribe {
    String[] tag();
    Type type() default Type.NORMAL;
    Thread subscribeOn() default Thread.IO;
    Thread observerOn() default Thread.MAIN;

    enum Type{
        NORMAL, STICKY
    }

    enum Thread{
        MAIN, IO, NEW
    }
}
