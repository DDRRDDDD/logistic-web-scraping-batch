package com.project.common;

import com.project.page.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class PagePostProcessor implements BeanPostProcessor {

    private final MethodInterceptor pageLoadAdvice;

    private final ElementLocatorFactory reactiveElementLocatorFactory;

    private final Environment environment;

    private final WebDriver webDriver;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Page.class)) {
            Page pageAnnotation = bean.getClass().getAnnotation(Page.class);
            navigateToHomePageIfRequired(pageAnnotation);
        }
        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Page.class)) {
            PageFactory.initElements(reactiveElementLocatorFactory, bean);
            return generatePageObjectProxy(bean);
        }
        return bean;
    }


    private void navigateToHomePageIfRequired(Page pageAnnotation) {
        String connectUrl = pageAnnotation.url();
        if (pageAnnotation.homePage() && StringUtils.isNotEmpty(connectUrl)) {
            webDriver.get(environment.resolvePlaceholders(connectUrl));
        }
    }


    private Object generatePageObjectProxy(Object bean) {
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvice(pageLoadAdvice);
        return proxyFactory.getProxy();
    }


}
