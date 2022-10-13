package ru.javawebinar.topjava.util.filter;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.stream.Stream;

public class NoFiltering implements FilterStrategy {
    @Override
    public Stream<Meal> filter(List<Meal> meals) {
        return meals.stream();
    }
}
