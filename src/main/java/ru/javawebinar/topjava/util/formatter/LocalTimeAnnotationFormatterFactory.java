package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class LocalTimeAnnotationFormatterFactory implements AnnotationFormatterFactory<LocalTimeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> set = new HashSet<>();
        set.add(LocalTime.class);
        return set;
    }

    @Override
    public Printer<?> getPrinter(LocalTimeFormat annotation, Class<?> fieldType) {
        return new LocalTimeFormatter();
    }

    @Override
    public Parser<?> getParser(LocalTimeFormat annotation, Class<?> fieldType) {
        return new LocalTimeFormatter();
    }
}
