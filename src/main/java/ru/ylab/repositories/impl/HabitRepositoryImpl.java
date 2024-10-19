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
import ru.ylab.forms.HabitSearchForm;
import ru.ylab.models.Habit;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.services.datasource.ConnectionPool;
import ru.ylab.utils.StringUtil;

/**
 * Class implementing {@link HabitRepository}.
 *
 * @author azatyamanaev
 */
public class HabitRepositoryImpl implements HabitRepository {

    /**
     * Instance of a {@link ConnectionPool}.
     */
    private final ConnectionPool connectionPool;

    /**
     * Creates new HabitRepositoryImpl.
     *
     * @param connectionPool ConnectionPool instance
     */
    public HabitRepositoryImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<Habit> findByName(String name) {
        Optional<Habit> habit = Optional.empty();
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM entity.habits WHERE name = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                habit = Optional.of(unwrap(resultSet));
            }

            resultSet.close();
            statement.close();
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return habit;
    }

    @Override
    public Habit getByName(String name) {
        return findByName(name).orElse(null);
    }

    @Override
    public boolean existsByName(String name) {
        return findByName(name).isPresent();
    }

    @Override
    public List<Habit> getAll() {
        List<Habit> habits = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM entity.habits");
            while (resultSet.next()) {
                habits.add(unwrap(resultSet));
            }

            resultSet.close();
            statement.close();
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return habits;
    }

    @Override
    public List<Habit> search(@NotNull HabitSearchForm form) {
        List<Habit> habits = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM entity.habits WHERE name LIKE ? and frequency = ? and created >= ? and created <= ?");
            statement.setString(1, !StringUtil.isEmpty(form.getName()) ? "%" + form.getName() + "%" : "*");
            statement.setString(2, form.getFrequency() != null ? form.getFrequency() : "*");
            statement.setDate(3, form.getFrom() != null ? Date.valueOf(form.getFrom()) : new Date(0L));
            statement.setDate(4, form.getTo() != null ? Date.valueOf(form.getTo()) : new Date(System.currentTimeMillis()));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                habits.add(unwrap(resultSet));
            }

            resultSet.close();
            statement.close();
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return habits;
    }

    @Override
    public List<Habit> getAllForUser(@NotNull Long userId) {
        List<Habit> habits = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM entity.habits WHERE user_id = ?");
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                habits.add(unwrap(resultSet));
            }

            resultSet.close();
            statement.close();
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return habits;
    }

    @Override
    public List<Habit> searchForUser(@NotNull Long userId, @NotNull HabitSearchForm form) {
        List<Habit> habits = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM entity.habits WHERE user_id = ? and name LIKE ? and frequency = ? and created >= ? and created <= ?");
            statement.setLong(1, userId);
            statement.setString(2, !StringUtil.isEmpty(form.getName()) ? "%" + form.getName() + "%" : "*");
            statement.setString(3, form.getFrequency() != null ? form.getFrequency() : "*");
            statement.setDate(4, form.getFrom() != null ? Date.valueOf(form.getFrom()) : new Date(0L));
            statement.setDate(5, form.getTo() != null ? Date.valueOf(form.getTo()) : new Date(System.currentTimeMillis()));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                habits.add(unwrap(resultSet));
            }

            resultSet.close();
            statement.close();
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return habits;
    }

   @Override
    public Habit save(Habit habit) {
        try {
            Connection connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO entity.habits(name, descripiton, frequency, created, user_id) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, habit.getName());
            statement.setString(2, habit.getDescription());
            statement.setString(3, String.valueOf(habit.getFrequency()));
            statement.setDate(4, Date.valueOf(habit.getCreated()));
            statement.setLong(5, habit.getUserId());
            statement.executeUpdate();
            connection.commit();

            statement.close();
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return habit;
    }

    @Override
    public Habit update(Habit habit) {
        try {
            Connection connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("UPDATE entity.habits set name = ?, description = ?, frequency = ?, created = ? WHERE user_id = ?");
            statement.setString(1, habit.getName());
            statement.setString(2, habit.getDescription());
            statement.setString(3, String.valueOf(habit.getFrequency()));
            statement.setDate(4, Date.valueOf(habit.getCreated()));
            statement.setLong(5, habit.getUserId());
            statement.executeUpdate();
            connection.commit();

            statement.close();
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return habit;
    }

    @Override
    public boolean delete(Long userId, @NotNull Habit habit) {
        int result;
        if (habit.getUserId().equals(userId)) {
            try {
                Connection connection = connectionPool.getConnection();
                connection.setAutoCommit(false);
                PreparedStatement statement = connection.prepareStatement("DELETE FROM entity.habits WHERE user_id = ? and id = ?");
                statement.setLong(1, userId);
                statement.setLong(2, habit.getId());
                result = statement.executeUpdate();
                connection.commit();

                statement.close();
                connectionPool.releaseConnection(connection);
                return result == 1;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            return false;
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
