package ru.ylab.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import ru.spring.auditstarter.annotations.CalculateExecution;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.in.UserSearchForm;
import ru.ylab.exception.HttpException;
import ru.ylab.models.User;
import ru.ylab.repositories.UserRepository;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.utils.constants.ErrorConstants;
import ru.ylab.utils.constants.SqlConstants;

/**
 * Class implementing {@link UserRepository}.
 *
 * @author azatyamanaev
 */
@CalculateExecution
@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    /**
     * Instance of a {@link CPDataSource}.
     */
    private final DataSource dataSource;

    @Override
    public Optional<User> find(Long id) {
        Optional<User> user = Optional.empty();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.SELECT_FROM_USERS_BY_ID)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = Optional.of(unwrap(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.SELECT_ERROR, "user");
        }
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = Optional.empty();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.SELECT_FROM_USERS_BY_EMAIL)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = Optional.of(unwrap(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.SELECT_ERROR, "user");
        }
        return user;
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
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.SELECT_ERROR, "user");
        }
        return users;
    }

    @Override
    public List<User> search(@NotNull UserSearchForm form) {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.SEARCH_USERS)) {

            String value = !StringUtils.isBlank(form.getName()) ? "%" + form.getName() + "%" : null;
            statement.setString(1, value);
            statement.setString(2, value);

            value = !StringUtils.isBlank(form.getEmail()) ? "%" + form.getEmail() + "%" : null;
            statement.setString(3, value);
            statement.setString(4, value);

            value = form.getRole() != null ? form.getRole().name() : null;
            statement.setString(5, value);
            statement.setString(6, value);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(unwrap(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.SELECT_ERROR, "user");
        }
        return users;
    }

    @Override
    public boolean save(User user) {
        int result;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.INSERT_INTO_USERS)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().toString());
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.CREATE_ERROR, "user");
        }
        return result == 1;
    }

    @Override
    public boolean update(Long userId, SignUpForm form) {
        int result;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.UPDATE_USERS)) {

            statement.setString(1, form.getName());
            statement.setString(2, form.getEmail());
            statement.setString(3, form.getPassword());
            statement.setLong(4, userId);
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.UPDATE_ERROR, "user");
        }
        return result == 1;
    }

    @Override
    public boolean delete(Long userId) {
        int result;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.DELETE_FROM_USERS_BY_ID)) {

            statement.setLong(1, userId);
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.DELETE_ERROR, "user");
        }
        return result == 1;
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
