package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.user1Meals.forEach(meal -> save(meal, 1));
        MealsUtil.user2Meals.forEach(meal -> save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            if (userMeals == null) {
                repository.putIfAbsent(userId, new ConcurrentHashMap<>());
            }
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        if (userMeals != null) {
            return userMeals.computeIfPresent(meal.getId(), (id, oldVal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals != null) {
            return userMeals.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        if (userMeals != null) {
            return userMeals.get(id);
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllFilteredByDate(userId, null, null);
    }

    @Override
    public List<Meal> getFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return getAllFilteredByDate(userId, startDate, endDate);
    }

    private List<Meal> getAllFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals == null ? Collections.EMPTY_LIST : userMeals.values().stream()
                .filter(meal -> DateTimeUtil.isBetweenDateOrTime(meal.getDate(), startDate, endDate, false))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

