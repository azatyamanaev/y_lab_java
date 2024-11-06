package ru.ylab.utils.constants;

import org.intellij.lang.annotations.Language;

/**
 * Class containing sql request constants.
 *
 * @author azatyamanaev
 */
public interface SqlConstants {

    //Habit requests

    /**
     * Sql request for selecting habit by id.
     */
    @Language("SQL")
    String SELECT_FROM_HABITS_BY_ID = "SELECT * FROM entity.habits WHERE id = ?";

    /**
     * Sql request for selecting habit by name.
     */
    @Language("SQL")
    String SELECT_FROM_HABITS_BY_NAME = "SELECT * FROM entity.habits WHERE name = ?";

    /**
     * Sql request for selecting all habits.
     */
    @Language("SQL")
    String SELECT_ALL_FROM_HABITS = "SELECT * FROM entity.habits";

    /**
     * Sql request for searching habits.
     */
    @Language("SQL")
    String SEARCH_HABITS = "SELECT * FROM entity.habits WHERE (name LIKE ? or coalesce(?, '') = '') " +
            "and (frequency = ? or coalesce(?, '') = '') and created >= ? and created <= ?";

    /**
     * Sql request for selecting habits, created by user.
     */
    @Language("SQL")
    String SELECT_ALL_HABITS_FOR_USER = "SELECT * FROM entity.habits WHERE user_id = ?";

    /**
     * Sql request for searching habits, created by user.
     */
    @Language("SQL")
    String SEARCH_HABITS_FOR_USER = "SELECT * FROM entity.habits WHERE user_id = ? and (name LIKE ? or coalesce(?, '') = '') " +
            "and (frequency = ? or coalesce(?, '') = '') and created >= ? and created <= ?";

    /**
     * Sql request for saving habit.
     */
    @Language("SQL")
    String INSERT_INTO_HABITS = "INSERT INTO entity.habits(name, description, frequency, " +
            "created, user_id) VALUES (?, ?, ?, ?, ?)";

    /**
     * Sql request for updating habit.
     */
    @Language("SQL")
    String UPDATE_HABITS = "UPDATE entity.habits set name = ?, description = ?, frequency = ?, " +
            "created = ? WHERE user_id = ? and id = ?";

    /**
     * Sql request for deleting habit.
     */
    @Language("SQL")
    String DELETE_FROM_HABITS = "DELETE FROM entity.habits WHERE user_id = ? and id = ?";

    //HabitHistory requests

    /**
     * Sql request for marking habit as completed.
     */
    @Language("SQL")
    String INSERT_INTO_HABIT_HISTORY = "INSERT INTO entity.habit_history(user_id, habit_id, completed_on) VALUES (?, ?, ?)";

    /**
     * Sql request for getting habit history.
     */
    @Language("SQL")
    String SELECT_FROM_HABIT_HISTORY_BY_HABIT_ID = "SELECT hb.name as name, hh.completed_on as completed_on " +
            "FROM entity.habits as hb join entity.habit_history as hh on hb.id = hh.habit_id WHERE hb.id = ?";

    /**
     * Sql request for deleting habit history.
     */
    @Language("SQL")
    String DELETE_FROM_HABIT_HISTORY_BY_HABIT_ID = "DELETE FROM entity.habit_history WHERE habit_id = ?";

    //User requests

    /**
     * Sql request for selecting user by id.
     */
    @Language("SQL")
    String SELECT_FROM_USERS_BY_ID = "SELECT * FROM entity.users WHERE id = ?";

    /**
     * Sql request for selecting user by email.
     */
    @Language("SQL")
    String SELECT_FROM_USERS_BY_EMAIL = "SELECT * FROM entity.users WHERE email = ?";

    /**
     * Sql request for selecting all users.
     */
    @Language("SQL")
    String SELECT_ALL_FROM_USERS = "SELECT * FROM entity.users";

    /**
     * Sql request for searching users.
     */
    @Language("SQL")
    String SEARCH_USERS = "SELECT * FROM entity.users WHERE (name LIKE ? or coalesce(?, '') = '') and " +
            "(email LIKE ? or coalesce(?, '') = '') and (role = ? or coalesce(?, '') = '')";

    /**
     * Sql request for saving user.
     */
    @Language("SQL")
    String INSERT_INTO_USERS = "INSERT INTO entity.users(name, email, password, role) VALUES (?, ?, ?, ?)";

    /**
     * Sql request for updating user.
     */
    @Language("SQL")
    String UPDATE_USERS = "UPDATE entity.users SET name = ?, email = ?, password = ? WHERE id = ?";

    /**
     * Sql request for deleting user by email.
     */
    @Language("SQL")
    String DELETE_FROM_USERS_BY_ID = "DELETE FROM entity.users WHERE id = ?";

    //RefreshToken requests

    /**
     * Sql request for selecting refresh token by token.
     */
    @Language("SQL")
    String SELECT_FROM_REFRESH_TOKENS_BY_TOKEN = "SELECT * FROM security.refresh_tokens WHERE token = ?";

    /**
     * Sql request for saving refresh token.
     */
    @Language("SQL")
    String INSERT_INTO_REFRESH_TOKENS = "INSERT INTO security.refresh_tokens(token, user_id, created, expires) VALUES (?, ?, ?, ?)";
}
