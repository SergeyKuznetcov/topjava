package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Meal update(Meal meal, int userId) {
        return checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public List<MealTo> getAll(int userId) {
        return MealsUtil.getTos(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getFilteredByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return MealsUtil.getTos(repository.getFilteredByDate(userId, startDate, endDate), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getFilteredByTime(int userId, LocalTime startTime, LocalTime endTime) {
        return MealsUtil.getFilteredByTimeTos(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }

    public List<MealTo> getFilteredByDateTime(int userId, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate) {
        return MealsUtil.getFilteredByTimeTos(repository.getFilteredByDate(userId, startDate, endDate), MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }

}