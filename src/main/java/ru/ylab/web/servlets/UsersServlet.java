package ru.ylab.web.servlets;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.aspects.LogRequest;
import ru.ylab.config.AppContext;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.mappers.UserMapper;
import ru.ylab.dto.out.UserDto;
import ru.ylab.exception.HttpException;
import ru.ylab.models.User;
import ru.ylab.services.entities.UserService;
import ru.ylab.utils.StringUtil;
import ru.ylab.utils.constants.ErrorConstants;
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
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        String uri = StringUtil.parseReqUri(req.getRequestURI());
        User user = (User) req.getAttribute("currentUser");
        if (method.equals("GET")) {
            if (uri.equals(USER_URL + SELF_URL)) {
                getProfile(req, resp, user);
            } else {
                throw HttpException.methodNotAllowed()
                                   .addDetail(ErrorConstants.NOT_IMPLEMENTED, "Http GET");
            }
        } else if (method.equals("PUT")) {
            if (uri.equals(USER_URL + SELF_URL)) {
                updateProfile(req, resp, user);
            } else {
                throw HttpException.methodNotAllowed()
                                   .addDetail(ErrorConstants.NOT_IMPLEMENTED, "Http PUT");
            }
        } else if (method.equals("DELETE")) {
            if (uri.equals(USER_URL + SELF_URL)) {
                deleteProfile(req, resp, user);
            } else {
                throw HttpException.methodNotAllowed()
                                   .addDetail(ErrorConstants.NOT_IMPLEMENTED, "Http DELETE");
            }
        } else {
            super.service(req, resp);
        }
    }

    /**
     * Gets user data for user and writes it to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void getProfile(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        UserDto dto = userMapper.apply(userService.get(user.getId()));
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(dto));
    }

    /**
     * Updates user profile for user and sets no content response status.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void updateProfile(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        SignUpForm form = mapper.readValue(req.getReader(), SignUpForm.class);
        userService.update(user.getId(), form);
        setResponse(resp, HttpServletResponse.SC_NO_CONTENT, "");
    }

    /**
     * Deletes user profile for user and sets no content response status.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void deleteProfile(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        userService.delete(user.getId());
        setResponse(resp, HttpServletResponse.SC_NO_CONTENT, "");
    }
}
