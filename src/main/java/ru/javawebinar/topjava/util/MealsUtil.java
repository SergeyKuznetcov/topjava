package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.filterStrategy.BetweenStartEndTimeFiltering;
import ru.javawebinar.topjava.util.filterStrategy.FilterStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {

    public static void main(String[] args) {
        List<Meal> meals = getTestMeals();

        List<MealTo> mealsTo = createTos(meals, 2000, new BetweenStartEndTimeFiltering(LocalTime.of(7, 0), LocalTime.of(12, 0)));
        mealsTo.forEach(System.out::println);
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId() == null ? 0 : meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public static List<MealTo> createTos(List<Meal> mealList, int caloriesPerDay, FilterStrategy filterStrategy) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream()
                .collect(Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum));
        return filterStrategy.filter(mealList.stream()
                .map(meal -> createTo(meal, caloriesPerDay < caloriesSumByDate.get(meal.getDate())))
                .collect(Collectors.toList()));
    }

    public static List<Meal> getTestMeals() {
        return Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
    }
}
