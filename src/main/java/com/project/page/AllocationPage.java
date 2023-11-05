package com.project.page;

import com.project.util.BeanContextUtils;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AllocationPage {

    private final WebDriver webDriver;
    private final WebDriverWait webDriverWait;

    private final By countTime = By.id("countTime");

    private final By searchButton = By.name("ok01");

    private final By orderCodes = By.cssSelector(".td02>a");

    private final By startDateInput = By.id("startDate");

    private final By endDateInput = By.id("endDate");


    public AllocationPage clickDataSearchButton(){
        webDriverWait.until(ExpectedConditions.textToBePresentInElementLocated(countTime,"검색가능"));
        webDriver.findElement(searchButton).click();
        return this;
    }

    public AllocationPage enterStartDateInput(String dayOfMonth){
        WebElement element =
                webDriverWait.until(ExpectedConditions.elementToBeClickable(startDateInput));
        enterDateOfMonthDateInput(element, dayOfMonth);
        return this;
    }

    public AllocationPage enterEndDateInput(String dayOfMonth){
        WebElement element =
                webDriverWait.until(ExpectedConditions.elementToBeClickable(endDateInput));
        enterDateOfMonthDateInput(element, dayOfMonth);
        return this;
    }
    public AllocationDataPopup getAllocationDataPopupByTableIndex(int index){
        /* TODO : 데이터 인덱스를 클릭했을 때 물류 데이터 팝업창이 나타난다*/
        return BeanContextUtils.getBean("allocationDataPopup", AllocationDataPopup.class);
    }

    private void enterDateOfMonthDateInput(WebElement element, String dayOfMonth){
        element.click();
        element.clear();
        element.sendKeys(dayOfMonth);
        element.sendKeys(Keys.ESCAPE);
    }


}
