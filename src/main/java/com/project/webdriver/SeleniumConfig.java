package com.project.webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.time.Duration;

import static org.springframework.beans.factory.support.AbstractBeanDefinition.SCOPE_DEFAULT;


@Configuration
public class SeleniumConfig {

    public static final int WAIT_TIMEOUT_SECONDS = 10;

    /** 1초 주기 polling 요청 */
    public static final int POLLING_INTERVAL =  1;

    public static final Duration DRIVER_WAIT_DURATION = Duration.ofSeconds(WAIT_TIMEOUT_SECONDS);

    public static final Duration DRIVER_POLLING_INTERVAL_DURATION = Duration.ofSeconds(POLLING_INTERVAL);

    @Bean
    public Capabilities chromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL)
                .addArguments("--incognito")
                .addArguments("--headless")
                .addArguments("--remote-allow-origins=*")
                .addArguments("--blink-settings=imagesEnabled=false");
        return options;
    }

    @Bean
    public Capabilities firefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL)
                .addArguments("-private")
                .addArguments("-headless")
                .addPreference("dom.webnotifications.enabled", false);
        return options;
    }

    /**
     * 추후 드라이버 이름을 환경변수로 받아 실행시킬 계획
     * ex. firefoxdriver, firefoxOptions 부분을 파라미터로 받을 예정
     */
    @Bean(destroyMethod = SCOPE_DEFAULT)
    public WebDriverManager webDriverManager() {
        return WebDriverManager.firefoxdriver()
                .capabilities(firefoxOptions())
                .remoteAddress("http://172.20.3.16:4444/wd/hub");
    }

    @Bean(destroyMethod = SCOPE_DEFAULT)
    public RemoteWebDriver webDriver() {
        WebDriver webDriver = webDriverManager().create();
        Assert.isInstanceOf(RemoteWebDriver.class, webDriver,
                "There might be an issue with the configuration of WebDriverManager.");
        return (RemoteWebDriver) webDriver;
    }

    @Bean
    public WebDriverWait webDriverWait(WebDriver webDriver) {
        WebDriverWait driverWait = new WebDriverWait(webDriver, DRIVER_WAIT_DURATION);
        driverWait.pollingEvery(DRIVER_POLLING_INTERVAL_DURATION);
        driverWait.ignoring(NoSuchElementException.class);
        return driverWait;
    }
}
