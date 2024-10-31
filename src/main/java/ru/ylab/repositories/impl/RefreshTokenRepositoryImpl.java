package ru.ylab.repositories.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.ylab.aspects.LogQuery;
import ru.ylab.exception.HttpException;
import ru.ylab.models.RefreshToken;
import ru.ylab.repositories.RefreshTokenRepository;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.utils.constants.ErrorConstants;
import ru.ylab.utils.constants.SqlConstants;

/**
 * Class implementing {@link RefreshTokenRepository}
 *
 * @author azatyamanaev
 */
@LogQuery
@RequiredArgsConstructor
@Repository
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    /**
     * Instance of a {@link CPDataSource}.
     */
    private final CPDataSource dataSource;

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        Optional<RefreshToken> refreshToken = Optional.empty();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.SELECT_FROM_REFRESH_TOKENS_BY_TOKEN)) {

            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                refreshToken = Optional.of(unwrap(resultSet));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.SELECT_ERROR, "refresh token");
        }
        return refreshToken;
    }

    @Override
    public boolean save(RefreshToken refreshToken) {
        int result = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstants.INSERT_INTO_REFRESH_TOKENS)) {

            statement.setString(1, refreshToken.getToken());
            statement.setLong(2, refreshToken.getUserId());
            statement.setTimestamp(3, Timestamp.from(refreshToken.getCreated()));
            statement.setTimestamp(4, Timestamp.from(refreshToken.getExpires()));
            result = statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw HttpException.databaseAccessError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.CREATE_ERROR, "refresh token");
        }
        return result == 1;
    }

    /**
     * Forms refresh token from current row in result set.
     *
     * @param rs result set
     * @return created refresh token
     * @throws SQLException if error occurs when accessing result set columns
     */
    private RefreshToken unwrap(ResultSet rs) throws SQLException {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(rs.getLong("id"));
        refreshToken.setToken(rs.getString("token"));
        refreshToken.setUserId(rs.getLong("user_id"));
        refreshToken.setCreated(rs.getTimestamp("created").toInstant());
        refreshToken.setExpires(rs.getTimestamp("expires").toInstant());
        return refreshToken;
    }
}
