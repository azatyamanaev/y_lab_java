package ru.ylab.core.utils.constants;

/**
 * Class containing web constants
 *
 * @author azatyamanaev
 */
public interface WebConstants {

    String JWTOKEN_USER_ACCESS = "user_access_token";
    String JWTOKEN_ADMIN_ACCESS = "admin_access_token";
    String JWTOKEN_PREFIX = "Bearer ";
    String APP_CONTEXT_PATH = "habits-app";

    String ID_URL = "/{id}";
    String ONE_URL = "/one";
    String SEARCH_URL = "/search";

    // auth patterns

    String AUTH_SERVLET_NAME = "authServlet";
    String AUTH_URL = "/auth";
    String SIGN_IN_URL = "/signIn";
    String SIGN_UP_URL = "/signUp";
    String REFRESH_TOKEN_URL = "/refresh-token";

    // habits patterns

    String HABITS_SERVLET_NAME = "habitsServlet";
    String HABITS_URL = "/habits";

    // habit history patterns

    String HABIT_HISTORY_SERVLET_NAME = "habitHistoryServlet";
    String HABIT_HISTORY_URL = "/habit-history";
    String HABIT_HISTORY_COMPLETION_URL = "/completion";
    String HABIT_STREAK_URL = HABIT_HISTORY_COMPLETION_URL + "/streak";
    String HABIT_PERCENTAGE_URL = HABIT_HISTORY_COMPLETION_URL + "/percent";
    String HABIT_REPORT_URL = HABIT_HISTORY_COMPLETION_URL + "/report";

    // user patterns

    String USERS_SERVLET_NAME = "usersServlet";
    String USER_URL = "/user";
    String SELF_URL = "/self";
    String USERS_URL = "/users";
    String USER_ACTIONS_URL = "/actions";

    // admin patterns

    String ADMIN_USERS_SERVLET_NAME = "adminUsersServlet";
    String ADMIN_HABITS_SERVLET_NAME = "adminHabitsServlet";
    String ADMIN_URL = "/admin";
}
