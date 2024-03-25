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

/**
 * 페이지 객체에서만 사용해주세요.
 * 스프링 빈 생명주기에 영향을 줄 수 있습니다.
 */

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
