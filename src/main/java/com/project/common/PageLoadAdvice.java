package com.project.common;

import com.project.webdriver.WebControlAgent;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Slf4j
@Component
public class PageLoadAdvice implements MethodInterceptor {

    private static final String COMPLETE_READY_STATE = "complete";

    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        WebControlAgent.waitUntilPageLoad(COMPLETE_READY_STATE);
        log.info("Page load complete. Proceeding with method : {}", invocation.getMethod().getName());
        return invocation.proceed();
    }
}
