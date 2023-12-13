package com.project.common;

import com.project.webdriver.WebDriverUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.BooleanUtils;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Slf4j
@Component
public class PageLoadAdvice implements MethodInterceptor {

    @Nullable @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        WebDriverUtils.waitUntil(expectedCondition());
        log.info("Page load complete. Proceeding with method : {}", invocation.getMethod().getName());
        return invocation.proceed();
    }

    private ExpectedCondition<Boolean> expectedCondition(){
        return (webDriver) -> {
            try{
                return Boolean.logicalAnd(isJQueryLoaded(), isJSLoaded());
            }catch(Exception exception){
                return Boolean.TRUE;
            }
        };
    }


    private boolean isJQueryLoaded() {
        return WebDriverUtils.getJavascriptExecutor()
                .executeScript("return jQuery.active === 0")
                .toString()
                .equals(BooleanUtils.TRUE);
    }

    private boolean isJSLoaded() {
        return WebDriverUtils.getJavascriptExecutor()
                .executeScript("return document.readyState")
                .toString()
                .equals("complete");
    }

}
