package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> mealsUser1 = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак user1", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед user1", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин user1", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение user1", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак user1", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед user1", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин user1", 410)
    );

    public static final List<Meal> mealsUser2 = Arrays.asList(
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак user2", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед user2", 1000),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 21, 0), "Ужин user2", 100),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Еда на граничное значение user2", 1000),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 14, 0), "Обед user2", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 19, 0), "Ужин user2", 400)
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filterByTime(meals, caloriesPerDay, null, null);
    }

    public static List<MealTo> getFilteredByTimeTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return filterByTime(meals, caloriesPerDay, startTime, endTime);
    }

    private static List<MealTo> filterByTime(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum));
        return meals.stream()
                .filter(meal -> DateTimeUtil.isBetweenTimeHalfOpenDateExclusive(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
