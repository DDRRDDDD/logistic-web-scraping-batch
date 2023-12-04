package com.project.metadata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class DateRange {

    private static final int FIRST_DAY_OF_MONTH = 1;

    private final String startDateOfMonth;
    private final String endDateOfMonth;

    public static DateRange ofMonth(int year, int month) {
        LocalDate startDateOfMonth = LocalDate.of(year, month, FIRST_DAY_OF_MONTH);
        LocalDate endDateOfMonth = LocalDate.of(year, month, startDateOfMonth.lengthOfMonth());
        return new DateRange(startDateOfMonth.toString(), endDateOfMonth.toString());
    }

    public static DateRange ofDay(int year, int month, int day) {
        LocalDate dateOfMonth = LocalDate.of(year, month, day);
        return new DateRange(dateOfMonth.toString(), dateOfMonth.toString());
    }
}
