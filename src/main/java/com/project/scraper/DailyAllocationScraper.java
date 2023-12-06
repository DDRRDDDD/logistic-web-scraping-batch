package com.project.scraper;

import com.project.metadata.DateRange;
import com.project.metadata.Menu;
import com.project.metadata.UserInfo;
import com.project.page.object.AllocationPage;
import com.project.page.object.MainPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DailyAllocationScraper implements WebScraper {

    private final MainPage mainPage;

    private final UserInfo userInfo;

    private final Menu<AllocationPage> allocationPageMenu;


    @Override
    public List<Map<String, String>> fetchData() {
        int index = 0;
        List<Map<String, String>> resultData = new ArrayList<>();

        AllocationPage allocationPage =
                mainPage.login(userInfo)
                .toHeader()
                .goToPageByMyPageMenu(allocationPageMenu);

        allocationPage.setDateRange(DateRange.ofToday()).clickSearchButton();

        while(index < allocationPage.getDataTableCount()){
            Map<String, String> data = allocationPage
                    .openAllocationDataPopupByOrderCodeIndex(index)
                    .extractAllocationData();
            resultData.add(data);
            index += 1;
        }
        return resultData;
    }
}
