package ru.ylab.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.forms.UserSearchForm;
import ru.ylab.models.User;
import ru.ylab.repositories.UserRepository;
import ru.ylab.utils.SqlConstants;
import ru.ylab.utils.StringUtil;

/**
 * Class implementing {@link UserRepository}.
 *
 * @author azatyamanaev
 */
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    /**
     * Instance of a {@link CPDataSource}.
     */
    private final CPDataSource dataSource;

    /**
     * Creates new UserRepositoryImpl.
     *
     * @param dataSource CPDataSource instance
     */
    public UserRepositoryImpl(CPDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = Optional.empty();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.SELECT_FROM_USERS_BY_EMAIL)){

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = Optional.of(unwrap(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            log.error("Error while getting user {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        return findByEmail(email).orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SqlConstants.SELECT_ALL_FROM_USERS)) {

            while (resultSet.next()) {
                users.add(unwrap(resultSet));
            }
        } catch (SQLException e) {
            log.error("Error while getting users {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public List<User> search(@NotNull UserSearchForm form) {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.SEARCH_USERS)) {

            String value =  !StringUtil.isEmpty(form.getName()) ? "%" + form.getName() + "%" : null;
            statement.setString(1, value);
            statement.setString(2, value);

            value = !StringUtil.isEmpty(form.getEmail()) ? "%" + form.getEmail() + "%" : null;
            statement.setString(3, value);
            statement.setString(4, value);

            value = !StringUtil.isEmpty(form.getRole()) ? form.getRole() : null;
            statement.setString(5, value);
            statement.setString(6, value);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(unwrap(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            log.error("Error while searching users {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User save(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.INSERT_INTO_USERS)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().toString());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("Error while saving user {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User update(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.UPDATE_USERS)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            log.error("Error while updating user {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public boolean deleteByEmail(String email) {
        User user = getByEmail(email);
        int result;
        if (user == null) {
            return false;
        } else {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SqlConstants.DELETE_FROM_USERS_BY_EMAIL)) {

                statement.setString(1, email);
                result = statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                log.error("Error while deleting user {}", e.getMessage());
                throw new RuntimeException(e);
            }
            return result == 1;
        }
    }

    /**
     * Forms user from current row in result set.
     *
     * @param rs result set
     * @return created user
     * @throws SQLException if error occurs when accessing result set columns
     */
    private User unwrap(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(User.Role.valueOf(rs.getString("role")));
        return user;
    }
}
