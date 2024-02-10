package com.project.page.object;

import com.project.common.BeanManager;
import com.project.metadata.UserInfo;
import com.project.page.Page;
import com.project.page.base.BasePage;
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


    @FindBy(css="a[href=\"javascript:goAllocList();\"]")
    private WebElement allocationPageButton;


    public MainPage login(UserInfo userInfo){
        loginIdTextInput.sendKeys(userInfo.getUserId());
        loginPwTextInput.sendKeys(userInfo.getUserPassword());
        loginButton.click();
        return this;
    }

    public AllocationPage goToAllocationPage(){
        allocationPageButton.click();
        return BeanManager.getPage(AllocationPage.class);
    }

}
