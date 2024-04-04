package com.project.batch;

import com.project.scraper.WebScraperItemReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Slf4j
@Configuration
public class StepConfig {

    public static final String DAILY_A_SCENARIO_STEP = "DAILY_A_SCENARIO_STEP";
    public static final String MONTHLY_A_SCENARIO_STEP = "MONTHLY_A_SCENARIO_STEP";

    private final JobRepository jobRepository;

    private final PlatformTransactionManager allocationTransactionManager;


    @Value("${chunkSize:10}")
    private int chunkSize;


    public StepConfig(@Qualifier("allocationTransactionManager") PlatformTransactionManager transactionManager,
                      JobRepository jobRepository) {
        this.jobRepository = jobRepository;
        this.allocationTransactionManager = transactionManager;
    }


    @JobScope
    @Bean(DAILY_A_SCENARIO_STEP)
    public Step dailyAllocationStep(@Qualifier("dailyAScenarioScraper") WebScraperItemReader<Map<String, String>> itemReader,
                                    JdbcBatchItemWriter<Map<String, String>> itemWriter) {
        return new StepBuilder(DAILY_A_SCENARIO_STEP, jobRepository)
                .<Map<String, String>, Map<String, String>>chunk(chunkSize, allocationTransactionManager)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }


    @JobScope
    @Bean(MONTHLY_A_SCENARIO_STEP)
    public Step yearlyAllocationStep(@Qualifier("monthlyAScenarioScraper") WebScraperItemReader<Map<String, String>> itemReader,
                                     JdbcBatchItemWriter<Map<String, String>> itemWriter) {
        return new StepBuilder(MONTHLY_A_SCENARIO_STEP, jobRepository)
                .<Map<String, String>, Map<String, String>>chunk(chunkSize, allocationTransactionManager)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }


}
