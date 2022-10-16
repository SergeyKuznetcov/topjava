package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenTimeHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return isAfterTime(lt, startTime) && isBeforeTime(lt, endTime);
    }

    public static boolean isAfterTime(LocalTime lt, LocalTime startTime) {
        return lt.compareTo(startTime) >= 0;
    }

    public static boolean isBeforeTime(LocalTime lt, LocalTime endTime) {
        return lt.compareTo(endTime) < 0;
    }

    public static boolean isAfterDate(LocalDate ld, LocalDate startDate) {
        return ld.compareTo(startDate) >= 0;
    }

    public static boolean isBeforeDate(LocalDate ld, LocalDate endDate) {
        return ld.compareTo(endDate) <= 0;
    }

    public static boolean isBetweenDate(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return isAfterDate(ld, startDate) && isBeforeDate(ld, endDate);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

