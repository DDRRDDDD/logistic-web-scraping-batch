package com.project.config;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.time.Duration;


@Configuration
public class WebDriverConfig {

    private final Duration WAIT_DURATION_SECONDS = Duration.ofSeconds(20);

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
        webDriver.manage().timeouts().pageLoadTimeout(WAIT_DURATION_SECONDS);
        webDriver.manage().timeouts().scriptTimeout(WAIT_DURATION_SECONDS);
        return webDriver;
    }

    @Bean
    @Scope
    public WebDriverWait webDriverWait(WebDriver webDriver){
        return new WebDriverWait(webDriver, WAIT_DURATION_SECONDS);
    }

}
