package com.project.common;

import com.project.page.Page;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static lombok.AccessLevel.PRIVATE;

/**
 * 페이지 객체에서만 사용해주세요.
 * 스프링 빈 생명주기에 영향을 줄 수 있습니다.
 */
@Slf4j
@NoArgsConstructor(access=PRIVATE)
public class PageBeanFactory {


    /**
     * 주어진 페이지 어노테이션의 URL을 확인하고, 현재 페이지의 URL과 일치하는지를 검사합니다.
     * 페이지 어노테이션의 URL이 설정되지 않은 경우에도 허용됩니다.
     */
    public static <T> T getPage(Class<T> pageClass) {
        Assert.isTrue(pageClass.isAnnotationPresent(Page.class), "This class is not registered as a page.");
        T pageBean = ApplicationContextProvider.getContext().getBean(pageClass);
        validatePageUrl(pageClass.getAnnotation(Page.class));
        return pageBean;
    }

    private static void validatePageUrl(Page pageAnnotation) {
        String pageUrl = ApplicationContextProvider.getEnvironment().resolvePlaceholders(pageAnnotation.url());
        String targetUrl = ApplicationContextProvider.getContext().getBean(WebDriver.class).getCurrentUrl();

        Assert.state(StringUtils.contains(targetUrl, pageUrl),
                "Access denied! The current page and the target page are different. " +
                        "Current URL : " + targetUrl + ", Page URL : " + pageUrl);

        if(StringUtils.isEmpty(pageUrl)){
            log.warn("The page URL[{}] is not defined, the page might not be accurate.", targetUrl);
        }
        log.info("Navigating to page : {}", targetUrl);
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

        private static Environment getEnvironment() {
            return context.getEnvironment();
        }
    }

}
