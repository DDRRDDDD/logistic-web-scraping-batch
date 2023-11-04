package com.project.page;

import com.project.util.BeanContextUtils;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyPage {

    private final WebDriverWait webDriverWait;

    private final By allocationLink = By.id("btn_nav_allocList1");

    public AllocationPage goToAllocationPage(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(allocationLink))
                    .click();
        return BeanContextUtils.getBean("allocationPage", AllocationPage.class);
    }
}
