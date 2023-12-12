package com.project.page.base;


import com.project.common.BeanManager;

public class BasePage {

    public Header toHeader(){
        return BeanManager.getBean("header", Header.class);
    }

}
