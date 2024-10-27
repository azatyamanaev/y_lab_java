package ru.ylab.repositories.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import org.jetbrains.annotations.NotNull;
import ru.ylab.aspects.LogQuery;
import ru.ylab.dto.out.HabitHistoryProjection;
import ru.ylab.exception.HttpException;
import ru.ylab.models.HabitHistory;
import ru.ylab.repositories.HabitHistoryRepository;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.utils.constants.ErrorConstants;
import ru.ylab.utils.constants.SqlConstants;

/**
 * Class implementing {@link HabitHistoryRepository}.
 *
 * @author azatyamanaev
 */
@LogQuery
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
    public boolean save(HabitHistory history) {
        int result;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SqlConstants.INSERT_INTO_HABIT_HISTORY)) {

            statement.setLong(1, history.getUserId());
            statement.setLong(2, history.getHabitId());
            statement.setDate(3, Date.valueOf(history.getCompletedOn()));
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError().addDetail(ErrorConstants.CREATE_ERROR, "habit history");
        }
        return result == 1;
    }

    @Override
    public HabitHistoryProjection getByHabitId(@NotNull Long habitId) {
        HabitHistoryProjection projection = new HabitHistoryProjection();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SqlConstants.SELECT_FROM_HABIT_HISTORY_BY_HABIT_ID)) {

            statement.setLong(1, habitId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                projection.setHabitName(resultSet.getString("name"));
                projection.setDays(new HashSet<>());
                projection.getDays().add(resultSet.getDate("completed_on").toLocalDate());
            }
            while (resultSet.next()) {
                projection.getDays().add(resultSet.getDate("completed_on").toLocalDate());
            }

            resultSet.close();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError().addDetail(ErrorConstants.SELECT_ERROR, "habit history");
        }

        return projection;
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
            throw HttpException.databaseAccessError().addDetail(ErrorConstants.DELETE_ERROR, "habit history");
        }
        return result > 0;
    }
}
