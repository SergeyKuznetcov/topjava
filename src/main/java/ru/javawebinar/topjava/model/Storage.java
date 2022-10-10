package ru.javawebinar.topjava.model;

import java.util.List;

public interface Storage {
    void save(Meal meal);
    Meal get(Integer id);
    void update(Meal meal);
    void delete(Integer key);
    List<Meal> getAll();
}
