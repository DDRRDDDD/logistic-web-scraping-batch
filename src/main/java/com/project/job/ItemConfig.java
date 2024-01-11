package com.project.job;

import com.project.datasource.sql.SqlMappedContext;
import com.project.metadata.DateRange;
import com.project.scraper.AllocationItemScraperBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ItemConfig {


    private final AllocationItemScraperBuilder allocationItemScraperBuilder;

    private final DataSource hikariDataSource;


    @Bean
    @StepScope
    public ItemStreamReader<Map<String, String>> dailyAllocationItemScraper() {
        return allocationItemScraperBuilder
                .setDateRange(DateRange.ofYesterday(LocalDate.now()))
                .build();
    }


    @Bean
    @StepScope
    public ItemStreamReader<Map<String, String>> yearlyAllocationItemScraper(@Value("#{jobParameters[year]}") int registrationYear) {
        return allocationItemScraperBuilder
                .setDateRange(DateRange.ofYear(registrationYear))
                .build();
    }


    @Bean
    @StepScope
    public ItemWriter<Map<String, String>> allocationJdbcItemWriter() {
        return new JdbcBatchItemWriterBuilder<Map<String, String>>()
                .dataSource(hikariDataSource)
                .columnMapped()
                .sql(SqlMappedContext.INSERT_ALLOCATION)
                .build();
    }
}
