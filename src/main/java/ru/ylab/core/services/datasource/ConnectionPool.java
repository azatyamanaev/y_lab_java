package ru.ylab.core.services.datasource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface describing logic of a database connection pool.
 *
 * @author azatyamanaev
 */
public interface ConnectionPool {

    /**
     * Gets connection from connection pool. If pool is empty and total
     * connection count is less than max pool size, creates new connection
     * and adds it to the pool. Then connection is removed from connection
     * pool and put in used connections. If obtained connection is not
     * valid, creates new connection.
     *
     * @return connection to database
     * @throws SQLException could be thrown when creating new connections
     * @throws RuntimeException thrown when connection pool is empty and
     * total connection count is equal max pool size
     */
    Connection getConnection() throws SQLException, RuntimeException;

    /**
     * Returns connection to connection pool from used connections.
     *
     * @param connection connection to release
     * @return whether removing connection from used connections
     * is successful
     */
    boolean releaseConnection(Connection connection);

    /**
     * Gets total count of connections in pool.
     *
     * @return connection pool size
     */
    int getSize();

    /**
     * Gets database url.
     *
     * @return database url
     */
    String getUrl();

    /**
     * Gets username, used to access database.
     *
     * @return database username
     */
    String getUserName();

    /**
     * Gets password, used to access database.
     *
     * @return database password
     */
    String getPassword();

    /**
     * Closes all connections and empties connection pool.
     *
     * @throws SQLException could be thrown when closing connections
     */
    void shutdown() throws SQLException;
}
