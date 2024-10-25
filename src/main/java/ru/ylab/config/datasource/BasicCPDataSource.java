package ru.ylab.config.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import ru.ylab.services.datasource.ConnectionPool;

/**
 * Class implementing {@link CPDataSource}.
 *
 * @author azatyamanaev
 */
public class BasicCPDataSource implements CPDataSource {

    /**
     * Whether connections should autocommit changes.
     */
    private boolean autoCommit;

    /**
     * Instance of a {@link ConnectionPool}.
     */
    private final ConnectionPool connectionPool;

    /**
     * Creates new BasicCPDataSource.
     *
     * @param connectionPool ConnectionPool instance.
     */
    public BasicCPDataSource(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

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
