package com.project.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.project.job.StepConfig.*;


@Configuration
@RequiredArgsConstructor
public class JobConfig {

    public static final String DAILY_ALLOCATION_JOB = "DAILY_ALLOCATION_JOB";
    public static final String MONTHLY_ALLOCATION_JOB = "MONTHLY_ALLOCATION_JOB";

    private final JobRepository jobRepository;


    @Bean(DAILY_ALLOCATION_JOB)
    public Job dailyAllocationJob(@Qualifier(DAILY_ALLOCATION_STEP) Step dailyStep){
        return new JobBuilder(DAILY_ALLOCATION_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(dailyStep)
                .build();
    }



    @Bean(MONTHLY_ALLOCATION_JOB)
    public Job monthlyAllocationJob(@Qualifier(MONTHLY_ALLOCATION_STEP) Step monthlyStep){
        return new JobBuilder(MONTHLY_ALLOCATION_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(monthlyStep)
                .build();
    }
}
