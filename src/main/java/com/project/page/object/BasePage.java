package com.project.page.object;


import com.project.common.PageBeanFactory;
import com.project.page.object.Header;

public class BasePage {

    public Header toHeader() {
        return PageBeanFactory.getPage(Header.class);
    }


}
