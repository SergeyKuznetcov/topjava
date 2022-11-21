package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService mealService;

    @GetMapping("/meals")
    public String getMeals(Model model) {
        log.info("meals");
        model.addAttribute("meals", MealsUtil.getTos(mealService.getAll(SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping(value = "/meals/delete", params = "id")
    public String deleteMeal(@RequestParam("id") String id) {
        int int_id = Integer.parseInt(id);
        mealService.delete(int_id, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/meals/create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal());
        return "mealForm";
    }

    @GetMapping(value = "/meals/update", params = "id")
    public String update(@RequestParam("id") String id, Model model) {
        model.addAttribute("meal", mealService.get(Integer.parseInt(id), SecurityUtil.authUserId()));
        return "mealForm";
    }

    @GetMapping(value = "/meals", params = {"startDate", "endDate", "startTime", "endTime"})
    public String getAllFiltered(Model model, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
                                 @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime){
        log.error(startDate);
        log.error(startTime);
        List<Meal> filteredByDate = mealService.getBetweenInclusive(parseLocalDate(startDate),
                parseLocalDate(endDate), SecurityUtil.authUserId());
        model.addAttribute("meals", MealsUtil.getFilteredTos(filteredByDate, SecurityUtil.authUserCaloriesPerDay(),
                parseLocalTime(startTime), parseLocalTime(endTime)));
        return "meals";
    }

    @PostMapping("/meals/*")
    public String save(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        if (StringUtils.hasLength(id)) {
            meal.setId(Integer.parseInt(id));
            mealService.update(meal, SecurityUtil.authUserId());
        } else {
            mealService.create(meal, SecurityUtil.authUserId());
        }
        return "redirect:/meals";
    }
}
