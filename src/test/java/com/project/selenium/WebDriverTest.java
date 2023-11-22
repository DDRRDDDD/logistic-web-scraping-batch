package com.project.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.project.webdriver.SeleniumConfig.DRIVER_WAIT_DURATION;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class WebDriverTest {
    private WebDriver webDriver;

    @Value("${dev.scraper.mainUrl}")
    private String mainUrl;


    @BeforeAll
    public static void beforeAll(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeTest(@Autowired ChromeOptions options){
        webDriver = new ChromeDriver(options);
        webDriver.manage().timeouts().pageLoadTimeout(DRIVER_WAIT_DURATION);
        webDriver.manage().timeouts().scriptTimeout(DRIVER_WAIT_DURATION);
    }

    @Test
    @DisplayName("드라이버 동작 테스트")
    public void webDriverTest1(){
        webDriver.get("https://www.google.com");
        String googleTitle = webDriver.getTitle();

        webDriver.get("https://www.naver.com");
        String naverTitle = webDriver.getTitle();

        Assertions.assertEquals("GOOGLE", googleTitle.toUpperCase());
        Assertions.assertEquals("NAVER", naverTitle.toUpperCase());
    }


    @Test
    @DisplayName("드라이버가 메인페이지에 정상적으로 접속이 가능한가?")
    public void webDriverTest2(){
        webDriver.get(mainUrl);
        String title = webDriver.getTitle();

        Assertions.assertEquals("비밀 ><", title.toUpperCase());
    }

    @AfterEach
    public void afterTest() throws InterruptedException {
        Thread.sleep(5000);
        webDriver.close();
        webDriver.quit();
    }

    @AfterAll
    public static void afterAll(){
        WebDriverManager.chromedriver().quit();
    }
}
