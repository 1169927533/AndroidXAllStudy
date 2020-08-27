package com.example.a11699.comp_netstudyt.other;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create time 2020/7/21
 * Create Yu
 * description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RenovaceThread {

    ThreadStrategy subscribeThread() default ThreadStrategy.IO;

    ThreadStrategy observeThread();
}
