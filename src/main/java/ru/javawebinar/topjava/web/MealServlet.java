package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.dao.MealsMapStorage;
import ru.javawebinar.topjava.dao.Storage;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.filterStrategy.NoFiltering;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MealsMapStorage(true);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "" : action) {
            case "delete" -> {
                String id = request.getParameter("id");
                storage.delete(Integer.parseInt(id));
                log.debug("delete meal where id = " + id + " and redirect to meals");
                response.sendRedirect("meals");
            }
            case "edit" -> {
                String id = request.getParameter("id");
                request.setAttribute("meal", storage.get(Integer.parseInt(id)));
                log.debug("redirect to meals?id=" + id + "&action=edit");
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
            }
            case "add" -> {
                request.setAttribute("meal", new Meal(LocalDateTime.now().withSecond(0).withNano(0)));
                log.debug("redirect to meals?action=add");
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
            }
            default -> {
                request.setAttribute("meals", MealsUtil.createTos(storage.getAll(), CALORIES_PER_DAY, new NoFiltering()));
                log.debug("redirect to meals");
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (id.equals("")) {
            storage.create(new Meal(dateTime, description, calories));
            log.debug("create new meal and redirect to meals");
        } else {
            storage.update(new Meal(Integer.parseInt(id), dateTime, description, calories));
            log.debug("update meal where id = " + id + " and redirect to meals");
        }
        response.sendRedirect("meals");
    }
}
