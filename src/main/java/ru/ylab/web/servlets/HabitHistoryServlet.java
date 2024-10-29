package ru.ylab.web.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.aspects.LogRequest;
import ru.ylab.config.AppContext;
import ru.ylab.dto.in.PeriodForm;
import ru.ylab.dto.out.HabitCompletionPercent;
import ru.ylab.dto.out.HabitCompletionStreak;
import ru.ylab.dto.out.HabitHistoryProjection;
import ru.ylab.exception.HttpException;
import ru.ylab.models.User;
import ru.ylab.services.entities.HabitHistoryService;
import ru.ylab.utils.StringUtil;
import ru.ylab.utils.constants.ErrorConstants;
import ru.ylab.utils.constants.WebConstants;

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
        urlPatterns = {USER_URL + HABIT_HISTORY_URL + "/*"})
public class HabitHistoryServlet extends HttpServlet implements HttpRequestHandler {

    /**
     * Instance of an {@link ObjectMapper}.
     */
    private ObjectMapper mapper;

    /**
     * Instance of a {@link HabitHistoryService}.
     */
    private HabitHistoryService habitHistoryService;

    @Override
    public void init() {
        ServletContext context = this.getServletContext();
        AppContext appContext = (AppContext) context.getAttribute("appContext");

        this.mapper = appContext.getMappersConfig().getMapper();
        this.habitHistoryService = appContext.getServicesConfig().getHabitHistoryService();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        String uri = StringUtil.parseReqUri(req.getRequestURI());
        User user = (User) req.getAttribute("currentUser");
        if (method.equals("GET")) {
            switch (uri) {
                case USER_URL + HABIT_HISTORY_URL + ONE_URL:
                    getHabitHistory(req, resp, user);
                    break;
                case USER_URL + HABIT_HISTORY_URL + HABIT_STREAK_URL:
                    getHabitStreak(req, resp, user);
                    break;
                case USER_URL + HABIT_HISTORY_URL + HABIT_PERCENTAGE_URL:
                    getHabitPercentage(req, resp, user);
                    break;
                case USER_URL + HABIT_HISTORY_URL + HABIT_REPORT_URL:
                    getHabitReport(req, resp, user);
                    break;
                default:
                    throw HttpException.methodNotAllowed()
                                       .addDetail(ErrorConstants.NOT_IMPLEMENTED, "Http GET");
            }
        } else {
            super.service(req, resp);
        }
    }

    /**
     * Gets habit history for user and writes it to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void getHabitHistory(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        HabitHistoryProjection projection = habitHistoryService.getHabitHistory(user.getId(), id);
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(projection));
    }

    /**
     * Gets habits completion streaks for user and writes them to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void getHabitStreak(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        List<HabitCompletionStreak> streaks = habitHistoryService.habitCompletionStreak(user.getId());
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(streaks));
    }

    /**
     * Gets habits completion percentage for user and writes it to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void getHabitPercentage(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        PeriodForm form = new PeriodForm();
        form.setFrom(req.getParameter("from") == null ? null : LocalDate.parse(req.getParameter("from")));
        form.setTo(req.getParameter("from") == null ? null : LocalDate.parse(req.getParameter("from")));
        List<HabitCompletionPercent> percentage = habitHistoryService.habitCompletionPercent(user.getId(), form);
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(percentage));
    }

    /**
     * Gets habits completion report for user and writes it to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void getHabitReport(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        setResponse(resp, HttpServletResponse.SC_OK,
                mapper.writeValueAsString(habitHistoryService.habitCompletionReport(user.getId())));
    }
}
