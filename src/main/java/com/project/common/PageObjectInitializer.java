package com.project.common;

import com.project.page.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class PageObjectInitializer implements BeanPostProcessor {

    private final Advice pageLoadAdvice;

    private final ElementLocatorFactory ajaxElementLocatorFactory;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(Page.class)){
            pageElementsInitialization(bean, beanName);
            return getPageObjectProxy(bean);
        }
        return bean;
    }

    private Object getPageObjectProxy(Object bean){
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvice(pageLoadAdvice);
        return proxyFactory.getProxy();
    }


    private void pageElementsInitialization(Object bean, String beanName){
        PageFactory.initElements(ajaxElementLocatorFactory, bean);
        log.info("Initialize Page Object : {}", beanName);
    }
}
