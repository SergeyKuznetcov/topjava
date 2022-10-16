package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return userId != repository.get(meal.getId()).getUserId() ? null : repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return userId == repository.get(id).getUserId() && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return userId == meal.getUserId() ? meal : null;
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
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(getPredicate(startDate, endDate))
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
    }

    private Predicate<Meal> getPredicate(LocalDate startDate, LocalDate endDate) {
        if (startDate != null) {
            if (endDate == null) {
                return meal -> DateTimeUtil.isAfterDate(meal.getDate(), startDate);
            } else {
                return meal -> DateTimeUtil.isBetweenDate(meal.getDate(), startDate, endDate);
            }
        } else {
            if (endDate == null) {
                return meal -> true;
            } else {
                return meal -> DateTimeUtil.isBeforeDate(meal.getDate(), endDate);
            }
        }
    }
}

