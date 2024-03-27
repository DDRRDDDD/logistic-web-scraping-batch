package com.project.webdriver;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

/**
 * 페이지 객체에서만 사용해주세요.
 * 스프링 빈 생명주기에 영향을 줄 수 있습니다.
 */

@NoArgsConstructor(access = PRIVATE)
public class WebControlAgent {

    public static void loadPage(String pageUrl) {
        String resolvedUrl = WebControlSupporter.getEnvironment().resolveRequiredPlaceholders(pageUrl);
        WebControlSupporter.getWebDriver().get(resolvedUrl);
    }

    public static <V> V waitUntil(Function<? super WebDriver, V> condition) {
        return WebControlSupporter.getWebDriverWait().until(condition);
    }

    public static void moveBy(WebElement element) {
        WebControlSupporter.getWebActions().moveToElement(element).perform();
    }

    public static void switchToWindow() {
        WebDriver webDriver = WebControlSupporter.getWebDriver();
        String currentWindow = getCurrentWindowHandle();

        webDriver.getWindowHandles().stream()
                .filter(window -> !window.equals(currentWindow))
                .findFirst()
                .ifPresent(webDriver.switchTo()::window);
    }

    public static void closeCurrentWindow() {
        WebControlSupporter.getWebDriver().close();
    }

    private static String getCurrentWindowHandle() {
        try{
            return WebControlSupporter.getWebDriver().getWindowHandle();
        }catch(NoSuchWindowException exception){
            return StringUtils.EMPTY;
        }
    }


    @Component
    private static class WebControlSupporter implements ApplicationContextAware {

        private static ApplicationContext context;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            WebControlSupporter.context = applicationContext;
        }

        private static WebDriver getWebDriver() {
            return context.getBean(RemoteWebDriver.class);
        }

        private static JavascriptExecutor getJavascriptExecutor() {
            return context.getBean(RemoteWebDriver.class);
        }

        private static WebDriverWait getWebDriverWait() {
            return context.getBean(WebDriverWait.class);
        }

        private static Actions getWebActions() {
            return new Actions(getWebDriver());
        }

        private static Environment getEnvironment() {
            return context.getEnvironment();
        }
    }
}
