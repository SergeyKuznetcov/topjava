package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(@Nullable String text, Locale locale) throws ParseException {
        return text != null && text.length() != 0 ? LocalDate.parse(text, DateTimeFormatter.ISO_DATE) : null;
    }

    @Override
    public String print(@Nullable LocalDate object, Locale locale) {
        return object != null ? object.format(DateTimeFormatter.ISO_DATE):"";
    }
}
