package com.project.selenium;

import com.project.metadata.DateRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DateRangeTest {

    @Test
    @DisplayName("시작일과 마지막일이 잘 출력이 되는가?")
    public void test1(){
        DateRange dateRange = DateRange.ofMonth(2023, 2);

        Assertions.assertEquals("2023-02-01", dateRange.getStartDateOfMonth());
        Assertions.assertEquals("2023-02-28", dateRange.getEndDateOfMonth());
    }

    @Test
    @DisplayName("입력 년도의 (1월 1일 / 12월 31일) 출력 테스트")
    public void dateRangeOfYearTest(){
        DateRange dateRange = DateRange.ofYear(2023);

        Assertions.assertEquals("2023-01-01", dateRange.getStartDateOfMonth());
        Assertions.assertEquals("2023-12-31", dateRange.getEndDateOfMonth());
    }

    @Test
    @DisplayName("이번 년도의 (1월 1일 / 12월 31일) 출력 테스트")
    public void dateRangeOfThisYearTest(){
        DateRange dateRange = DateRange.ofThisYear();

        Assertions.assertEquals("2023-01-01", dateRange.getStartDateOfMonth());
        Assertions.assertEquals("2023-12-31", dateRange.getEndDateOfMonth());
    }

    @Test
    @DisplayName("하루 출력 테스트 (int)")
    public void dateRangeOfMonthTest(){
        DateRange dateRange = DateRange.ofDay(2023, 1, 1);

        Assertions.assertEquals("2023-01-01", dateRange.getStartDateOfMonth());
        Assertions.assertEquals("2023-01-01", dateRange.getEndDateOfMonth());
    }

    @Test
    @DisplayName("하루 출력 테스트 (LocalDate)")
    public void dateRangeOfDayTest(){
           DateRange dateRange = DateRange.ofYesterday(LocalDate.of(2024, 1, 1));

           Assertions.assertEquals("2024-01-01", dateRange.getStartDateOfMonth());
           Assertions.assertEquals("2024-01-01", dateRange.getEndDateOfMonth());
    }
}
