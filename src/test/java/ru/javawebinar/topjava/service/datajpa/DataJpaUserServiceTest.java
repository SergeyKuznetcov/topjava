package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Override
    public void getUserWithMeal() {
        User dbUser = service.getUserWithMeal(USER_ID);
        USER_MATCHER.assertMatch(user, dbUser);
        MealTestData.MEAL_MATCHER.assertMatch(dbUser.getMeals(), MealTestData.meals);
    }
}
