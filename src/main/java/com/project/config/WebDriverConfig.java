package com.project.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.time.Duration;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.*;

@Slf4j
@Configuration
public class WebDriverConfig {


    public static final Duration WAIT_TIMEOUT_SECONDS = Duration.ofSeconds(20);

    @Bean
    @Scope(SCOPE_SINGLETON)
    public ChromeOptions chromeOptions(){
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER)
                .addArguments("--incognito")
                .addArguments("headless")
                .addArguments("--remote-allow-origins=*")
                .addArguments("--blink-settings=imagesEnabled=false");
        return options;
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public WebDriver chromeDriver(ChromeOptions chromeOptions) {
        WebDriverManager.chromedriver().setup();
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().timeouts().pageLoadTimeout(WAIT_TIMEOUT_SECONDS);
        webDriver.manage().timeouts().scriptTimeout(WAIT_TIMEOUT_SECONDS);
        return webDriver;
    }

    @Bean
    @Scope(SCOPE_SINGLETON)
    public WebDriverWait webDriverWait(WebDriver webDriver){
        return new WebDriverWait(webDriver, WAIT_TIMEOUT_SECONDS);
    }



}
