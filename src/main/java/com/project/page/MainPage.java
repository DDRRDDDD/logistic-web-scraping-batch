package com.project.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import static org.openqa.selenium.support.ui.ExpectedConditions.*;
@Component
public class MainPage {

    private final WebDriverWait webDriverWait;

    private final WebDriver webDriver;

    private final String MAIN_PAGE_URL;

    private final By loginIdInput = By.id("login_id");

    private final By loginPwInput = By.id("login_pass");

    private final By loginButton = By.id("btn_login");

    public MainPage(@Autowired WebDriverWait webDriverWait, @Autowired WebDriver webDriver,
                    @Value("${dev.scraper.mainUrl}") String mainUrl){
        this.webDriverWait = webDriverWait;
        this.webDriver = webDriver;
        MAIN_PAGE_URL = mainUrl;
    }

    public MainPage loadMainPage(){
        webDriver.get(MAIN_PAGE_URL);
        return this;
    }

    public MainPage enterLoginUserIdInput(String userId){
        webDriverWait.until(visibilityOfElementLocated(loginIdInput))
                    .sendKeys(userId);
        return this;
    }

    public MainPage enterLoginUserPwInput(String userPassword){
        webDriverWait.until(visibilityOfElementLocated(loginPwInput))
                .sendKeys(userPassword);
        return this;
    }

    public MainPage clickLoginButton(){
        webDriverWait.until(elementToBeClickable(loginButton))
                    .click();
        return this;
    }
}
