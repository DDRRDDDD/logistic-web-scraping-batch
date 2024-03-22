package com.project.page.object;


import com.project.common.PageBeanFactory;
import com.project.metadata.DateRange;
import com.project.page.Page;
import com.project.webdriver.WebControlAgent;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


@Page
public class AllocationPage extends BasePage {

    private static final String ATTRIBUTE_NAME = "value";
    private static final String SEARCH_POSSIBLE_VALUE = "검색가능";

    @FindBy(id = "startDate")
    private WebElement startDateInput;
    @FindBy(id = "endDate")
    private WebElement endDateInput;
    @FindBy(id = "countTime")
    private WebElement countTimeText;
    @FindBy(className = "ok01")
    private WebElement searchButton;

    @FindBy(xpath = "//*[@id=\"myTable\"]/tbody/tr/td[2]/a")
    private List<WebElement> orderCodesByDataTable;


    public AllocationPage setDateRange(DateRange dateRange) {
        startDateInput.click();
        startDateInput.clear();
        startDateInput.sendKeys(dateRange.getStartDateOfMonth());
        startDateInput.sendKeys(Keys.ESCAPE);

        endDateInput.click();
        endDateInput.clear();
        endDateInput.sendKeys(dateRange.getEndDateOfMonth());
        endDateInput.sendKeys(Keys.ESCAPE);
        return this;
    }

    /**
     * 메서드 호출시 orderCodesByDataTable 의 값이 변경될 수 있습니다.
     */

    public AllocationPage clickSearchButton() {
        ExpectedCondition<Boolean> expectedCondition =
                ExpectedConditions.attributeToBe(countTimeText, ATTRIBUTE_NAME, SEARCH_POSSIBLE_VALUE);
        WebControlAgent.waitUntil(expectedCondition);
        searchButton.click();
        return this;
    }

    public int getDataTableCount() {
        return orderCodesByDataTable.size();
    }

    public AllocationDataPopup openAllocationDataPopupByOrderCodeIndex(int orderCodeIndex) {
        if (orderCodeIndex < orderCodesByDataTable.size()){
            orderCodesByDataTable.get(orderCodeIndex).click();
            return PageBeanFactory.getPage(AllocationDataPopup.class);
        }
        return new AllocationDataPopup.VoidDataPopup();
    }

}
