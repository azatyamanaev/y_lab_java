package ru.ylab.config;

import lombok.Getter;
import ru.ylab.config.datasource.DataSourceConfig;

/**
 * Class representing application context.
 *
 * @author azatyamanaev
 */
@Getter
public class AppContext {

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
     */
    public AppContext() {
        this.dataSourceConfig = new DataSourceConfig();
        this.repositoriesConfig = new RepositoriesConfig(dataSourceConfig);
        this.validatorsConfig = new ValidatorsConfig(repositoriesConfig);
        this.servicesConfig = new ServicesConfig(dataSourceConfig, repositoriesConfig, validatorsConfig);
        this.mappersConfig = new MappersConfig();
    }

    /**
     * Creates new instance of an AppContext class.
     *
     * @return instance of an AppContext
     */
    public static AppContext createContext() {
        return new AppContext();
    }
}
