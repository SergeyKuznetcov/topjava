package ru.javawebinar.topjava.util.filter;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface FilterStrategy {
    List<Meal> filter(List<Meal> meals);
}
