package com.project.selenium;

import com.project.metadata.DateRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DateRangeTest {


    @Test
    @DisplayName("지정 날짜 범위 출력 테스트")
    public void dateRangeTest(){
        DateRange dateRange = new DateRange("2024-01-12", "2024-01-13");

        Assertions.assertEquals("2024-01-12", dateRange.getStartDateOfMonth());
        Assertions.assertEquals("2024-01-13", dateRange.getEndDateOfMonth());
    }

    @Test
    @DisplayName("String 파라미터 하나를 받아서 어제의 날짜 범위를 출력한다")
    public void dailyDateRangeTest(){
        DateRange dateRange = DateRange.ofYesterday("2023-01-12");

        Assertions.assertEquals("2023-01-11", dateRange.getStartDateOfMonth());
        Assertions.assertEquals("2023-01-11", dateRange.getEndDateOfMonth());
    }


    @Test
    @DisplayName("String 파라미터 하나를 받아서 1년의 날짜 범위를 출력한다 (1월 1일 ~ 12월 31)")
    public void yearlyDateRangeTest(){
        DateRange dateRange = DateRange.ofYear("2023-02-10");

        Assertions.assertEquals("2023-01-01", dateRange.getStartDateOfMonth());
        Assertions.assertEquals("2023-12-31", dateRange.getEndDateOfMonth());
    }
}
