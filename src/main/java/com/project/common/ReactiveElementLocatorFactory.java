package com.project.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

import static com.project.webdriver.SeleniumConfig.DRIVER_POLLING_INTERVAL_DURATION;
import static com.project.webdriver.SeleniumConfig.WAIT_TIMEOUT_SECONDS;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReactiveElementLocatorFactory implements ElementLocatorFactory {

    private final RemoteWebDriver searchContext;

    @Override
    public ElementLocator createLocator(Field field) {
        return new AjaxElementLocator(searchContext, field, WAIT_TIMEOUT_SECONDS) {

            /** Element locator 의 폴링 주기 설정 */
            @Override
            protected long sleepFor() {
                log.info("Waiting for web element..");
                return DRIVER_POLLING_INTERVAL_DURATION.toMillis();
            }
        };
    }
}
