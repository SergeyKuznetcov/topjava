package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping
    public String getAll(Model model, @RequestParam Map<String, String> params) {
        if (params.size() == 0) {
            log.info("get all meals");
            model.addAttribute("meals", MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()),
                    SecurityUtil.authUserCaloriesPerDay()));
        } else {
            LocalDate startDate = params.containsKey("startDate") ? parseLocalDate(params.get("startDate")) : null;
            LocalDate endDate = params.containsKey("endDate") ? parseLocalDate(params.get("endDate")) : null;
            LocalTime startTime = params.containsKey("startTime") ? parseLocalTime(params.get("startTime")) : null;
            LocalTime endTime = params.containsKey("endTime") ? parseLocalTime(params.get("endTime")) : null;
            log.info("filter date between {} and {}, time between {} and {}", startDate, endDate, startTime, endTime);
            List<Meal> filteredByDate = service.getBetweenInclusive(startDate, endDate, SecurityUtil.authUserId());
            model.addAttribute("meals", MealsUtil.getFilteredTos(filteredByDate,
                    SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        }
        return "meals";
    }

    @GetMapping(value = "/delete", params = "id")
    public String delete(@RequestParam("id") String id) {
        log.info("delete id={}", id);
        service.delete(Integer.parseInt(id), SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        log.info("create");
        model.addAttribute("action", "create");
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping(value = "/update", params = "id")
    public String update(@RequestParam("id") String id, Model model) {
        log.info("update id={}", id);
        model.addAttribute("action", "update");
        model.addAttribute("meal", service.get(Integer.parseInt(id), SecurityUtil.authUserId()));
        return "mealForm";
    }

    @PostMapping
    public String save(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        if (StringUtils.hasLength(id)) {
            meal.setId(Integer.parseInt(id));
            log.info("update meal with id={}", meal.getId());
            service.update(meal, SecurityUtil.authUserId());
        } else {
            log.info("create new meal");
            service.create(meal, SecurityUtil.authUserId());
        }
        return "redirect:meals";
    }
}
