package ru.ylab.config.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.ylab.services.datasource.ConnectionPool;
import ru.ylab.settings.LiquibaseSettings;

/**
 * Class for migrating database with Liquibase.
 *
 * @author azatyamanaev
 */
public class LiquibaseConfig {

    /**
     * Liquibase settings.
     */
    private final LiquibaseSettings settings;


    /**
     * Creates new LiquibaseConfig.
     *
     * @param settings liquibase settings
     */
    public LiquibaseConfig(LiquibaseSettings settings) {
        this.settings = settings;
    }

    /**
     * Creates new Liquibase instance.
     *
     * @param connectionPool connection pool for getting database connection
     * @return Liquibase instance
     */
    public Liquibase liquibase(ConnectionPool connectionPool) {
        try {
            Connection connection = connectionPool.getConnection();
            createDefaultSchemas(connection);

            Database database = DatabaseFactory.getInstance()
                                               .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(settings.getDefaultSchema());
            database.setLiquibaseSchemaName(settings.getChangelogSchema());
            return new Liquibase(settings.getLocation(), new ClassLoaderResourceAccessor(), database);
        } catch (SQLException | LiquibaseException e) {
            System.out.println("Error when configuring liquibase " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void createDefaultSchemas(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS " + settings.getChangelogSchema());
        statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS " + settings.getDefaultSchema());
        connection.commit();
    }
}
