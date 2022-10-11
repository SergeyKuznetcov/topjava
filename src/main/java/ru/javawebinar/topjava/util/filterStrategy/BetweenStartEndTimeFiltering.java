package ru.javawebinar.topjava.util.filterStrategy;

import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class BetweenStartEndTimeFiltering implements FilterStrategy {
    private final LocalTime startTime;
    private final LocalTime endTime;

    public BetweenStartEndTimeFiltering(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public List<MealTo> filter(List<MealTo> mealTos) {
        return mealTos.stream()
                .filter(mealTo -> TimeUtil.isBetweenHalfOpen(mealTo.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}
