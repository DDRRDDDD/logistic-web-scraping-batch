package com.project.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;


import static org.openqa.selenium.support.ui.ExpectedConditions.*;
@Component
@RequiredArgsConstructor
public class MainPage {

    private final WebDriverWait webDriverWait;

    private final By loginIdInput = By.id("login_id");

    private final By loginPwInput = By.id("login_pass");

    private final By loginButton = By.id("btn_login");

    private final By logoutButton = By.id("btn_logout");

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
