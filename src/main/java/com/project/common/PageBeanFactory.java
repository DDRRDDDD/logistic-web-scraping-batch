package com.project.common;

import com.project.page.Page;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access=PRIVATE)
public class PageBeanFactory {

    public static <T> T getPage(Class<T> pageClass){
        Assert.isTrue(pageClass.isAnnotationPresent(Page.class), "This class is not registered as a page.");
        return ApplicationContextProvider.getContext().getBean(pageClass);
    }

    @Component
    private static class ApplicationContextProvider implements ApplicationContextAware {

        private static ApplicationContext context;

        @Override
        public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
            ApplicationContextProvider.context = applicationContext;
        }

        private static ApplicationContext getContext(){
            return context;
        }
    }

}
