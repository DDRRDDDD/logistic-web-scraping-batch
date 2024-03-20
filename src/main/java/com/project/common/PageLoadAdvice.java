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

    private static final String COMPLETE_READY_STATE = "complete";


    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        WebDriverUtils.waitUntil(webDriver -> isPageLoaded());
        log.info("Page load complete. Proceeding with method : {}", invocation.getMethod().getName());

        return invocation.proceed();
    }

    // WebControlAgent로 옮겨질 예정
    private boolean isPageLoaded() {
        return WebDriverUtils.getJavascriptExecutor()
                .executeScript("return document.readyState")
                .toString()
                .equals(COMPLETE_READY_STATE);
    }

}
