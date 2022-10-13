package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealsStorage implements Storage {
    private final Map<Integer, Meal> storage;
    private final AtomicInteger counter;

    public MemoryMealsStorage() {
        storage = new ConcurrentHashMap<>();
        counter = new AtomicInteger(0);
        saveTestValues();
    }

    private void saveTestValues() {
        for (Meal meal : MealsUtil.getTestMeals()) {
            create(meal);
        }
    }

    @Override
    public Meal create(Meal meal) {
        int id = counter.incrementAndGet();
        Meal newMeal = new Meal(id, meal.getDateTime(), meal.getDescription(), meal.getCalories());
        storage.put(id, newMeal);
        return newMeal;
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        return storage.replace(meal.getId(), meal) == null ? null : meal;
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }
}
