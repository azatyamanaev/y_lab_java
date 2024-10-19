package ru.ylab.repositories.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jetbrains.annotations.NotNull;
import ru.ylab.models.HabitHistory;
import ru.ylab.repositories.HabitHistoryRepository;
import ru.ylab.services.datasource.ConnectionPool;

/**
 * Class implementing {@link HabitHistoryRepository}.
 *
 * @author azatyamanaev
 */
public class HabitHistoryRepositoryImpl implements HabitHistoryRepository {

    /**
     * Instance of a {@link ConnectionPool}.
     */
    private final ConnectionPool connectionPool;

    /**
     * Creates new HabitHistoryRepository.
     *
     * @param connectionPool ConnectionPool instance
     */
    public HabitHistoryRepositoryImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public HabitHistory save(HabitHistory history) {
        try {
            Connection connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO entity.habit_history(user_id, habit_id, completed_on)");
            statement.setLong(1, history.getUserId());
            statement.setLong(2, history.getHabitId());
            statement.setDate(3, Date.valueOf(history.getCompletedOn()));
            statement.executeUpdate();
            connection.commit();

            statement.close();
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return history;
    }

    @Override
    public HabitHistory getByHabitId(@NotNull Long habitId) {
        HabitHistory history = new HabitHistory();
        history.setHabitId(habitId);
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM entity.habit_history WHERE habit_id = ?");
            statement.setLong(1, habitId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                history.setUserId(resultSet.getLong("user_id"));
                history.getDays().add(resultSet.getDate("completed_on").toLocalDate());
            }
            while (resultSet.next()) {
                history.getDays().add(resultSet.getDate("completed_on").toLocalDate());
            }

            resultSet.close();
            statement.close();
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return history;
    }

    @Override
    public boolean deleteByHabitId(@NotNull Long habitId) {
        int result;
        try {
            Connection connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM entity.habit_history WHERE habit_id = ?");
            statement.setLong(1, habitId);
            result = statement.executeUpdate();
            connection.commit();

            statement.close();
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result > 0;
    }
}
