package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class LocalDateAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalDateFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(LocalDate.class);
        return set;
    }

    @Override
    public Printer<?> getPrinter(LocalDateFormat annotation, Class<?> fieldType) {
        return new LocalDateFormatter();
    }

    @Override
    public Parser<?> getParser(LocalDateFormat annotation, Class<?> fieldType) {
        return new LocalDateFormatter();
    }
}
