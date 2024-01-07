package com.project.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static com.project.batch.JobConfig.DAILY_ALLOCATION_JOB;
import static com.project.batch.JobConfig.YEARLY_ALLOCATION_JOB;

@SpringBootTest
@SpringBatchTest
public class BatchTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    @Qualifier(DAILY_ALLOCATION_JOB)
    private Job dailyAllocationJob;

    @Autowired
    @Qualifier(YEARLY_ALLOCATION_JOB)
    private Job yearlyAllocationJob;


    @Test
    @DisplayName("일일 배차내역 조회 스크래퍼 테스트")
    public void dailyScraperTest() throws Exception {
        jobLauncherTestUtils.setJob(this.dailyAllocationJob);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

    @Test
    @DisplayName("연간 배차내역 조회 스크래퍼 테스트")
    public void yearlyScraperTest() throws Exception {
        jobLauncherTestUtils.setJob(this.yearlyAllocationJob);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        Assertions.assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }


}
