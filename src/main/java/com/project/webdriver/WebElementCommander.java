package com.project.webdriver;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

@RequiredArgsConstructor
public class WebElementCommander {

    private static final String ATTRIBUTE_VALUE = "value";

    private final WebElement webElement;

    public static WebElementCommander with(WebElement webElement) {
        return new WebElementCommander(webElement);
    }

    public WebElement waitForValue(WebElement element, String value){
        WebDriverUtils.waitUntil(ExpectedConditions.attributeToBe(element, ATTRIBUTE_VALUE, value));
        return webElement;
    }


    public void selectByOptionText(String optionsText){
        WebDriverUtils.getWebActions().moveToElement(webElement).perform();

        WebElement webElement = WebDriverUtils.findElement(By.linkText(optionsText));
        WebDriverUtils.waitUntil(ExpectedConditions.elementToBeClickable(webElement)).click();
    }

    public void enterDate(String date){
        webElement.click();
        webElement.clear();
        webElement.sendKeys(date);
        webElement.sendKeys(Keys.ESCAPE);
    }



}
