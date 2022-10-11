package ru.javawebinar.topjava.util.filterStrategy;

import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface FilterStrategy {
    List<MealTo> filter(List<MealTo> mealTos);
}
