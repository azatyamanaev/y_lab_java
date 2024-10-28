package ru.ylab.web.servlets;

import java.io.IOException;
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
import ru.ylab.dto.in.UserForm;
import ru.ylab.dto.in.UserSearchForm;
import ru.ylab.dto.mappers.UserMapper;
import ru.ylab.dto.out.UserDto;
import ru.ylab.exception.HttpException;
import ru.ylab.services.entities.UserService;
import ru.ylab.utils.StringUtil;
import ru.ylab.utils.constants.ErrorConstants;
import ru.ylab.utils.constants.WebConstants;

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
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        String uri = StringUtil.parseReqUri(req.getRequestURI());
        if (method.equals("GET")) {
            switch (uri) {
                case ADMIN_URL + USERS_URL + ONE_URL:
                    getUser(req, resp);
                    break;
                case ADMIN_URL + USERS_URL:
                    getUsers(req, resp);
                    break;
                case ADMIN_URL + USERS_URL + SEARCH_URL:
                    searchUsers(req, resp);
                    break;
                default:
                    throw HttpException.methodNotAllowed()
                                       .addDetail(ErrorConstants.NOT_IMPLEMENTED, "Http GET");
            }
        } else if (method.equals("POST")) {
            if (uri.equals(ADMIN_URL + USERS_URL + ONE_URL)) {
               createUser(req, resp);
            } else {
                throw HttpException.methodNotAllowed()
                                   .addDetail(ErrorConstants.NOT_IMPLEMENTED, "Http POST");
            }
        } else if (method.equals("DELETE")) {
            if (uri.equals(ADMIN_URL + USERS_URL + ONE_URL)) {
                deleteUser(req, resp);
            } else {
                throw HttpException.methodNotAllowed()
                                   .addDetail(ErrorConstants.NOT_IMPLEMENTED, "Http DELETE");
            }
        } else {
            super.service(req, resp);
        }
    }

    /**
     * Gets user for admin and writes it to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void getUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        UserDto dto = userMapper.apply(userService.get(id));
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(dto));
    }

    /**
     * Gets users for admin and writes them to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void getUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<UserDto> dtos = userMapper.apply(userService.getAll());
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(dtos));
    }

    /**
     * Searches users for admin and writes them to response.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void searchUsers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserSearchForm form = new UserSearchForm();
        form.setName(req.getParameter("name"));
        form.setEmail(req.getParameter("email"));
        form.setRole(req.getParameter("role"));
        List<UserDto> dtos = userMapper.apply(userService.searchUsers(form));
        setResponse(resp, HttpServletResponse.SC_OK, mapper.writeValueAsString(dtos));
    }

    /**
     * Creates user for admin and sets created response status.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void createUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserForm form = mapper.readValue(req.getReader(), UserForm.class);
        userService.createByAdmin(form);
        setResponse(resp, HttpServletResponse.SC_CREATED, "");
    }

    /**
     * Deletes user for admin and sets no content response status.
     *
     * @param req Http request
     * @param resp Http response
     * @throws IOException if error occurs when writing to response
     */
    public void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        userService.delete(id);
        setResponse(resp, HttpServletResponse.SC_NO_CONTENT, "");
    }
}
