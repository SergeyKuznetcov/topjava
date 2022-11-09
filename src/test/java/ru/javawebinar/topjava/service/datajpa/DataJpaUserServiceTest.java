package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Override
    public void getUserWithMeal() {
        User dbUser = service.getUserWithMeal(USER_ID);
        USER_MATCHER.assertMatch(dbUser, user);
        MealTestData.MEAL_MATCHER.assertMatch(dbUser.getMeals(), MealTestData.meals);
    }

    @Test
    public void getNotFoundUserWithMeal() {
        assertThrows(NotFoundException.class, () -> service.getUserWithMeal(NOT_FOUND));
    }
}
