package ru.ylab.web.servlets;

import jakarta.servlet.annotation.WebServlet;
import ru.ylab.utils.constants.WebConstants;

import static ru.ylab.utils.constants.WebConstants.ADMIN_URL;
import static ru.ylab.utils.constants.WebConstants.USERS_SEARCH_URL;
import static ru.ylab.utils.constants.WebConstants.USERS_URL;

/**
 * Servlet for handling users HTTP requests for admin.
 *
 * @author azatyamanaev
 */
@WebServlet(name = WebConstants.ADMIN_USERS_SERVLET_NAME,
        urlPatterns = {ADMIN_URL + USERS_URL, ADMIN_URL + USERS_URL + USERS_SEARCH_URL})
public class AdminUsersServlet {
}
