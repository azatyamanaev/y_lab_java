package ru.ylab.web.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.aspects.LogRequest;
import ru.ylab.config.AppContext;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.dto.mappers.HabitMapper;
import ru.ylab.dto.out.HabitDto;
import ru.ylab.services.entities.HabitService;
import ru.ylab.utils.constants.WebConstants;

import static ru.ylab.utils.StringUtil.parseReqUri;
import static ru.ylab.utils.constants.WebConstants.ADMIN_URL;
import static ru.ylab.utils.constants.WebConstants.HABITS_URL;
import static ru.ylab.utils.constants.WebConstants.ONE_URL;
import static ru.ylab.utils.constants.WebConstants.SEARCH_URL;

/**
 * Servlet for handling habits HTTP requests for admin.
 *
 * @author azatyamanaev
 */
@LogRequest
@WebServlet(name = WebConstants.ADMIN_HABITS_SERVLET_NAME,
        urlPatterns = {ADMIN_URL + HABITS_URL,
                ADMIN_URL + HABITS_URL + ONE_URL,
                ADMIN_URL + HABITS_URL + SEARCH_URL})
public class AdminHabitsServlet extends HttpServlet implements HttpRequestHandler{

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = parseReqUri(req.getRequestURI());
        String response;
        List<HabitDto> habits = new ArrayList<>();

        switch (uri) {
            case ADMIN_URL + HABITS_URL + ONE_URL:
                HabitDto habit = habitMapper.apply(habitService.get(Long.valueOf(req.getParameter("id"))));
                response = mapper.writeValueAsString(habit);
                break;
            case ADMIN_URL + HABITS_URL:
                habits.addAll(habitMapper.apply(habitService.getAll()));
                response = mapper.writeValueAsString(habits);
                break;
            case ADMIN_URL + HABITS_URL + SEARCH_URL:
                HabitSearchForm form = new HabitSearchForm();
                form.setName(req.getParameter("name"));
                form.setFrequency(req.getParameter("frequency"));
                form.setFrom(req.getParameter("from") == null ? null : LocalDate.parse(req.getParameter("from")));
                form.setTo(req.getParameter("to") == null ? null : LocalDate.parse(req.getParameter("to")));
                habits.addAll(habitMapper.apply(habitService.search(form)));
                response = mapper.writeValueAsString(habits);
                break;
            default:
                response = "";
        }

        setResponse(resp, HttpServletResponse.SC_OK, response);
    }
}
