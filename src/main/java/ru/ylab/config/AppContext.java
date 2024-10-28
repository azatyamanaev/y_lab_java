package ru.ylab.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.ylab.config.datasource.DataSourceConfig;
import ru.ylab.settings.DbSettings;
import ru.ylab.settings.LiquibaseSettings;
import ru.ylab.utils.ConfigParser;

/**
 * Class representing application context.
 *
 * @author azatyamanaev
 */
@Slf4j
@Getter
public class AppContext {

    /**
     * Instance of a {@link DbSettings}.
     */
    private final DbSettings dbSettings;

    /**
     * Instance of a {@link LiquibaseSettings}.
     */
    private final LiquibaseSettings liquibaseSettings;

    /**
     * Instance of a {@link DataSourceConfig}.
     */
    private final DataSourceConfig dataSourceConfig;

    /**
     * Instance of a {@link RepositoriesConfig}.
     */
    private final RepositoriesConfig repositoriesConfig;

    /**
     * Instance of a {@link ValidatorsConfig}.
     */
    private final ValidatorsConfig validatorsConfig;

    /**
     * Instance of a {@link ServicesConfig}.
     */
    private final ServicesConfig servicesConfig;

    /**
     * Instance of a {@link MappersConfig}.
     */
    private final MappersConfig mappersConfig;

    /**
     * Creates new AppContext.
     *
     * @param profile app profile
     */
    public AppContext(String profile) {
        this.dbSettings = ConfigParser.parseDbSettings(profile);
        log.info("Database username {}, password {}", dbSettings.username(), dbSettings.password());
        this.liquibaseSettings = ConfigParser.parseLiquibaseSettings(profile);

        this.dataSourceConfig = new DataSourceConfig(dbSettings, liquibaseSettings);
        this.repositoriesConfig = new RepositoriesConfig(dataSourceConfig);
        this.validatorsConfig = new ValidatorsConfig(repositoriesConfig);
        this.servicesConfig = new ServicesConfig(dataSourceConfig, repositoriesConfig, validatorsConfig);
        this.mappersConfig = new MappersConfig();
    }

    /**
     * Creates new instance of an AppContext class.
     *
     * @param profile app profile
     * @return instance of an AppContext
     */
    public static AppContext createContext(String profile) {
        return new AppContext(profile);
    }
}
