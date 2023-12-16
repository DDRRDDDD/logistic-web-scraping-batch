package com.project.selenium;

import com.project.scraper.AllocationItemScraperBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BatchTest {

    private final Logger log = LoggerFactory.getLogger(BatchTest.class);

    private final ApplicationContext context;

    private final DataSource dataSource;

    @Autowired
    public BatchTest(ApplicationContext context, DataSource dataSource){
        this.context = context;
        this.dataSource = dataSource;
    }

    @Test
    @DisplayName("배차 조회 스크래퍼 빌더 클래스 테스트")
    public void allocationItemScraperBuilderTest(){
        AllocationItemScraperBuilder builder1 = context.getBean(AllocationItemScraperBuilder.class);
        AllocationItemScraperBuilder builder2 = context.getBean(AllocationItemScraperBuilder.class);

        Assertions.assertNotEquals(builder1, builder2);
    }

    @Test
    @DisplayName("데이터 소스의 구현 클래스 확인")
    public void defaultDataSourceTest(){
        String className = dataSource.getClass().getSimpleName();
        log.info("DataSource class >>> {}", className);
    }
}
