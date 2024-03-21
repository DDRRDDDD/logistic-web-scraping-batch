package com.project.page.object;

import com.project.page.Page;
import com.project.webdriver.WebControlAgent;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Slf4j
@Page(pageScope = SCOPE_PROTOTYPE)
public class AllocationDataPopup {

    @FindBy(xpath = "//*[@id=\"cargoDetailView01\"]/table/tbody/tr/td[1]")
    private List<WebElement> dataKeys;

    @FindBy(xpath = "//*[@id=\"cargoDetailView01\"]/table/tbody/tr/td[2]")
    private List<WebElement> dataValues;

    private Map<String, String> resultData;

    @PostConstruct
    public void openPopup() {
        WebControlAgent.switchToWindow();
    }

    public Map<String, String> extractAllocationData() {
        setResultData();
        WebControlAgent.closeCurrentWindow();
        WebControlAgent.switchToWindow();
        return resultData;
    }

    /**
     * DTO로 변경 검토 중
     */
    private void setResultData() {
        resultData = IntStream.range(0, dataKeys.size()).boxed()
                .collect(Collectors.toMap(
                        index -> dataKeys.get(index).getText(),
                        index -> dataValues.get(index).getText()
                ));
    }


    static class VoidDataPopup extends AllocationDataPopup {

        @Override
        public Map<String, String> extractAllocationData() {
            return null;
        }
    }
}
