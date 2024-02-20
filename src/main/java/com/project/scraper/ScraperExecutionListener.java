package com.project.scraper;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

@JobScope
@Component
@RequiredArgsConstructor
public class ScraperExecutionListener implements JobExecutionListener {

    private final WebDriverManager webDriverManager;


    @Override
    public void afterJob(JobExecution jobExecution) {
        webDriverManager.quit();
    }
}
