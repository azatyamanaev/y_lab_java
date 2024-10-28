package ru.ylab.web.servlets;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.aspects.LogRequest;
import ru.ylab.config.AppContext;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.mappers.UserMapper;
import ru.ylab.dto.out.UserDto;
import ru.ylab.models.User;
import ru.ylab.services.entities.UserService;
import ru.ylab.utils.constants.WebConstants;

import static ru.ylab.utils.constants.WebConstants.SELF_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

/**
 * Servlet for handling users HTTP requests for user.
 *
 * @author azatyamanaev
 */
@LogRequest
@WebServlet(name = WebConstants.USERS_SERVLET_NAME,
        urlPatterns = {USER_URL + SELF_URL})
public class UsersServlet extends HttpServlet implements HttpRequestHandler {

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
        String response = "";
        User user = (User) req.getAttribute("currentUser");
        UserDto dto = userMapper.apply(userService.get(user.getId()));
        response = mapper.writeValueAsString(dto);

        setResponse(resp, HttpServletResponse.SC_OK, response);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getAttribute("currentUser");
        SignUpForm form = mapper.readValue(req.getReader(), SignUpForm.class);
        userService.update(user.getId(), form);

        setResponse(resp, HttpServletResponse.SC_NO_CONTENT, "");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getAttribute("currentUser");
        userService.delete(user.getId());

        setResponse(resp, HttpServletResponse.SC_NO_CONTENT, "");
    }
}
