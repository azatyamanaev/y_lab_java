package ru.ylab.core.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.ylab.auditstarter.repository.AuditRepository;
import ru.ylab.core.exception.HttpException;
import ru.ylab.core.models.User;
import ru.ylab.core.models.UserRequest;
import ru.ylab.core.repositories.UserRequestRepository;
import ru.ylab.core.utils.constants.ErrorConstants;
import ru.ylab.core.utils.constants.SqlConstants;

/**
 * Class implementing {@link UserRequestRepository}.
 *
 * @author azatyamanaev
 */
@Repository
@RequiredArgsConstructor
public class UserRequestRepositoryImpl implements UserRequestRepository, AuditRepository {

    private final DataSource dataSource;

    @Override
    public List<UserRequest> getAllForUser(Long id) {
        List<UserRequest> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.SELECT_FROM_USER_REQUESTS_BY_USER_ID)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(unwrap(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.SELECT_ERROR, "user request");
        }
        return result;
    }

    @Override
    public boolean save(UserRequest userRequest) {
        int result = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.INSERT_INTO_USER_REQUESTS)) {

            statement.setString(1, userRequest.getMethod());
            statement.setString(2, userRequest.getUri());
            statement.setLong(3, userRequest.getUserId());
            statement.setString(4, userRequest.getRole());
            statement.setTimestamp(5, Timestamp.from(userRequest.getTimestamp()));
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.CREATE_ERROR, "user request");
        }
        return result == 1;
    }

    @Override
    public void save(String method, String uri, Long userId, Object role, Instant timestamp) {
        UserRequest userRequest = new UserRequest();
        userRequest.setMethod(method);
        userRequest.setUri(uri);
        userRequest.setUserId(userId);
        userRequest.setRole(((User.Role) role).name());
        userRequest.setTimestamp(timestamp);
        save(userRequest);
    }

    /**
     * Forms user request from current row in result set.
     *
     * @param rs result set
     * @return created user request
     * @throws SQLException if error occurs when accessing result set columns
     */
    private UserRequest unwrap(ResultSet rs) throws SQLException {
        UserRequest userRequest = new UserRequest();
        userRequest.setId(rs.getLong("id"));
        userRequest.setMethod(rs.getString("method"));
        userRequest.setUri(rs.getString("uri"));
        userRequest.setUserId(rs.getLong("user_id"));
        userRequest.setRole(rs.getString("role"));
        userRequest.setTimestamp(rs.getTimestamp("timestamp").toInstant());
        return userRequest;
    }
}
