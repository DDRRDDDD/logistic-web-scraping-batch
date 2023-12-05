package com.project.page.object.base;

import com.project.common.BeanManager;
import com.project.metadata.Menu;
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
        mainLogoAnchor.click();
        return BeanManager.getPage(MainPage.class);
    }


    // 마이페이지 메뉴를 선택하여 해당 페이지로 이동하는 메서드
    public <T> T goToPageByMyPageMenu(Menu<T> menu){
        WebElementCommander.with(myPageLinkAnchor)
                    .selectByOptionText(menu.getOption());
        return BeanManager.getPage(menu.getPageClass());
    }

}

