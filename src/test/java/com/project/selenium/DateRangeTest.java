package com.project.selenium;

import com.project.metadata.DateRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("하루 출력 테스트")
    public void dateRangeOfMonthTest(){
        DateRange dateRange = DateRange.ofDay(2023, 1, 1);

        Assertions.assertEquals("2023-01-01", dateRange.getStartDateOfMonth());
        Assertions.assertEquals("2023-01-01", dateRange.getEndDateOfMonth());
    }

    /**
     * 해당 테스트 코드는 2023.12.14 기준으로 작성되었습니다.
     * 테스트를 다시 실행하려면 기대값을 수정하세요.
     */
    @Test
    @DisplayName("오늘 날짜 출력 테스트")
    public void dateRangeOfTodayTest(){
        DateRange dateRange = DateRange.ofToday();

        Assertions.assertEquals("2023-12-14", dateRange.getStartDateOfMonth());
        Assertions.assertEquals("2023-12-14", dateRange.getEndDateOfMonth());
    }

}
