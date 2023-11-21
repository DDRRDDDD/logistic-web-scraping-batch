package com.project.page;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.*;

@Retention(RUNTIME)
@Target({FIELD, TYPE})
@Lazy @Component
public @interface Page {

    boolean homePage() default false;
    String url() default "";

}

