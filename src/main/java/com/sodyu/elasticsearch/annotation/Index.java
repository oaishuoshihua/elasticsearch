package com.sodyu.elasticsearch.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by yuhp on 2017/9/14.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Index {

    String name() default "";//索引名称
    String typeName() default "";//索引类型
}
