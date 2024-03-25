package com.project.page.object;

import com.project.common.PageBeanFactory;
import com.project.metadata.Menu;
import com.project.page.Page;
import com.project.webdriver.WebControlAgent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Page
public class Header {

    @FindBy(id = "btn_main")
    private WebElement mainLogoAnchor;

    @FindBy(id = "btn_header_orderStatus")
    private WebElement myPageLinkAnchor;

    public MainPage goToMainPage() {
        mainLogoAnchor.click();
        return PageBeanFactory.getPage(MainPage.class);
    }

    // 마이페이지 메뉴를 선택하여 해당 페이지로 이동하는 메서드
    public <T> T goToPageByMyPageMenu(Menu<T> menu) {
        WebControlAgent.moveBy(myPageLinkAnchor);

        By menuOptionLocator = By.linkText(menu.getOption());
        ExpectedCondition<WebElement> expectedCondition =
                ExpectedConditions.elementToBeClickable(menuOptionLocator);

        WebControlAgent.waitUntil(expectedCondition).click();
        return PageBeanFactory.getPage(menu.getPageClass());
    }

}

