package com.project.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class WebDriverTest {

    @Autowired
    private WebDriver webDriver;
    @Test
    @DisplayName("드라이버 동작 테스트")
    public void webDriverTest1(){
        webDriver.get("https://www.google.com");
        String googleTitle = webDriver.getTitle();
        webDriver.get("https://www.naver.com");
        String naverTitle = webDriver.getTitle();
        webDriver.quit();

        Assertions.assertEquals("GOOGLE", googleTitle.toUpperCase());
        Assertions.assertEquals("NAVER", naverTitle.toUpperCase());
    }
}
