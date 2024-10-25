package ru.ylab.repositories.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.ylab.config.datasource.CPDataSource;
import ru.ylab.models.HabitHistory;
import ru.ylab.repositories.HabitHistoryRepository;
import ru.ylab.utils.SqlConstants;

/**
 * Class implementing {@link HabitHistoryRepository}.
 *
 * @author azatyamanaev
 */
@Slf4j
public class HabitHistoryRepositoryImpl implements HabitHistoryRepository {

    /**
     * Instance of a {@link CPDataSource}.
     */
    private final CPDataSource dataSource;

    /**
     * Creates new HabitHistoryRepository.
     *
     * @param dataSource CPDataSource instance
     */
    public HabitHistoryRepositoryImpl(CPDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public HabitHistory save(HabitHistory history) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SqlConstants.INSERT_INTO_HABIT_HISTORY)) {

            statement.setLong(1, history.getUserId());
            statement.setLong(2, history.getHabitId());
            statement.setDate(3, Date.valueOf(history.getCompletedOn()));
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("Error while saving habit history {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return history;
    }

    @Override
    public HabitHistory getByHabitId(@NotNull Long habitId) {
        HabitHistory history = new HabitHistory();
        history.setHabitId(habitId);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SqlConstants.SELECT_FROM_HABIT_HISTORY_BY_HABIT_ID)) {

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
        } catch (SQLException e) {
            log.error("Error while getting habit history {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return history;
    }

    @Override
    public boolean deleteByHabitId(@NotNull Long habitId) {
        int result;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SqlConstants.DELETE_FROM_HABIT_HISTORY_BY_HABIT_ID)) {

            statement.setLong(1, habitId);
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("Error while deleting habit history {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return result > 0;
    }
}
