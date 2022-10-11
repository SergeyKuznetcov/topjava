package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsMapStorage implements Storage {
    private final Map<Integer, Meal> storage;
    private final AtomicInteger counter;

    public MealsMapStorage(boolean addTestValues) {
        this.storage = new ConcurrentHashMap<>();
        counter = new AtomicInteger(0);
        if (addTestValues) {
            saveTestValues();
        }
    }

    private void saveTestValues() {
        for (Meal meal : MealsUtil.getTestMeals()) {
            create(meal);
        }
    }

    @Override
    public Meal create(Meal meal) {
        int id = counter.incrementAndGet();
        storage.put(id, new Meal(id, meal.getDateTime(), meal.getDescription(), meal.getCalories()));
        return get(id);
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        storage.replace(meal.getId(), meal);
        return storage.get(meal.getId());
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
