package ru.ylab.core.config.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import ru.ylab.core.exception.HttpException;
import ru.ylab.core.settings.LiquibaseSettings;
import ru.ylab.core.utils.constants.ErrorConstants;

/**
 * Class for migrating database with Liquibase.
 *
 * @author azatyamanaev
 */
@RequiredArgsConstructor
public class LiquibaseConfig {

    /**
     * Liquibase settings.
     */
    private final LiquibaseSettings settings;

    /**
     * Creates new Liquibase instance.
     *
     * @param datasource datasource for getting database connection
     * @return Liquibase instance
     */
    public Liquibase liquibase(DataSource datasource) {
        try {
            Connection connection = datasource.getConnection();
            Database database = DatabaseFactory.getInstance()
                                               .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setDefaultSchemaName(settings.defaultSchema());
            database.setLiquibaseSchemaName(settings.changelogSchema());
            return new Liquibase(settings.changelogPath(), new ClassLoaderResourceAccessor(), database);
        } catch (SQLException | LiquibaseException e) {
            throw HttpException.liquibaseError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.CONFIGURATION_ERROR, "liquibase");
        }
    }
}
