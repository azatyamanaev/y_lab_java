package ru.ylab.config.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import ru.ylab.services.datasource.ConnectionPool;
import ru.ylab.settings.LiquibaseSettings;

/**
 * Class for migrating database with Liquibase.
 *
 * @author azatyamanaev
 */
@Slf4j
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
            Database database = DatabaseFactory.getInstance()
                                               .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(settings.defaultSchema());
            database.setLiquibaseSchemaName(settings.changelogSchema());
            return new Liquibase(settings.location(), new ClassLoaderResourceAccessor(), database);
        } catch (SQLException | LiquibaseException e) {
            log.error("Error when configuring liquibase {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}