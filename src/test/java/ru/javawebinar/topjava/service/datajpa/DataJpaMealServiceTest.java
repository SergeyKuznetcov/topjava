package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Override
    public void getMealWithUser() {
        Meal meal = service.getMealWithUser(MEAL1_ID, USER_ID);
        MEAL_MATCHER.assertMatch(meal, meal1);
        USER_MATCHER.assertMatch(meal.getUser(), user);
    }

    @Test
    public void getNotOwnMealWithUser() {
        assertThrows(NotFoundException.class, () -> service.getMealWithUser(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void getNotFoundMealWithUser() {
        assertThrows(NotFoundException.class, () -> service.getMealWithUser(MealTestData.NOT_FOUND, USER_ID));
    }
}
