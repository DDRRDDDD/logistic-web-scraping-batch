package com.project.page;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;



@Slf4j
@Component
@RequiredArgsConstructor
public class PageObjectInitializer implements BeanPostProcessor {

    private final ElementLocatorFactory elementLocatorFactory;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean.getClass().isAnnotationPresent(Page.class)){
            pageElementsInitialization(bean, beanName);
        }
        return bean;
    }

    private void pageElementsInitialization(Object bean, String beanName){
        PageFactory.initElements(elementLocatorFactory, bean);
        log.info("Initialize Page Object : {}", beanName);
    }
}
