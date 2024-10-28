package ru.ylab.web.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.aspects.LogRequest;
import ru.ylab.config.AppContext;
import ru.ylab.dto.in.HabitPercentageForm;
import ru.ylab.dto.mappers.HabitMapper;
import ru.ylab.dto.out.HabitCompletionPercent;
import ru.ylab.dto.out.HabitCompletionStreak;
import ru.ylab.dto.out.HabitHistoryProjection;
import ru.ylab.models.User;
import ru.ylab.services.entities.HabitHistoryService;
import ru.ylab.utils.constants.WebConstants;

import static ru.ylab.utils.StringUtil.parseReqUri;
import static ru.ylab.utils.constants.WebConstants.HABIT_HISTORY_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_PERCENTAGE_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_REPORT_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_STREAK_URL;
import static ru.ylab.utils.constants.WebConstants.ONE_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

/**
 * Servlet for handling habit history HTTP requests.
 *
 * @author azatyamanaev
 */
@LogRequest
@WebServlet(name = WebConstants.HABIT_HISTORY_SERVLET_NAME,
        urlPatterns = {USER_URL + HABIT_HISTORY_URL + ONE_URL,
                USER_URL + HABIT_HISTORY_URL + HABIT_STREAK_URL,
                USER_URL + HABIT_HISTORY_URL + HABIT_PERCENTAGE_URL,
                USER_URL + HABIT_HISTORY_URL + HABIT_REPORT_URL})
public class HabitHistoryServlet extends HttpServlet implements HttpRequestHandler {

    /**
     * Instance of an {@link ObjectMapper}.
     */
    private ObjectMapper mapper;

    /**
     * Instance of a {@link HabitMapper}.
     */
    private HabitMapper habitMapper;

    /**
     * Instance of a {@link HabitHistoryService}.
     */
    private HabitHistoryService habitHistoryService;

    @Override
    public void init() {
        ServletContext context = this.getServletContext();
        AppContext appContext = (AppContext) context.getAttribute("appContext");

        this.mapper = appContext.getMappersConfig().getMapper();
        this.habitMapper = appContext.getMappersConfig().getHabitMapper();
        this.habitHistoryService = appContext.getServicesConfig().getHabitHistoryService();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = parseReqUri(req.getRequestURI());
        String response;
        User user = (User) req.getAttribute("currentUser");
        switch (uri) {
            case USER_URL + HABIT_HISTORY_URL + ONE_URL:
                Long id = Long.valueOf(req.getParameter("id"));
                HabitHistoryProjection projection = habitHistoryService.getHabitHistory(user.getId(), id);
                response = mapper.writeValueAsString(projection);
                break;
            case USER_URL + HABIT_HISTORY_URL + HABIT_STREAK_URL:
                List<HabitCompletionStreak> streaks = habitHistoryService.habitCompletionStreak(user.getId());
                response = mapper.writeValueAsString(streaks);
                break;
            case USER_URL + HABIT_HISTORY_URL + HABIT_PERCENTAGE_URL:
                HabitPercentageForm form = new HabitPercentageForm();
                form.setFrom(req.getParameter("from") == null ? null : LocalDate.parse(req.getParameter("from")));
                form.setTo(req.getParameter("from") == null ? null : LocalDate.parse(req.getParameter("from")));
                List<HabitCompletionPercent> percentage = habitHistoryService.habitCompletionPercent(user.getId(), form);
                response = mapper.writeValueAsString(percentage);
                break;
            case USER_URL + HABIT_HISTORY_URL + HABIT_REPORT_URL:
                response = mapper.writeValueAsString(habitHistoryService.habitCompletionReport(user.getId()));
                break;
            default:
                response = "";
        }

        setResponse(resp, HttpServletResponse.SC_OK, response);
    }
}
