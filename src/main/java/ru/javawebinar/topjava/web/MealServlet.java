package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.MealsStorage;
import ru.javawebinar.topjava.model.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private static final Storage storage = MealsStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> mealToList = MealsUtil.createTo(storage.getAll(), CALORIES_PER_DAY);
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if (action == null) {
            request.setAttribute("meals", mealToList);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        switch (action) {
            case "delete":
                storage.delete(Integer.parseInt(id));
                response.sendRedirect("meals");
                break;
            case "edit":
                request.setAttribute("meal", getMealTo(mealToList, Integer.parseInt(id)));
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
                break;
            case "add":
                request.setAttribute("meal", MealsUtil.createTo(new Meal(), false));
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(request.getParameter("id"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ISO_DATE_TIME);
        String description = request.getParameter("description");
        Integer calories = Integer.parseInt(request.getParameter("calories"));
        storage.save(new Meal(id, dateTime, description, calories));
        response.sendRedirect("meals");
    }

    private MealTo getMealTo(List<MealTo> mealToList, Integer id) {
        for (MealTo mealTo :
                mealToList) {
            if (mealTo.getId().equals(id)) {
                return mealTo;
            }
        }
        return null;
    }
}
