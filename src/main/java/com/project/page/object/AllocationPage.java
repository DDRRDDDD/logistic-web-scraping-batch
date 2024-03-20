package com.project.page.object;


import com.project.common.PageBeanFactory;
import com.project.metadata.DateRange;
import com.project.page.Page;
import com.project.page.base.BasePage;
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


    /**
     * 메서드 호출시 orderCodesByDataTable 의 값이 변경될 수 있습니다.
     */

    public AllocationPage clickSearchButton() {
        WebElementCommander.with(searchButton)
                .waitForValue(countTimeText, "검색가능")
                .click();
        return this;
    }

    public int getDataTableCount(){
        return orderCodesByDataTable.size();
    }


    public AllocationDataPopup openAllocationDataPopupByOrderCodeIndex(int orderCodeIndex){
        if(orderCodesByDataTable.size() <= orderCodeIndex){
            return new AllocationDataPopup.VoidDataPopup();
        }
        orderCodesByDataTable.get(orderCodeIndex).click();
        return PageBeanFactory.getPage(AllocationDataPopup.class);
    }

}
