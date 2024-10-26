package ru.ylab.utils.constants;

public interface WebConstants {

    String APP_CONTEXT_PATH = "habits-app";

    // auth patterns

    String AUTH_SERVLET_NAME = "authServlet";
    String AUTH_URL = "/auth";
    String SIGN_IN_URL = "/signIn";
    String SIGN_UP_URL = "/signUp";
    String REFRESH_TOKEN_URL = "/refresh-token";

    // habits patterns

    String HABITS_SERVLET_NAME = "habitsServlet";
    String HABITS_URL = "/habits";
    String HABITS_SEARCH_URL = "/search";


    // habit history patterns

    String HABIT_HISTORY_SERVLET_NAME = "habitHistoryServlet";
    String HABIT_HISTORY_URL = HABITS_URL + "/history";
    String HABIT_HISTORY_COMPLETION_URL = "/completion";
    String HABIT_HISTORY_STREAK_URL = HABIT_HISTORY_COMPLETION_URL + "/streak";
    String HABIT_HISTORY_PERCENT_URL = HABIT_HISTORY_COMPLETION_URL + "/percent";
    String HABIT_HISTORY_REPORT_URL = HABIT_HISTORY_COMPLETION_URL + "/report";

    // user patterns

    String USERS_SERVLET_NAME = "usersServlet";
    String USER_URL = "/user";
    String SELF_URL = "/self";
    String USERS_URL = "/users";
    String USERS_SEARCH_URL = "/search";

    // admin patterns

    String ADMIN_USERS_SERVLET_NAME = "adminUsersServlet";
    String ADMIN_URL = "/admin";

}
