package com.project.page.object;


import com.project.data.DateRange;
import com.project.page.object.base.BasePage;
import com.project.page.Page;
import com.project.webdriver.WebElementCommander;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


@Page
public class AllocationPage extends BasePage {

    @FindBy(id="startDate")
    private WebElement startDateInput;
    @FindBy(id="endDate")
    private WebElement endDateInput;
    @FindBy(id="countTime")
    private WebElement countTimeText;
    @FindBy(className="ok01")
    private WebElement searchButton;

    @FindBy(xpath="//*[@id=\"myTable\"]/tbody/tr/td[2]/a")
    private List<WebElement> orderCodesByDataTable;


    public AllocationPage setDateRange(DateRange dateRange){
        WebElementCommander.with(startDateInput).enterDate(dateRange.getStartDateOfMonth());
        WebElementCommander.with(endDateInput).enterDate(dateRange.getEndDateOfMonth());
        return this;
    }

    public AllocationPage clickSearchButton() {
        WebElementCommander.with(searchButton).waitForValue(countTimeText, "검색가능").click();
        return this;
    }

}
