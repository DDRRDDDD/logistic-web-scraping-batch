package com.project.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
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

    public static final String DAILY_A_SCENARIO_JOB = "DAILY_A_SCENARIO_JOB";
    public static final String MONTHLY_A_SCENARIO_JOB = "MONTHLY_A_SCENARIO_JOB";

    private final JobRepository jobRepository;

    private final JobExecutionListener jobExecutionListener;


    @Bean(DAILY_A_SCENARIO_JOB)
    public Job dailyAllocationJob(@Qualifier(DAILY_A_SCENARIO_STEP) Step dailyStep){
        return new JobBuilder(DAILY_A_SCENARIO_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutionListener)
                .start(dailyStep)
                .build();
    }



    @Bean(MONTHLY_A_SCENARIO_JOB)
    public Job monthlyAllocationJob(@Qualifier(MONTHLY_A_SCENARIO_STEP) Step monthlyStep){
        return new JobBuilder(MONTHLY_A_SCENARIO_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(jobExecutionListener)
                .start(monthlyStep)
                .build();
    }
}
