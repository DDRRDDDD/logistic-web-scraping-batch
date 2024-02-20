package com.project.scraper;

import com.project.metadata.DateRange;
import com.project.metadata.Menu;
import com.project.metadata.UserInfo;
import com.project.page.object.AllocationPage;
import com.project.page.object.MainPage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;


@Component
@Scope(SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class AllocationItemScraperBuilder {

    private final MainPage mainPage;

    private final UserInfo userInfo;

    private DateRange dateRange;

    public AllocationItemScraperBuilder setDateRange(DateRange dateRange){
        this.dateRange = dateRange;
        return this;
    }

    public AllocationItemScraper build(){
        return new AllocationItemScraper(mainPage, userInfo, dateRange);
    }
}
