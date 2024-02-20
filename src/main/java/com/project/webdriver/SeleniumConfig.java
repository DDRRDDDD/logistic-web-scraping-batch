package com.project.webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class SeleniumConfig {

    private static final int WAIT_TIMEOUT_SECONDS = 7;
    public static final Duration DRIVER_WAIT_DURATION = Duration.ofSeconds(WAIT_TIMEOUT_SECONDS);


    @Bean
    public ChromeOptions chromeOptions(){
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER)
                .addArguments("--incognito")
                .addArguments("--headless")
                .addArguments("--remote-allow-origins=*")
                .addArguments("--blink-settings=imagesEnabled=false");
        return options;
    }

    @Bean
    public FirefoxOptions firefoxOptions(){
        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER)
                .addArguments("-private")
                .addArguments("-headless")
                .addPreference("dom.webnotifications.enabled", false);
        return options;
    }

//    @Bean(destroyMethod="quit")
//    public WebDriver chromeDriver(){
//       return WebDriverManager.chromedriver()
//                .capabilities(chromeOptions())
//                .browserInDocker()
//                .create();
//    }

    @Bean(destroyMethod="quit")
    public WebDriver firefoxDriver(){
        return WebDriverManager.firefoxdriver()
                .capabilities(firefoxOptions())
                .browserInDocker()
                .create();
    }

    @Bean
    public WebDriverWait webDriverWait(WebDriver webDriver){
        WebDriverWait driverWait = new WebDriverWait(webDriver, DRIVER_WAIT_DURATION);
        driverWait.ignoring(NoSuchElementException.class);
        return driverWait;
    }

    @Bean
    public ElementLocatorFactory ajaxElementLocatorFactory(WebDriver webDriver){
        return new AjaxElementLocatorFactory(webDriver, WAIT_TIMEOUT_SECONDS);
    }

}
