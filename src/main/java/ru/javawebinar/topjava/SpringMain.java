package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            mealRestController.create(new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "meal", 1500));
            mealRestController.getAll().stream().forEach(System.out::println);
            mealRestController.getAll().stream()
                    .filter(mealTo -> DateTimeUtil.isBetweenDateOrTime(mealTo.getDateTime().toLocalTime(), LocalTime.of(10, 0), LocalTime.of(20, 0), true))
                    .forEach(System.out::println);
        }
    }
}
