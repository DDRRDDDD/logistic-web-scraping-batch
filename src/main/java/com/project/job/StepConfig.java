package com.project.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Slf4j
@Configuration
public class StepConfig {

    public static final String DAILY_ALLOCATION_STEP = "DAILY_ALLOCATION_STEP";
    public static final String MONTHLY_ALLOCATION_STEP = "MONTHLY_ALLOCATION_STEP";

    private final JobRepository jobRepository;

    private final PlatformTransactionManager allocationTransactionManager;

    private final ChunkListener chunkListener;


    @Value("${chunkSize:10}")
    private int chunkSize;


    public StepConfig(JobRepository jobRepository, ChunkListener chunkListener,
                      @Qualifier("allocationTransactionManager") PlatformTransactionManager transactionManager)
    {
        this.jobRepository = jobRepository;
        this.chunkListener = chunkListener;
        this.allocationTransactionManager = transactionManager;
    }


    @JobScope
    @Bean(DAILY_ALLOCATION_STEP)
    public Step dailyAllocationStep(@Qualifier("dailyAllocationItemScraper") ItemStreamReader<Map<String, String>> itemReader,
                                    ItemWriter<Map<String, String>> itemWriter) {
        return new StepBuilder(DAILY_ALLOCATION_STEP, jobRepository)
                .<Map<String, String>, Map<String, String>>chunk(chunkSize, allocationTransactionManager)
                .listener(chunkListener)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }


    @JobScope
    @Bean(MONTHLY_ALLOCATION_STEP)
    public Step yearlyAllocationStep(@Qualifier("monthlyAllocationItemScraper") ItemStreamReader<Map<String, String>> itemReader,
                                     ItemWriter<Map<String, String>> itemWriter) {
        return new StepBuilder(MONTHLY_ALLOCATION_STEP, jobRepository)
                .<Map<String, String>, Map<String, String>>chunk(chunkSize, allocationTransactionManager)
                .listener(chunkListener)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }

}
