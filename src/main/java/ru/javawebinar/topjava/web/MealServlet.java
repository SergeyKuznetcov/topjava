package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MemoryMealsStorage;
import ru.javawebinar.topjava.dao.Storage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MemoryMealsStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "" : action) {
            case "delete": {
                int id = getIdAndParse(request);
                storage.delete(id);
                log.debug("delete meal where id = {} and redirect to meals", id);
                response.sendRedirect("meals");
                break;
            }
            case "edit": {
                int id = getIdAndParse(request);
                request.setAttribute("meal", storage.get(id));
                log.debug("redirect to meals?id={}&action=edit", id);
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
                break;
            }
            case "add":
                request.setAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)));
                log.debug("redirect to meals?action=add");
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
                break;
            default:
                request.setAttribute("meals", MealsUtil.createTos(storage.getAll(), CALORIES_PER_DAY));
                request.setAttribute("formatter", DATE_TIME_FORMATTER);
                log.debug("redirect to meals");
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (id.isEmpty()) {
            storage.create(new Meal(dateTime, description, calories));
            log.debug("create new meal and redirect to meals");
        } else {
            storage.update(new Meal(Integer.parseInt(id), dateTime, description, calories));
            log.debug("update meal where id = {} and redirect to meals", id);
        }
        response.sendRedirect("meals");
    }

    private int getIdAndParse(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }
}
