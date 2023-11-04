package com.project.page;

import com.project.util.BeanContextUtils;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Header {

    private final WebDriverWait webDriverWait;

    private final By mainLogo = By.id("btn_main");

    private final By myPageLink = By.id("btn_header_orderStatus");

    public MainPage goToMainPage(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(mainLogo))
                    .click();
        return BeanContextUtils.getBean("mainPage", MainPage.class);
    }

    public MyPage goToMyPage(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(myPageLink))
                    .click();
        return BeanContextUtils.getBean("myPage", MyPage.class);
    }

}
