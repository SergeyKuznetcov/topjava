package ru.javawebinar.topjava.util.filter;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.stream.Stream;

public interface FilterStrategy {
    Stream<Meal> filter(List<Meal> meals);
}
