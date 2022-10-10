package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MealsStorage implements Storage {
    private volatile static MealsStorage instance;
    private final ConcurrentMap<Integer, Meal> storage;

    private MealsStorage() {
        this.storage = new ConcurrentHashMap<>();
    }

    public static MealsStorage getInstance() {
        if (instance == null) {
            synchronized (MealsStorage.class) {
                if (instance == null) {
                    instance = new MealsStorage();
                    instance.saveTestValues();
                }
            }
        }
        return instance;
    }

    private void saveTestValues(){
        for (Meal meal :
                MealsUtil.getTestMeals()) {
            save(meal);
        }
    }

    @Override
    public void save(Meal meal) {
        storage.put(meal.getId(), meal);
    }

    @Override
    public Meal get(Integer key) {
        return storage.get(key);
    }

    @Override
    public void update(Meal meal) {
        storage.replace(meal.getId(), meal);
    }

    @Override
    public void delete(Integer key) {
        storage.remove(key);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }
}
