package com.project.page;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AllocationDataPopup {

    private final WebDriverWait driverWait;

    private final By dataTable = By.cssSelector("tr");
}
