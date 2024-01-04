package com.project.scraper;

import com.project.metadata.DateRange;
import com.project.metadata.Menu;
import com.project.metadata.UserInfo;
import com.project.page.object.AllocationDataPopup;
import com.project.page.object.AllocationPage;
import com.project.page.object.MainPage;
import io.micrometer.core.instrument.noop.NoopCounter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.*;

import java.util.Map;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
public class AllocationItemScraper implements ItemStreamReader<Map<String, String>> {

    private static final int DEFAULT_INDEX = 0;
    private static final String CURRENT_INDEX = "current.index";

    private int index;
    private AllocationPage allocationPage;


    private final MainPage mainPage;

    private final UserInfo userInfo;

    private final Menu<AllocationPage> allocationPageMenu;

    private final DateRange dateRange;



    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.allocationPage = navigateToAllocationPage();
        this.index = executionContext.getInt(CURRENT_INDEX, DEFAULT_INDEX);
        log.info("Loading the allocation page. Current index : {}", index);
    }


    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put(CURRENT_INDEX, index);
        log.info("Updating ExecutionContext. Current index: {}", index);
    }


    @Override
    public Map<String, String> read() throws Exception {
        Map<String, String> dataResult = fetchAllocationData();
        index += 1;
        log.info("Reading allocation data. Current index: {}", index);
        return dataResult;
    }


    private AllocationPage navigateToAllocationPage(){
        return mainPage
                .login(userInfo)
                .toHeader()
                .goToPageByMyPageMenu(allocationPageMenu)
                .setDateRange(dateRange)
                .clickSearchButton();
    }


    private Map<String, String> fetchAllocationData(){
        return allocationPage
                .openAllocationDataPopupByOrderCodeIndex(index)
                .extractAllocationData();
    }
}
