package com.project.batch;

import com.project.batch.mapped.SqlMappedContext;
import com.project.metadata.DateRange;
import com.project.scraper.AllocationItemScraperBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ItemConfig {

    private final AllocationItemScraperBuilder allocationItemScraperBuilder;

    private final DataSource hikariDataSource;


    @Bean
    @StepScope
    public ItemReader<Map<String, String>> dailyAllocationItemScraper(){
        return allocationItemScraperBuilder
                .setDateRange(DateRange.ofToday())
                .build();
    }


    @Bean
    @StepScope
    public ItemReader<Map<String, String>> yearlyAllocationItemScraper(){
        return allocationItemScraperBuilder
                .setDateRange(DateRange.ofThisYear())
                .build();
    }


    @Bean
    @StepScope
    public ItemWriter<Map<String, String>> allocationJdbcItemWriter(){
        return new JdbcBatchItemWriterBuilder<Map<String, String>>()
                .dataSource(hikariDataSource)
                .beanMapped()
                .sql(SqlMappedContext.INSERT_ALLOCATION)
                .build();
    }
}
