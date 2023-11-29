package com.project.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access=PRIVATE)
public class BeanManager {

    public static <T> T getBean(String beanName, Class<T> beanClass){
        return ApplicationContextProvider.getContext().getBean(beanName, beanClass);
    }

    public static <T> T getPage(Class<T> pageClass){
        return ApplicationContextProvider.getContext().getBean(pageClass);
    }


    @Component
    private static class ApplicationContextProvider implements ApplicationContextAware {

        private static ApplicationContext context;

        @Override
        public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
            context = applicationContext;
        }

        public static ApplicationContext getContext(){
            return context;
        }
    }

}
