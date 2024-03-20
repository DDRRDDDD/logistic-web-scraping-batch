package com.project.page.base;


import com.project.common.PageBeanFactory;

public class BasePage {

    public Header toHeader(){
        return PageBeanFactory.getPage(Header.class);
    }

}
