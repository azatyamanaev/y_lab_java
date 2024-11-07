package ru.ylab.core.services.datasource.impl;

import java.sql.Connection;
import java.sql.SQLException;

import ru.ylab.core.services.datasource.CPDataSource;
import ru.ylab.core.services.datasource.ConnectionPool;

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
     * Connection pool, associated with this datasource.
     */
    private final ConnectionPool connectionPool;

    /**
     * Creates new BasicCPDatasource.
     *
     * @param url database url
     * @param username database username
     * @param password database password
     */
    public BasicCPDataSource(String url, String username, String password) {
        this.connectionPool = new BasicConnectionPool(url, username, password);
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

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
