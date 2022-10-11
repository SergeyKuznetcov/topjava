package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    Meal create(Meal meal);

    Meal get(int id);

    Meal update(Meal meal);

    void delete(int id);

    List<Meal> getAll();
}
