package com.project.selenium;


import com.project.scraper.WebScraper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class WebScraperTest {

    private final Logger log = LoggerFactory.getLogger(WebScraperTest.class);
    private final WebScraper dailyAllocationScraper;

    private final WebScraper annualAllocationScraper;

    @Autowired
    public WebScraperTest(@Qualifier("dailyAllocationScraper")WebScraper dailyAllocationScraper,
                          @Qualifier("annualAllocationScraper")WebScraper annualAllocationScraper) {
        this.dailyAllocationScraper = dailyAllocationScraper;
        this.annualAllocationScraper = annualAllocationScraper;
    }

    @Test
    @DisplayName("일일 배차 내역 조회 스크래퍼 테스트")
    public void dailyScraperTest(){
        List<Map<String, String>> mapList = dailyAllocationScraper.fetchData();
        log.info("mapList size >> {}", mapList.size());

        for(Map<String, String> map : mapList){
            map.keySet().forEach((key) -> log.info("{} :: {}", key, map.get(key)));
        }
    }

    @Test
    @DisplayName("연간 배차 내역 조회 스크래퍼 테스트")
    public void annualScraperTest(){
        List<Map<String, String>> mapList = annualAllocationScraper.fetchData();
        log.info("mapList size >> {}", mapList.size());

        for(Map<String, String> map : mapList){
            map.keySet().forEach((key) -> log.info("{} :: {}", key, map.get(key)));
        }
    }

}
