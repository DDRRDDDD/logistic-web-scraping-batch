package com.project.page.object.base;

import com.project.common.BeanManager;
import com.project.data.Menu;
import com.project.page.Page;
import com.project.page.object.MainPage;
import com.project.webdriver.WebElementCommander;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Page
public class Header {

    @FindBy(id="btn_main")
    private WebElement mainLogoAnchor;

    @FindBy(id="btn_header_orderStatus")
    private WebElement myPageLinkAnchor;

    public MainPage goToMainPage(){
        WebElementCommander.with(mainLogoAnchor).click();
        return BeanManager.getPage(MainPage.class);
    }


    public <T> T goToPageByMyPageMenu(Menu<T> menu){
        WebElementCommander.with(myPageLinkAnchor)
                    .selectByOptionText(menu.getOption());
        return BeanManager.getPage(menu.getPageClass());
    }

}

