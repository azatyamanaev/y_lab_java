package ru.ylab.web.servlets;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.aspects.LogRequest;
import ru.ylab.config.AppContext;
import ru.ylab.dto.in.UserForm;
import ru.ylab.dto.in.UserSearchForm;
import ru.ylab.dto.mappers.UserMapper;
import ru.ylab.dto.out.UserDto;
import ru.ylab.services.entities.UserService;
import ru.ylab.utils.constants.WebConstants;

import static ru.ylab.utils.StringUtil.parseReqUri;
import static ru.ylab.utils.constants.WebConstants.ADMIN_URL;
import static ru.ylab.utils.constants.WebConstants.ONE_URL;
import static ru.ylab.utils.constants.WebConstants.SEARCH_URL;
import static ru.ylab.utils.constants.WebConstants.USERS_URL;

/**
 * Servlet for handling users HTTP requests for admin.
 *
 * @author azatyamanaev
 */
@LogRequest
@WebServlet(name = WebConstants.ADMIN_USERS_SERVLET_NAME,
        urlPatterns = {ADMIN_URL + USERS_URL,
                ADMIN_URL + USERS_URL + ONE_URL,
                ADMIN_URL + USERS_URL + SEARCH_URL})
public class AdminUsersServlet extends HttpServlet implements HttpRequestHandler {

    /**
     * Instance of an {@link ObjectMapper}.
     */
    private ObjectMapper mapper;

    /**
     * Instance of an {@link UserMapper}.
     */
    private UserMapper userMapper;

    /**
     * Instance of an {@link UserService}.
     */
    private UserService userService;

    @Override
    public void init() {
        ServletContext context = this.getServletContext();
        AppContext appContext = (AppContext) context.getAttribute("appContext");

        this.mapper = appContext.getMappersConfig().getMapper();
        this.userMapper = appContext.getMappersConfig().getUserMapper();
        this.userService = appContext.getServicesConfig().getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = parseReqUri(req.getRequestURI());
        String response;
        List<UserDto> dtos;
        switch (uri) {
            case ADMIN_URL + USERS_URL + ONE_URL:
                Long id = Long.valueOf(req.getParameter("id"));
                UserDto dto = userMapper.apply(userService.get(id));
                response = mapper.writeValueAsString(dto);
                break;
            case ADMIN_URL + USERS_URL:
                dtos = userMapper.apply(userService.getAll());
                response = mapper.writeValueAsString(dtos);
                break;
            case ADMIN_URL + USERS_URL + SEARCH_URL:
                UserSearchForm form = new UserSearchForm();
                form.setName(req.getParameter("name"));
                form.setEmail(req.getParameter("email"));
                form.setRole(req.getParameter("role"));
                dtos = userMapper.apply(userService.searchUsers(form));
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
        if (uri.equals(ADMIN_URL + USERS_URL + ONE_URL)) {
            UserForm form = mapper.readValue(req.getReader(), UserForm.class);
            userService.createByAdmin(form);
        }

        setResponse(resp, HttpServletResponse.SC_CREATED, "");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = parseReqUri(req.getRequestURI());
        if (uri.equals(ADMIN_URL + USERS_URL + ONE_URL)) {
            Long id = Long.valueOf(req.getParameter("id"));
            userService.delete(id);
        }

        setResponse(resp, HttpServletResponse.SC_NO_CONTENT, "");
    }
}
