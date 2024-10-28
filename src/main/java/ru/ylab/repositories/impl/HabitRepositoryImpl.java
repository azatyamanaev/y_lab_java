package ru.ylab.repositories.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import ru.ylab.aspects.LogQuery;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.exception.HttpException;
import ru.ylab.models.Habit;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.utils.StringUtil;
import ru.ylab.utils.constants.ErrorConstants;
import ru.ylab.utils.constants.SqlConstants;

/**
 * Class implementing {@link HabitRepository}.
 *
 * @author azatyamanaev
 */
@LogQuery
public class HabitRepositoryImpl implements HabitRepository {

    /**
     * Instance of a {@link CPDataSource}.
     */
    private final CPDataSource dataSource;

    /**
     * Creates new HabitRepositoryImpl.
     *
     * @param dataSource CPDataSource instance
     */
    public HabitRepositoryImpl(CPDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Habit> find(Long id) {
        Optional<Habit> habit = Optional.empty();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.SELECT_FROM_HABITS_BY_ID)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                habit = Optional.of(unwrap(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.SELECT_ERROR, "habit");
        }
        return habit;
    }

    @Override
    public List<Habit> getAll() {
        List<Habit> habits = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SqlConstants.SELECT_ALL_FROM_HABITS)) {

            while (resultSet.next()) {
                habits.add(unwrap(resultSet));
            }
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.SELECT_ERROR, "habit");
        }
        return habits;
    }

    @Override
    public List<Habit> search(@NotNull HabitSearchForm form) {
        List<Habit> habits = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.SEARCH_HABITS)) {

            String name = !StringUtil.isEmpty(form.getName()) ? "%" + form.getName() + "%" : null;
            statement.setString(1, name);
            statement.setString(2, name);

            String frequency = form.getFrequency() != null ? form.getFrequency() : null;
            statement.setString(3, frequency);
            statement.setString(4, frequency);

            statement.setDate(5, form.getFrom() != null ? Date.valueOf(form.getFrom()) : new Date(0L));
            statement.setDate(6, form.getTo() != null ? Date.valueOf(form.getTo()) : new Date(System.currentTimeMillis()));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                habits.add(unwrap(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.SELECT_ERROR, "habit");
        }
        return habits;
    }

    @Override
    public List<Habit> getAllForUser(@NotNull Long userId) {
        List<Habit> habits = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.SELECT_ALL_HABITS_FOR_USER)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                habits.add(unwrap(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.SELECT_ERROR, "habit");
        }
        return habits;
    }

    @Override
    public List<Habit> searchForUser(@NotNull Long userId, @NotNull HabitSearchForm form) {
        List<Habit> habits = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.SEARCH_HABITS_FOR_USER)) {

            statement.setLong(1, userId);

            String value = !StringUtil.isEmpty(form.getName()) ? "%" + form.getName() + "%" : null;
            statement.setString(2, value);
            statement.setString(3, value);

            value = form.getFrequency() != null ? form.getFrequency() : null;
            statement.setString(4, value);
            statement.setString(5, value);

            statement.setDate(6, form.getFrom() != null ? Date.valueOf(form.getFrom()) : new Date(0L));
            statement.setDate(7, form.getTo() != null ? Date.valueOf(form.getTo()) : new Date(System.currentTimeMillis()));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                habits.add(unwrap(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.SELECT_ERROR, "habit");
        }
        return habits;
    }

    @Override
    public boolean save(Habit habit) {
        int result;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.INSERT_INTO_HABITS)) {

            statement.setString(1, habit.getName());
            statement.setString(2, habit.getDescription());
            statement.setString(3, String.valueOf(habit.getFrequency()));
            statement.setDate(4, Date.valueOf(habit.getCreated()));
            statement.setLong(5, habit.getUserId());
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.CREATE_ERROR, "habit");
        }
        return result == 1;
    }

    @Override
    public boolean update(Habit habit) {
        int result;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.UPDATE_HABITS)) {

            statement.setString(1, habit.getName());
            statement.setString(2, habit.getDescription());
            statement.setString(3, String.valueOf(habit.getFrequency()));
            statement.setDate(4, Date.valueOf(habit.getCreated()));
            statement.setLong(5, habit.getUserId());
            statement.setLong(6, habit.getId());
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.UPDATE_ERROR, "habit");
        }
        return result == 1;
    }

    @Override
    public boolean delete(Long userId, Long habitId) {
        int result;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.DELETE_FROM_HABITS)) {

            statement.setLong(1, userId);
            statement.setLong(2, habitId);
            result = statement.executeUpdate();
            connection.commit();

            return result == 1;
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.DELETE_ERROR, "habit");
        }
    }

    /**
     * Forms habit from current row in result set.
     *
     * @param rs result set
     * @return created habit
     * @throws SQLException if error occurs when accessing result set columns
     */
    private Habit unwrap(ResultSet rs) throws SQLException {
        Habit habit = new Habit();
        habit.setId(rs.getLong("id"));
        habit.setName(rs.getString("name"));
        habit.setDescription(rs.getString("description"));
        habit.setFrequency(Habit.Frequency.valueOf(rs.getString("frequency")));
        habit.setCreated(rs.getDate("created").toLocalDate());
        habit.setUserId(rs.getLong("user_id"));
        return habit;
    }
}
