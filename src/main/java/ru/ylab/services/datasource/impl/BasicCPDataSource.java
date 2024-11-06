package ru.ylab.services.datasource.impl;

import java.sql.Connection;
import java.sql.SQLException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.services.datasource.ConnectionPool;

/**
 * Class implementing {@link CPDataSource}.
 *
 * @author azatyamanaev
 */
@Component
@RequiredArgsConstructor
public class BasicCPDataSource implements CPDataSource {

    /**
     * Whether connections should autocommit changes.
     */
    private boolean autoCommit;

    /**
     * Connection pool, associated with this datasource.
     */
    private final ConnectionPool connectionPool;

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = connectionPool.getConnection();
        configureConnection(connection);
        return connection;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    /**
     * Configures connection.
     *
     * @param connection Connection instance
     * @throws SQLException if a database access error occurs or
     * connection is closed
     */
    private void configureConnection(Connection connection) throws SQLException {
        connection.setAutoCommit(autoCommit);
    }
}
