package ru.ylab.web.servlets;

import jakarta.servlet.annotation.WebServlet;
import ru.ylab.utils.constants.WebConstants;

import static ru.ylab.utils.constants.WebConstants.SELF_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

/**
 * Servlet for handling users HTTP requests for user.
 *
 * @author azatyamanaev
 */
@WebServlet(name = WebConstants.USERS_SERVLET_NAME,
urlPatterns = {USER_URL + SELF_URL})
public class UsersServlet {
}
