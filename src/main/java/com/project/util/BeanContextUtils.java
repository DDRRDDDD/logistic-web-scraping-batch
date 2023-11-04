package com.project.util;

import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access=PRIVATE)
public class BeanContextUtils {

    public static <T> T getBean(String beanName, Class<T> beanClass){
        return ApplicationContextProvider.getContext().getBean(beanName, beanClass);
    }


    @Component
    static class ApplicationContextProvider implements ApplicationContextAware {

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
