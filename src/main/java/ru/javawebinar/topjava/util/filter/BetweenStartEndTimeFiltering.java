package ru.javawebinar.topjava.util.filter;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

public class BetweenStartEndTimeFiltering implements FilterStrategy {
    private final LocalTime startTime;
    private final LocalTime endTime;

    public BetweenStartEndTimeFiltering(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Stream<Meal> filter(List<Meal> meals) {
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime));
    }
}
