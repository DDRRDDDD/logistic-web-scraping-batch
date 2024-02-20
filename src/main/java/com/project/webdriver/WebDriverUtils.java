package com.project.webdriver;

import lombok.NoArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public class WebDriverUtils {

    public static <V> V waitUntil(Function<? super WebDriver, V> condition) {
        return WebDriverProvider.getDriverWait().until(condition);
    }
    public static  WebElement findElement(By by){
        return WebDriverProvider.getWebDriver().findElement(by);
    }

    public static Actions getWebActions(){
        return new Actions(WebDriverProvider.getWebDriver());
    }

    public static JavascriptExecutor getJavascriptExecutor(){
        return (JavascriptExecutor) WebDriverProvider.getWebDriver();
    }

    public static void switchToWindow(){
        WebDriver webDriver = WebDriverProvider.getWebDriver();
        String currentWindow = getCurrentWindowHandle();

        webDriver.getWindowHandles().stream()
                .filter(window -> !window.equals(currentWindow))
                .findFirst()
                .ifPresent(webDriver.switchTo()::window);

    }


    public static void closeCurrentWindow(){
        WebDriverProvider.getWebDriver().close();
    }


    private static String getCurrentWindowHandle(){
        try{
            return WebDriverProvider.getWebDriver().getWindowHandle();
        }catch(NoSuchWindowException exception){
            return "";
        }
    }

    public static void closeWebDriver(){
        WebDriverProvider.getWebDriver().close();
        WebDriverProvider.getWebDriver().quit();
    }


    @Component
    private static class WebDriverProvider  {

        private static WebDriver driver;
        private static WebDriverWait driverWait;

        @Autowired
        public void setWebDriver(WebDriver webDriver) throws BeansException {
            driver = webDriver;
        }

        @Autowired
        public void setWebDriverWait(WebDriverWait webDriverWait) throws BeansException {
            driverWait = webDriverWait;
        }

        public static WebDriver getWebDriver()  {
            return driver;
        }

        public static WebDriverWait getDriverWait() {
            return driverWait;
        }

    }

}

