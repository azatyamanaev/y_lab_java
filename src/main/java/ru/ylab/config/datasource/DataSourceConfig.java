package ru.ylab.config.datasource;

import lombok.Getter;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.services.datasource.ConnectionPool;
import ru.ylab.services.datasource.impl.BasicCPDataSource;
import ru.ylab.services.datasource.impl.BasicConnectionPool;
import ru.ylab.settings.DbSettings;
import ru.ylab.settings.LiquibaseSettings;
import ru.ylab.utils.ConfigParser;

/**
 * DataSource configuration class.
 *
 * @author azatyamanaev
 */
@Getter
public class DataSourceConfig {

    /**
     * Instance of a {@link ConnectionPool}.
     */
    private final ConnectionPool connectionPool;

    /**
     * Instance of a {@link CPDataSource}.
     */
    private final CPDataSource dataSource;

    /**
     * Instance of a {@link LiquibaseConfig}.
     */
    private final LiquibaseConfig liquibaseConfig;

    /**
     * Creates new DataSourceConfig.
     */
    public DataSourceConfig() {
        DbSettings dbSettings = ConfigParser.parseDbSettings();
        LiquibaseSettings liquibaseSettings = ConfigParser.parseLiquibaseSettings();

        this.connectionPool = new BasicConnectionPool(dbSettings);
        this.dataSource = new BasicCPDataSource(connectionPool);
        this.dataSource.setAutoCommit(false);
        this.liquibaseConfig = new LiquibaseConfig(liquibaseSettings);
    }
}