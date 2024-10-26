package ru.ylab.web.servlets;

import jakarta.servlet.annotation.WebServlet;
import ru.ylab.utils.constants.WebConstants;

import static ru.ylab.utils.constants.WebConstants.HABIT_HISTORY_PERCENT_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_HISTORY_REPORT_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_HISTORY_STREAK_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_HISTORY_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

/**
 * Servlet for handling habit history HTTP requests.
 *
 * @author azatyamanaev
 */
@WebServlet(name = WebConstants.HABIT_HISTORY_SERVLET_NAME,
        urlPatterns = {USER_URL + HABIT_HISTORY_URL,
                USER_URL + HABIT_HISTORY_URL + HABIT_HISTORY_STREAK_URL,
                USER_URL + HABIT_HISTORY_URL + HABIT_HISTORY_PERCENT_URL,
                USER_URL + HABIT_HISTORY_URL + HABIT_HISTORY_REPORT_URL})
public class HabitHistoryServlet {
}
