package com.sodyu.elasticsearch.annotation;

import com.sodyu.elasticsearch.enums.IndexFieldType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by yuhp on 2017/10/9.
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.FIELD)
public @interface IndexField {
    IndexFieldType dataType() default IndexFieldType.Default;
    String name() default "";
    boolean store() default true;
}
