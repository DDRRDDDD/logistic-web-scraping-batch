package com.project.selenium;

import com.project.scraper.metadata.DateRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateRangeTest {

    @Test
    @DisplayName("시작일과 마지막일이 잘 출력이 되는가?")
    public void test1(){
        DateRange date = DateRange.ofMonth(2023, 2);

        Assertions.assertEquals("2023-02-01", date.getStartDateOfMonth());
        Assertions.assertEquals("2023-02-28", date.getEndDateOfMonth());
    }

}
