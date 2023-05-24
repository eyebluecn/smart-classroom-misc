package com.smart.classroom.misc.controller.auth;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Feature {

    FeatureType[] value() default {FeatureType.PUBLIC};

}
