package ru.ylab.web.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
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
import ru.ylab.models.User;
import ru.ylab.services.entities.HabitService;
import ru.ylab.utils.constants.WebConstants;

import static ru.ylab.utils.StringUtil.parseReqUri;
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
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        AppContext appContext = (AppContext) context.getAttribute("appContext");

        this.mapper = appContext.getMappersConfig().getMapper();
        this.habitMapper = appContext.getMappersConfig().getHabitMapper();
        this.habitService = appContext.getServicesConfig().getHabitService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = parseReqUri(req.getRequestURI());
        String response;
        List<HabitDto> dtos;

        User current = (User) req.getAttribute("currentUser");
        switch (uri) {
            case USER_URL + HABITS_URL + ONE_URL:
                HabitDto habit = habitMapper.apply(habitService.get(Long.valueOf(req.getParameter("id"))));
                response = mapper.writeValueAsString(habit);
                break;
            case USER_URL + HABITS_URL:
                dtos = habitMapper.apply(habitService.getHabitsForUser(current.getId()));
                response = mapper.writeValueAsString(dtos);
                break;
            case USER_URL + HABITS_URL + SEARCH_URL:
                HabitSearchForm form = new HabitSearchForm();
                form.setName(req.getParameter("name"));
                form.setFrequency(req.getParameter("frequency"));
                form.setFrom(req.getParameter("from") == null ? null : LocalDate.parse(req.getParameter("from")));
                form.setTo(req.getParameter("to") == null ? null : LocalDate.parse(req.getParameter("to")));
                dtos = habitMapper.apply(habitService.searchHabitsForUser(current.getId(), form));
                response = mapper.writeValueAsString(dtos);
                break;
            default:
                response = "";
        }

        setResponse(resp, HttpServletResponse.SC_OK, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = parseReqUri(req.getRequestURI());
        if (uri.equals(USER_URL + HABITS_URL + ONE_URL)) {
            User user = (User) req.getAttribute("currentUser");
            HabitForm form = mapper.readValue(req.getReader(), HabitForm.class);
            habitService.create(user.getId(), form);
        }

        setResponse(resp, HttpServletResponse.SC_CREATED, "");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = parseReqUri(req.getRequestURI());
        if (uri.equals(USER_URL + HABITS_URL + ONE_URL)) {
            Long id = Long.valueOf(req.getParameter("id"));
            User user = (User) req.getAttribute("currentUser");
            HabitForm form = mapper.readValue(req.getReader(), HabitForm.class);
            habitService.update(user.getId(), id, form);
        }

        setResponse(resp, HttpServletResponse.SC_NO_CONTENT, "");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = parseReqUri(req.getRequestURI());
        log.info("GET {}", uri);
        if (uri.equals(USER_URL + HABITS_URL + ONE_URL)) {
            Long id = Long.valueOf(req.getParameter("id"));
            User user = (User) req.getAttribute("currentUser");
            habitService.delete(user.getId(), id);
        }

        setResponse(resp, HttpServletResponse.SC_NO_CONTENT, "");
    }
}
