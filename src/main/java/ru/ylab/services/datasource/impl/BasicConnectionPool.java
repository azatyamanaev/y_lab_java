package ru.ylab.services.datasource.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.services.datasource.ConnectionPool;
import ru.ylab.settings.DbSettings;

/**
 * Class implementing {@link ConnectionPool}.
 *
 * @author azatyamanaev
 */
@Slf4j
public class BasicConnectionPool implements ConnectionPool {

    /**
     * Initial size of a connection pool.
     */
    private static final int INITIAL_POOL_SIZE = 10;

    /**
     * Max total connections in connection pool and used connections.
     */
    private static final int MAX_POOL_SIZE = 100;

    /**
     * Timeout to wait for connection before it is considered invalid.
     */
    private static final int MAX_TIMEOUT = 5;

    /**
     * Database url.
     */
    private final String url;

    /**
     * Database username.
     */
    private final String username;

    /**
     * Database password.
     */
    private final String password;

    /**
     * List with available connections.
     */
    private final List<Connection> connectionPool;

    /**
     * List with connections currently in use.
     */
    private final List<Connection> usedConnections;

    /**
     * Creates new BasicConnectionPool and populates connectionPool field
     * with initial pool size number of connections.
     *
     * @param settings settings for connecting to database
     */
    public BasicConnectionPool(DbSettings settings) {
        this.url = settings.getUrl();
        this.username = settings.getUsername();
        this.password = settings.getPassword();
        this.usedConnections = new ArrayList<>();

        this.connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
        try {
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                connectionPool.add(createConnection(settings.getUrl(), settings.getUsername(), settings.getPassword()));
            }
        } catch (SQLException e) {
            log.error("Error when creating connection {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates new connection to database.
     *
     * @param url database url
     * @param username username, used to access database
     * @param password password, used to access database
     * @return new connection to database
     * @throws SQLException if a database access error occurs or the url is null
     */
    private Connection createConnection(String url, String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection() throws SQLException, RuntimeException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection(url, username, password));
            } else {
                throw new RuntimeException("Maximum pool size reached, no available connections!");
            }
        }
        Connection connection = connectionPool.remove(connectionPool.size() - 1);

        if (!connection.isValid(MAX_TIMEOUT)) {
            connection = createConnection(url, username, password);
        }

        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    @Override
    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void shutdown() throws SQLException {
        for (Connection used : usedConnections) {
            used.close();
        }
        usedConnections.clear();
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }
}
