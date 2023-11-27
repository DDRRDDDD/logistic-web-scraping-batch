package com.project.webdriver;

import com.project.common.BeanManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WebElementCommander {

    private static final String ATTRIBUTE_VALUE = "value";
    private final WebDriver webDriver;
    private final WebDriverWait driverWait;
    private final WebElement webElement;

    private WebElementCommander(WebElement webElement){
        this.webElement = webElement;
        this.webDriver = BeanManager.getBean("chromeDriver", WebDriver.class);
        this.driverWait = BeanManager.getBean("webDriverWait", WebDriverWait.class);
    }

    public static WebElementCommander with(WebElement webElement) {
        return new WebElementCommander(webElement);
    }

    public WebElement waitForValue(WebElement element, String value){
        driverWait.until(ExpectedConditions.attributeToBe(element, ATTRIBUTE_VALUE, value));
        return webElement;
    }


    public void selectByOptionText(String optionsText){
        Actions actions = new Actions(webDriver);
        actions.moveToElement(webElement).perform();

        WebElement webElement = webDriver.findElement(By.linkText(optionsText));
        driverWait.until(ExpectedConditions.elementToBeClickable(webElement)).click();
    }

    public void enterDate(String date){
        webElement.click();
        webElement.clear();
        webElement.sendKeys(date);
        webElement.sendKeys(Keys.ESCAPE);
    }



}
