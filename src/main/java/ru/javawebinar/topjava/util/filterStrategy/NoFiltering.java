package ru.javawebinar.topjava.util.filterStrategy;

import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public class NoFiltering implements FilterStrategy {
    @Override
    public List<MealTo> filter(List<MealTo> mealTos) {
        return mealTos;
    }
}
