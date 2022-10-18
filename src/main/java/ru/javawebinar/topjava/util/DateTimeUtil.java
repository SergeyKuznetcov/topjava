package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenDateOrTime(T ldt, T start, T end, boolean exclusive) {
        return start == null ? (end == null || isBefore(ldt, end, exclusive)) : (end == null ? isAfter(ldt, start) : isBetween(ldt, start, end, exclusive));
    }

    private static <T extends Comparable<T>> boolean isBetween(T ldt, T start, T end, boolean exclusive) {
        return isAfter(ldt, start) && isBefore(ldt, end, exclusive);
    }

    private static <T extends Comparable<T>> boolean isAfter(T ldt, T start) {
        return ldt.compareTo(start) >= 0;
    }

    private static <T extends Comparable<T>> boolean isBefore(T ldt, T end, boolean exclusive) {
        return exclusive ? ldt.compareTo(end) < 0 : ldt.compareTo(end) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

