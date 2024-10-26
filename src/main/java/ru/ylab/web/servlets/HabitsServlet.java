package ru.ylab.web.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.ylab.config.AppContext;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.dto.mappers.HabitMapper;
import ru.ylab.dto.out.HabitDto;
import ru.ylab.models.User;
import ru.ylab.services.entities.HabitService;
import ru.ylab.utils.constants.WebConstants;

import static ru.ylab.utils.constants.WebConstants.HABITS_SEARCH_URL;
import static ru.ylab.utils.constants.WebConstants.HABITS_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

/**
 * Servlet for handling habits HTTP requests.
 *
 * @author azatyamanaev
 */
@Slf4j
@WebServlet(name = WebConstants.HABITS_SERVLET_NAME,
        urlPatterns = {USER_URL + HABITS_URL,
                USER_URL + HABITS_URL + HABITS_SEARCH_URL})
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
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        AppContext appContext = (AppContext) context.getAttribute("appContext");

        this.mapper = appContext.getMapper();
        this.habitMapper = appContext.getHabitMapper();
        this.habitService = appContext.getServicesConfig().getHabitService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = parseReqUri(req);
        List<HabitDto> habits = new ArrayList<>();
        log.info("GET {}", uri);

        User current = (User) req.getAttribute("currentUser");
        switch (uri) {
            case USER_URL + HABITS_URL:
                habits.addAll(habitMapper.apply(habitService.getHabitsForUser(current.getId())));
                break;
            case USER_URL + HABITS_URL + HABITS_SEARCH_URL:
                HabitSearchForm form = new HabitSearchForm();
                form.setName(req.getParameter("name"));
                form.setFrequency(req.getParameter("frequency"));
                form.setFrom(req.getParameter("from") == null ? null : LocalDate.parse(req.getParameter("from")));
                form.setTo(req.getParameter("to") == null ? null : LocalDate.parse(req.getParameter("to")));
                habits.addAll(habitMapper.apply(habitService.searchHabitsForUser(current.getId(), form)));
                break;
        }

        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(habits));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = parseReqUri(req);

        setResponse(resp, HttpServletResponse.SC_CREATED, "OK");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = parseReqUri(req);

        setResponse(resp, HttpServletResponse.SC_OK, "OK");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = parseReqUri(req);
        log.info("GET {}", uri);

        setResponse(resp, HttpServletResponse.SC_OK, "OK");
    }
}
