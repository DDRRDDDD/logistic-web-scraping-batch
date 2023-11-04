package com.project.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AllocationPage {

    private final WebDriverWait webDriverWait;

    private final By countTime = By.id("countTime");

    private final By searchButton = By.name("ok01");

    private final By orderCodes = By.cssSelector(".td02>a");

    private final By startDateInput = By.id("startDate");

    private final By endDateInput = By.id("endDate");

}
