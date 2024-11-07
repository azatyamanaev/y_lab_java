package ru.ylab.services.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import ru.ylab.utils.constants.ErrorConstants;

/**
 * Interface describing logic for working with connection pool.
 *
 * @author azatyamanaev
 */
public interface CPDataSource extends DataSource {

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

    default Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException(ErrorConstants.NOT_IMPLEMENTED);
    }

    default PrintWriter getLogWriter() {
        throw new UnsupportedOperationException(ErrorConstants.NOT_IMPLEMENTED);
    }

    default void setLogWriter(PrintWriter out) {
        throw new UnsupportedOperationException(ErrorConstants.NOT_IMPLEMENTED);
    }

    default void setLoginTimeout(int seconds) {
        throw new UnsupportedOperationException(ErrorConstants.NOT_IMPLEMENTED);
    }

    default int getLoginTimeout() {
        throw new UnsupportedOperationException(ErrorConstants.NOT_IMPLEMENTED);
    }

    default Logger getParentLogger() {
        throw new UnsupportedOperationException(ErrorConstants.NOT_IMPLEMENTED);
    }
}
