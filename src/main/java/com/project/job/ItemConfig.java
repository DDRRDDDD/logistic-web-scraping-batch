package com.project.job;

import com.project.datasource.sql.SqlMappedContext;
import com.project.metadata.DateRange;
import com.project.scraper.AllocationItemScraperBuilder;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class ItemConfig {


    private final AllocationItemScraperBuilder allocationItemScraperBuilder;

    private final DataSource allocationDataSource;

    public ItemConfig(AllocationItemScraperBuilder allocationItemScraperBuilder,
                      @Qualifier("allocationDataSource") DataSource allocationDataSource)
    {
        this.allocationItemScraperBuilder = allocationItemScraperBuilder;
        this.allocationDataSource = allocationDataSource;
    }


    @Bean
    @StepScope
    public ItemStreamReader<Map<String, String>> dailyAllocationItemScraper(@Qualifier("dailyDateRange") DateRange dateRange) {
        return allocationItemScraperBuilder.setDateRange(dateRange).build();
    }


    @Bean
    @StepScope
    public ItemStreamReader<Map<String, String>> monthlyAllocationItemScraper(@Qualifier("monthlyDateRange") DateRange dateRange) {
        return allocationItemScraperBuilder.setDateRange(dateRange).build();
    }


    @Bean
    @StepScope
    public ItemWriter<Map<String, String>> allocationJdbcItemWriter() {
        return new JdbcBatchItemWriterBuilder<Map<String, String>>()
                .dataSource(allocationDataSource)
                .sql(SqlMappedContext.INSERT_ALLOCATION)
                .columnMapped()
                .build();
    }
}
