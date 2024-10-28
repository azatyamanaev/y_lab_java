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
import lombok.extern.slf4j.Slf4j;
import ru.ylab.aspects.LogRequest;
import ru.ylab.config.AppContext;
import ru.ylab.dto.in.HabitForm;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.dto.mappers.HabitMapper;
import ru.ylab.dto.out.HabitDto;
import ru.ylab.exception.HttpException;
import ru.ylab.models.User;
import ru.ylab.services.entities.HabitService;
import ru.ylab.utils.StringUtil;
import ru.ylab.utils.constants.ErrorConstants;
import ru.ylab.utils.constants.WebConstants;

import static ru.ylab.utils.constants.WebConstants.HABITS_URL;
import static ru.ylab.utils.constants.WebConstants.ONE_URL;
import static ru.ylab.utils.constants.WebConstants.SEARCH_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

/**
 * Servlet for handling habits HTTP requests.
 *
 * @author azatyamanaev
 */
@LogRequest
@Slf4j
@WebServlet(name = WebConstants.HABITS_SERVLET_NAME,
        urlPatterns = {USER_URL + HABITS_URL,
                USER_URL + HABITS_URL + SEARCH_URL,
                USER_URL + HABITS_URL + ONE_URL,})
public class HabitsServlet extends HttpServlet implements HttpRequestHandler {

    /**
     * Instance of an {@link ObjectMapper}.
     */
    private ObjectMapper mapper;

    /**
     * Instance of a {@link HabitMapper}.
     */
    private HabitMapper habitMapper;

    /**
     * Instance of a {@link HabitService}.
     */
    private HabitService habitService;

    @Override
    public void init() {
        ServletContext context = this.getServletContext();
        AppContext appContext = (AppContext) context.getAttribute("appContext");

        this.mapper = appContext.getMappersConfig().getMapper();
        this.habitMapper = appContext.getMappersConfig().getHabitMapper();
        this.habitService = appContext.getServicesConfig().getHabitService();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        String uri = StringUtil.parseReqUri(req.getRequestURI());
        User user = (User) req.getAttribute("currentUser");
        if (method.equals("GET")) {
            switch (uri) {
                case USER_URL + HABITS_URL + ONE_URL:
                    getHabit(req, resp, user);
                    break;
                case USER_URL + HABITS_URL:
                    getHabits(req, resp, user);
                    break;
                case USER_URL + HABITS_URL + SEARCH_URL:
                    searchHabits(req, resp, user);
                    break;
                default:
                    throw HttpException.methodNotAllowed()
                                       .addDetail(ErrorConstants.NOT_IMPLEMENTED, "Http GET");
            }
        } else if (method.equals("POST")) {
            if (uri.equals(USER_URL + HABITS_URL + ONE_URL)) {
                createHabit(req, resp, user);
            } else {
                throw HttpException.methodNotAllowed()
                                   .addDetail(ErrorConstants.NOT_IMPLEMENTED, "Http POST");
            }
        } else if (method.equals("PUT")) {
            if (uri.equals(USER_URL + HABITS_URL + ONE_URL)) {
                updateHabit(req, resp, user);
            } else {
                throw HttpException.methodNotAllowed()
                                   .addDetail(ErrorConstants.NOT_IMPLEMENTED, "Http PUT");
            }

        } else if (method.equals("DELETE")) {
            if (uri.equals(USER_URL + HABITS_URL + ONE_URL)) {
                deleteHabit(req, resp, user);
            } else {
                throw HttpException.methodNotAllowed()
                                   .addDetail(ErrorConstants.NOT_IMPLEMENTED, "Http DELETE");
            }

        } else {
            super.service(req, resp);
        }
    }

    /**
     * Gets habit for user and writes it to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void getHabit(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        HabitDto habit = habitMapper.apply(habitService.getForUser(user.getId(), id));
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(habit));
    }

    /**
     * Gets habits for user and writes them to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void getHabits(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        List<HabitDto> dtos = habitMapper.apply(habitService.getHabitsForUser(user.getId()));
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(dtos));
    }

    /**
     * Searches habits for user and writes them to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void searchHabits(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        HabitSearchForm form = new HabitSearchForm();
        form.setName(req.getParameter("name"));
        form.setFrequency(req.getParameter("frequency"));
        form.setFrom(req.getParameter("from") == null ? null : LocalDate.parse(req.getParameter("from")));
        form.setTo(req.getParameter("to") == null ? null : LocalDate.parse(req.getParameter("to")));
        List<HabitDto> dtos = habitMapper.apply(habitService.searchHabitsForUser(user.getId(), form));
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(dtos));
    }

    /**
     * Creates habit for user and sets created response status.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void createHabit(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        HabitForm form = mapper.readValue(req.getReader(), HabitForm.class);
        habitService.create(user.getId(), form);
        setResponse(resp, HttpServletResponse.SC_CREATED, "");
    }

    /**
     * Updates habit for user and sets no content response status.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void updateHabit(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        HabitForm form = mapper.readValue(req.getReader(), HabitForm.class);
        habitService.update(user.getId(), id, form);
        setResponse(resp, HttpServletResponse.SC_NO_CONTENT, "");
    }

    /**
     * Deletes habit for user and sets no content response status.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void deleteHabit(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        habitService.delete(user.getId(), id);
        setResponse(resp, HttpServletResponse.SC_NO_CONTENT, "");
    }
}
