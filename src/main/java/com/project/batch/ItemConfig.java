package com.project.batch;

import com.project.batch.sql.SqlMappedContext;
import com.project.metadata.DateRange;
import com.project.metadata.UserInfo;
import com.project.page.Scenario;
import com.project.page.object.AllocationPage;
import com.project.page.object.MainPage;
import com.project.scraper.WebScraperBuilder;
import com.project.scraper.WebScraperItemReader;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class ItemConfig {

    private final DataSource allocationDataSource;

    private final DateRange dailyDateRange;

    private final DateRange monthlyDateRange;

    private final UserInfo userInfo;


    public ItemConfig(@Qualifier("allocationDataSource") DataSource allocationDataSource,
                      @Qualifier("dailyDateRange") DateRange dailyDateRange,
                      @Qualifier("monthlyDateRange") DateRange monthlyDateRange,
                      UserInfo userInfo)
    {
        this.allocationDataSource = allocationDataSource;
        this.dailyDateRange = dailyDateRange;
        this.monthlyDateRange = monthlyDateRange;
        this.userInfo = userInfo;
    }


    @Bean
    @StepScope
    public WebScraperItemReader<Map<String, String>> dailyAScenarioScraper() {
        return new WebScraperBuilder<Map<String, String>>("daily.A.scenario")
                .scenario(Scenario.startWith(MainPage.class))
                .prepare((reader, scenario) ->
                    scenario.expectedPage(MainPage.class)
                            .login(userInfo)
                            .goToAllocationPage()
                            .setDateRange(dailyDateRange)
                            .clickSearchButton()
                )
                .read((reader, scenario) ->
                    scenario.expectedPage(AllocationPage.class)
                            .openAllocationDataPopupByOrderCodeIndex(reader.getCurrentItemIndex())
                            .extractAllocationData()
                )
                .build();
    }


    @Bean
    @StepScope
    public WebScraperItemReader<Map<String, String>> monthlyAScenarioScraper() {
        return new WebScraperBuilder<Map<String, String>>("monthly.A.scenario")
                .scenario(Scenario.startWith(MainPage.class))
                .prepare((reader, scenario) ->
                    scenario.expectedPage(MainPage.class)
                            .login(userInfo)
                            .goToAllocationPage()
                            .setDateRange(monthlyDateRange)
                            .clickSearchButton()
                )
                .read((reader, scenario) ->
                    scenario.expectedPage(AllocationPage.class)
                            .openAllocationDataPopupByOrderCodeIndex(reader.getCurrentItemIndex())
                            .extractAllocationData()
                )
                .build();
    }


    @Bean
    @StepScope
    public JdbcBatchItemWriter<Map<String, String>> AScenarioJdbcItemWriter() {
        return new JdbcBatchItemWriterBuilder<Map<String, String>>()
                .dataSource(allocationDataSource)
                .sql(SqlMappedContext.INSERT_ALLOCATION)
                .assertUpdates(false)
                .columnMapped()
                .build();
    }


}
