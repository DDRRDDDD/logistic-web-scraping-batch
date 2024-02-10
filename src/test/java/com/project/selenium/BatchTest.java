package com.project.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static com.project.job.JobConfig.DAILY_ALLOCATION_JOB;
import static com.project.job.JobConfig.MONTHLY_ALLOCATION_JOB;

@SpringBootTest
@SpringBatchTest
public class BatchTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    @Qualifier(DAILY_ALLOCATION_JOB)
    private Job dailyAllocationJob;

    @Autowired
    @Qualifier(MONTHLY_ALLOCATION_JOB)
    private Job monthlyAllocationJob;


    private JobParameters jobParameters;

    @BeforeEach
    public void beforeBatchTest(){
        jobParameters = new JobParametersBuilder()
                .addString("requestDate", "2023-10-20")
                .toJobParameters();
    }

    @Test
    @DisplayName("일일 배차내역 조회 스크래퍼 테스트")
    public void dailyScraperTest() throws Exception {
        jobLauncherTestUtils.setJob(this.dailyAllocationJob);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

    @Test
    @DisplayName("월간 배차내역 조회 스크래퍼 테스트")
    public void monthlyScraperTest() throws Exception {
        jobLauncherTestUtils.setJob(this.monthlyAllocationJob);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }


}
