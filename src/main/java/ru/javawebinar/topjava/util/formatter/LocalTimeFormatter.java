package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(@Nullable String text, Locale locale) throws ParseException {
        return text != null && text.length() != 0 ? LocalTime.of(Integer.parseInt(text.substring(0, 2)), Integer.parseInt(text.substring(3, 4))) : null;
    }

    @Override
    public String print(@Nullable LocalTime object, Locale locale) {
        return object == null ? "" : object.format(DateTimeFormatter.ISO_TIME).substring(0, 5);
    }
}
