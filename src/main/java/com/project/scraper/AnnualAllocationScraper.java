package com.project.scraper;

import com.project.scraper.metadata.DateRange;
import com.project.scraper.metadata.Menu;
import com.project.scraper.metadata.UserInfo;
import com.project.page.object.AllocationPage;
import com.project.page.object.MainPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class AnnualAllocationScraper implements WebScraper {

    private final MainPage mainPage;

    private final UserInfo userInfo;

    private final Menu<AllocationPage> allocationPageMenu;


    // 주의 : 사이트의 헛점을 이용한 메서드이다 추후 사이트 관리자에 의해 변경될 여지가 있음!
    @Override
    public List<Map<String, String>> fetchData() {
        int index = 0;
        List<Map<String, String>> resultData = new ArrayList<>();

        AllocationPage allocationPage =
                mainPage.login(userInfo)
                        .toHeader()
                        .goToPageByMyPageMenu(allocationPageMenu);

        int thisYear = LocalDate.now().getYear();
        allocationPage.setDateRange(DateRange.ofYear(thisYear))
                .clickSearchButton();

        while(index < allocationPage.getDataTableCount()){
            Map<String, String> data = allocationPage
                    .openAllocationDataPopupByOrderCodeIndex(index)
                    .extractAllocationData();
            resultData.add(data);
            index += 1;
        }
        return resultData;
    }

    /*  데이터를 월별로 조회하고 스크래핑 하는 메서드.
        시간이 오래 걸리는 대신 정직한 방법이다.

    @Override
    public List<Map<String, String>> fetchData() {
        AllocationPage allocationPage =
                mainPage.login(userInfo)
                        .toHeader()
                        .goToPageByMyPageMenu(allocationPageMenu);

        return fetchDataByMonthlyRanges(allocationPage);
    }
     */

    private List<Map<String, String>> fetchDataByMonthlyRanges(AllocationPage allocationPage){
        int thisYear = LocalDate.now().getYear();
        List<Map<String, String>> resultData = new ArrayList<>();

        for(int i = 1; i <= 12; i++){
            allocationPage.setDateRange(DateRange.ofMonth(thisYear, i))
                    .clickSearchButton();
            resultData.addAll(fetchDataForMonth(allocationPage));
        }
        return resultData;
    }

    private List<Map<String, String>> fetchDataForMonth(AllocationPage allocationPage){
        int index = 0;
        List<Map<String, String>> resultData = new ArrayList<>();

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
