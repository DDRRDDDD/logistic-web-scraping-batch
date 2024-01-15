package com.project.metadata;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Getter
@RequiredArgsConstructor
public class DateRange {

    public static final int ONE_DAY = 1;

    private final String startDateOfMonth;
    private final String endDateOfMonth;

    public static DateRange ofYesterday(@NonNull String dateParameter){
        LocalDate date = LocalDate.parse(dateParameter, ISO_LOCAL_DATE).minusDays(ONE_DAY);

        return new DateRange(date.toString(), date.toString());
    }

    public static DateRange ofYear(@NonNull String dateParameter){
        LocalDate date = LocalDate.parse(dateParameter, ISO_LOCAL_DATE);

        String startDateOfMonth = date.with(TemporalAdjusters.firstDayOfYear()).toString();
        String endDateOfMonth = date.with(TemporalAdjusters.lastDayOfYear()).toString();

        return new DateRange(startDateOfMonth, endDateOfMonth);
    }
}
