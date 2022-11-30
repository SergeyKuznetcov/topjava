package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(@Nullable String text, Locale locale) throws ParseException {
        return text != null && text.length() != 0 ? LocalDate.of(Integer.parseInt(text.substring(0,4)),
                Integer.parseInt(text.substring(6,7)), Integer.parseInt(text.substring(9,10))) : null;
    }

    @Override
    public String print(@Nullable LocalDate object, Locale locale) {
        return object != null ? object.getYear() + "-" + object.getMonthValue() + "-" + object.getDayOfMonth() : "";
    }
}
