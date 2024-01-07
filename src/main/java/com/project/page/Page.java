package com.project.page;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({FIELD, TYPE})
@Lazy @Component @Scope
public @interface Page {

    boolean homePage() default false;

    String url() default "";

    @AliasFor(annotation=Scope.class, attribute="scopeName")
    String pageScope() default ConfigurableBeanFactory.SCOPE_SINGLETON;
}

