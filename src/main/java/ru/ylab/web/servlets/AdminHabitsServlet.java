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
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.dto.mappers.HabitMapper;
import ru.ylab.dto.out.HabitDto;
import ru.ylab.exception.HttpException;
import ru.ylab.services.entities.HabitService;
import ru.ylab.utils.StringUtil;
import ru.ylab.utils.constants.ErrorConstants;
import ru.ylab.utils.constants.WebConstants;

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
        urlPatterns = {ADMIN_URL + HABITS_URL + "/*"})
public class AdminHabitsServlet extends HttpServlet implements HttpRequestHandler {

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
        if (method.equals("GET")) {
            switch (uri) {
                case ADMIN_URL + HABITS_URL + ONE_URL:
                    getHabit(req, resp);
                    break;
                case ADMIN_URL + HABITS_URL:
                    getHabits(req, resp);
                    break;
                case ADMIN_URL + HABITS_URL + SEARCH_URL:
                    searchHabits(req, resp);
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
     * Gets habit for admin and writes it to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void getHabit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HabitDto habit = habitMapper.mapToDto(habitService.get(Long.valueOf(req.getParameter("id"))));
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(habit));
    }

    /**
     * Gets habits for admin and writes them to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void getHabits(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<HabitDto> habits = habitMapper.mapToDto(habitService.getAll());
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(habits));
    }

    /**
     * Searches habits for admin and writes them to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void searchHabits(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HabitSearchForm form = new HabitSearchForm();
        form.setName(req.getParameter("name"));
        form.setFrequency(req.getParameter("frequency"));
        form.setFrom(req.getParameter("from") == null ? null : LocalDate.parse(req.getParameter("from")));
        form.setTo(req.getParameter("to") == null ? null : LocalDate.parse(req.getParameter("to")));
        List<HabitDto> habits = habitMapper.mapToDto(habitService.search(form));
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(habits));
    }
}
