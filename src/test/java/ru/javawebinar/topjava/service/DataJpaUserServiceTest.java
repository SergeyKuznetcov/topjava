package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = "datajpa")
public class DataJpaUserServiceTest extends UserServiceTest{

    @Override
    public void getUserWithMeal() {
        User dbUser = service.getUserWithMeal(USER_ID);
        USER_MATCHER.assertMatch(user, dbUser);
        MealTestData.MEAL_MATCHER.assertMatch(dbUser.getMeals(), MealTestData.meals);
    }
}
