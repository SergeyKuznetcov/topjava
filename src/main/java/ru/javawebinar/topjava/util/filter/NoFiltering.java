package ru.javawebinar.topjava.util.filter;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class NoFiltering implements FilterStrategy {
    @Override
    public List<Meal> filter(List<Meal> meals) {
        return meals;
    }
}
