package com.project.webdriver;

import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public class WebDriverUtils {

    public static  WebElement findElement(By by){
        return WebDriverProvider.getWebDriver().findElement(by);
    }

    public static Actions getWebActions(){
        return new Actions(WebDriverProvider.getWebDriver());
    }
    public static <V> V waitUntil(Function<? super WebDriver, V> condition) {
        return WebDriverProvider.getDriverWait().until(condition);
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

