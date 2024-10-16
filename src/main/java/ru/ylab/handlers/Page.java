package ru.ylab.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum for all pages in the application.
 *
 * @author azatyamanaev
 */
@Getter
@AllArgsConstructor
public enum Page {
    /**
     * Page for all unauthorized users.
     */
    AUTH_PAGE("""
            1.Sign in\s
            2.Sign up\s
            3.Exit"""),

    /**
     * Page for authorized users with role {@link ru.ylab.models.User.Role#USER}.
     */
    AUTHORIZED_USER_PAGE("""
            1.Profile\s
            2.Habits\s
            3.Logout\s
            4.Exit"""),

    /**
     * Page for managing user account.
     */
    USER_PROFILE_PAGE("""
            1.Edit profile\s
            2.Delete account\s
            3.Back to main\s
            4.Logout\s
            5.Exit"""),

    /**
     * Page with habit options.
     */
    HABITS_PAGE("""
            1.Manage habits\s
            2.Habit history\s
            3.Statistics\s
            4.Back to main\s
            5.Logout\s
            6.Exit"""),

    /**
     * Page for viewing and editing user's habits.
     */
    MANAGE_HABITS_PAGE("""
            1.Habit list\s
            2.Create habit\s
            3.Edit habit \s
            4.Delete habit\s
            5.Back to main\s
            6.Logout\s
            7.Exit"""),

    /**
     * Page for completing habits and seeing habit history.
     */
    HABIT_HISTORY_PAGE("""
            1.Habit list\s
            2.Mark habit completed\s
            3.View habit history\s
            4.Back to main\s
            5.Logout\s
            6.Exit"""),

    /**
     * Page for viewing user's habit statistics.
     */
    HABIT_STATISTICS_PAGE("""
            1.Habit completion streak\s
            2.Habit completion percent\s
            3.Habit completion report\s
            4.Back to main\s
            5.Logout\s
            6.Exit"""),

    /**
     * Page for authorized users with role {@link ru.ylab.models.User.Role#ADMIN}.
     */
    ADMIN_PANEL_PAGE("""
            1.Users\s
            2.Habit List\s
            3.Logout\s
            4.Exit"""),

    /**
     * Page for managing users.
     */
    USERS_PAGE("""
            1.User list\s
            2.Create user\s
            3.Delete user\s
            4.Back to main\s
            5.Logout\s
            6.Exit""");

    /**
     * Possible user actions on page.
     */
    private final String options;
}
