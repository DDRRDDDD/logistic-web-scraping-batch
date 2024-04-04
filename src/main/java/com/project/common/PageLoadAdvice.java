package com.project.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Slf4j
@Component
@RequiredArgsConstructor
public class PageLoadAdvice implements MethodInterceptor {

    private static final String COMPLETE_READY_STATE = "complete";


    private final RemoteWebDriver webDriver;

    private final WebDriverWait webDriverWait;


    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        webDriverWait.until(unusedWebDriver -> isPageLoaded());
        log.info("Page load complete. Continuing with the process");
        log.info("Method name : {}", invocation.getMethod().getName());
        return invocation.proceed();
    }


    public boolean isPageLoaded() {
        return webDriver.executeScript("return document.readyState")
                .toString().equals(COMPLETE_READY_STATE);
    }


}
