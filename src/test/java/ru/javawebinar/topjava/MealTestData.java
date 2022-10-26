package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 10;
    public static final int USER_MEAL1_ID = START_SEQ + 3;
    public static final int USER_MEAL2_ID = START_SEQ + 4;
    public static final int USER_MEAL3_ID = START_SEQ + 5;
    public static final int USER_MEAL4_ID = START_SEQ + 6;
    public static final int USER_MEAL5_ID = START_SEQ + 7;
    public static final int ADMIN_MEAL1_ID = START_SEQ + 8;
    public static final int ADMIN_MEAL2_ID = START_SEQ + 9;
    public static final int ADMIN_MEAL3_ID = START_SEQ + 10;
    public static final int ADMIN_MEAL4_ID = START_SEQ + 11;

    public static final LocalDate startDate = LocalDate.of(2022, 4, 4);
    public static final LocalDate endDate = LocalDate.of(2022, 4, 6);

    public static final Meal userMeal1 = new Meal(USER_MEAL1_ID,
            LocalDateTime.of(2022, 4, 1, 9, 0),
            "Завтрак user", 500);
    public static final Meal userMeal2 = new Meal(USER_MEAL2_ID,
            LocalDateTime.of(2022, 4, 4, 13, 0),
            "Обед user", 800);
    public static final Meal userMeal3 = new Meal(USER_MEAL3_ID,
            LocalDateTime.of(2022, 4, 4, 19, 0),
            "Ужин user", 600);
    public static final Meal userMeal4 = new Meal(USER_MEAL4_ID,
            LocalDateTime.of(2022, 4, 5, 1, 0),
            "Ночной перекус user", 1500);
    public static final Meal userMeal5 = new Meal(USER_MEAL5_ID,
            LocalDateTime.of(2022, 4, 6, 8, 0),
            "Завтрак user", 400);
    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL1_ID,
            LocalDateTime.of(2022, 4, 8, 12, 0),
            "Обед admin", 500);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL2_ID,
            LocalDateTime.of(2022, 4, 8, 20, 0),
            "Ужин admin", 1500);
    public static final Meal adminMeal3 = new Meal(ADMIN_MEAL3_ID,
            LocalDateTime.of(2022, 4, 9, 9, 0),
            "Завтрак admin", 400);
    public static final Meal adminMeal4 = new Meal(ADMIN_MEAL4_ID,
            LocalDateTime.of(2022, 4, 10, 0, 0),
            "Пограничное время admin", 400);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2022, 1, 1, 9, 0), "new meal", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(userMeal1);
        updated.setDateTime(LocalDateTime.of(2022, 1, 1, 1, 0));
        updated.setDescription("updated meal");
        updated.setCalories(1000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
