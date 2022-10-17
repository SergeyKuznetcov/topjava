package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenTimeHalfOpenDateExclusive(T ldt, T start, T end) {
        if (start != null) {
            if (end != null) {
                return ldt.compareTo(start) >= 0 && ldt.getClass().equals(LocalTime.class) ? ldt.compareTo(end) < 0 : ldt.compareTo(end) <= 0;
            } else {
                return ldt.compareTo(start) >= 0;
            }
        } else {
            if (end != null) {
                return ldt.getClass().equals(LocalTime.class) ? ldt.compareTo(end) < 0 : ldt.compareTo(end) <= 0;
            } else {
                return true;
            }
        }
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

