package com.project.page.object;

import com.project.data.UserInfo;
import com.project.page.object.base.BasePage;
import com.project.page.Page;
import com.project.webdriver.WebElementCommander;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Page(homePage=true, url="${dev.scraper.mainUrl}")
public class MainPage extends BasePage {

    @FindBy(id="login_id")
    private WebElement loginIdTextInput;

    @FindBy(id="login_pass")
    private WebElement loginPwTextInput;

    @FindBy(id="btn_login")
    private WebElement loginButton;

    public MainPage login(UserInfo userInfo){
        loginIdTextInput.sendKeys(userInfo.getUserId());
        loginPwTextInput.sendKeys(userInfo.getUserPassword());
        WebElementCommander.with(loginButton).click();
        return this;
    }

}
