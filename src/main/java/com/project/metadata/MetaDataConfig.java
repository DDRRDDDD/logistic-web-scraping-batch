package com.project.metadata;

import com.project.page.object.AllocationPage;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetaDataConfig {

    @Bean
    public Menu<AllocationPage> allocationPageMenu() {
        return new Menu<>("배차내역조회", AllocationPage.class);
    }


    @Bean
    @JobScope
    public DateRange dailyDateRange(@Value("#{jobParameters[requestDate]}") String currentDate) {
        return DateRange.ofYesterday(currentDate);
    }


    @Bean
    @JobScope
    public DateRange monthlyDateRange(@Value("#{jobParameters[requestDate]}") String currentDate) {
        return DateRange.ofMonth(currentDate);
    }


}
