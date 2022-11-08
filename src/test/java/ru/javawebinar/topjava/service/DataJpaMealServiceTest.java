package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = "datajpa")
public class DataJpaMealServiceTest extends MealServiceTest{

    @Override
    public void getMealWithUser() {
        Meal meal = service.getMealWithUser(MEAL1_ID, USER_ID);
        MEAL_MATCHER.assertMatch(meal, meal1);
        USER_MATCHER.assertMatch(meal.getUser(), getWithMeal());
    }
}
