package com.project.config;


import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebDriverOptionsConfiguration {

    @Bean
    public ChromeOptions chromeOptions(){
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER)
                .addArguments("--incognito")
                .addArguments("headless")
                .addArguments("--remote-allow-origins=*")
                .addArguments("--blink-settings=imagesEnabled=false");
        return options;
    }


}
