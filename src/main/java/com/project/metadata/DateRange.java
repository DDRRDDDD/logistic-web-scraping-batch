package com.project.metadata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Year;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class DateRange {

    // 1월 1일
    private static final int FIRST_MONTH_OF_YEAR = 1;
    private static final int FIRST_DAY_OF_MONTH = 1;

    // 12월 31일
    private static final int LAST_MONTH_OF_YEAR = 12;
    private static final int LAST_DAY_OF_MONTH = 31;

    private final String startDateOfMonth;
    private final String endDateOfMonth;


    public static DateRange ofYear(int year){
        LocalDate startDateOfMonth = LocalDate.of(year, FIRST_MONTH_OF_YEAR, FIRST_DAY_OF_MONTH);
        LocalDate endDateOfMonth = LocalDate.of(year, LAST_MONTH_OF_YEAR, LAST_DAY_OF_MONTH);
        return new DateRange(startDateOfMonth.toString(), endDateOfMonth.toString());
    }


    public static DateRange ofThisYear(){
        return ofYear(Year.now().getValue());
    }


    public static DateRange ofMonth(int year, int month) {
        LocalDate startDateOfMonth = LocalDate.of(year, month, FIRST_DAY_OF_MONTH);
        LocalDate endDateOfMonth = LocalDate.of(year, month, startDateOfMonth.lengthOfMonth());
        return new DateRange(startDateOfMonth.toString(), endDateOfMonth.toString());
    }


    public static DateRange ofDay(int year, int month, int day) {
        LocalDate dateOfMonth = LocalDate.of(year, month, day);
        return new DateRange(dateOfMonth.toString(), dateOfMonth.toString());
    }


    public static DateRange ofToday(){
        LocalDate currentDate = LocalDate.now();
        return ofDay(currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth());
    }

}
