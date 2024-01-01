package com.project.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class StepConfig {

    public static final String DAILY_ALLOCATION_STEP = "DAILY_ALLOCATION_STEP";
    public static final String YEARLY_ALLOCATION_STEP = "YEARLY_ALLOCATION_STEP";

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    // jobparameter로 수정할거임
    private final int chunkSize = 10;


    @JobScope
    @Bean(DAILY_ALLOCATION_STEP)
    public Step dailyAllocationStep(@Qualifier("dailyAllocationItemScraper") ItemReader<Map<String, String>> itemReader,
                                     ItemWriter<Map<String, String>> itemWriter) {
        return new StepBuilder(DAILY_ALLOCATION_STEP, jobRepository)
                .<Map<String, String>, Map<String, String>>chunk(chunkSize, transactionManager)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }


    @JobScope
    @Bean(YEARLY_ALLOCATION_STEP)
    public Step yearlyAllocationStep(@Qualifier("yearlyAllocationItemScraper") ItemReader<Map<String, String>> itemReader,
                                     ItemWriter<Map<String, String>> itemWriter) {
        return new StepBuilder(YEARLY_ALLOCATION_STEP, jobRepository)
                .<Map<String, String>, Map<String, String>>chunk(chunkSize, transactionManager)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }


}
