package ru.ylab.services.datasource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface describing logic for working with connection pool.
 *
 * @author azatyamanaev
 */
public interface CPDataSource {

    /**
     * Gets connection from connection pool.
     *
     * @return connection to database
     * @throws SQLException if a database access error occurs
     */
    Connection getConnection() throws SQLException;

    /**
     * Sets autocommit parameter.
     *
     * @param autoCommit whether connections should autocommit changes
     */
    void setAutoCommit(boolean autoCommit);
}
