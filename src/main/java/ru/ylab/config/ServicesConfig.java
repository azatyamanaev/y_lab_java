package ru.ylab.config;

import lombok.Getter;
import ru.ylab.config.datasource.DataSourceConfig;
import ru.ylab.services.auth.AuthService;
import ru.ylab.services.auth.JwtService;
import ru.ylab.services.auth.PasswordService;
import ru.ylab.services.auth.impl.AuthServiceImpl;
import ru.ylab.services.auth.impl.JwtServiceImpl;
import ru.ylab.services.auth.impl.PasswordServiceImpl;
import ru.ylab.services.datasource.LiquibaseService;
import ru.ylab.services.datasource.impl.LiquibaseServiceImpl;
import ru.ylab.services.entities.HabitHistoryService;
import ru.ylab.services.entities.HabitService;
import ru.ylab.services.entities.UserService;
import ru.ylab.services.entities.impl.HabitHistoryServiceImpl;
import ru.ylab.services.entities.impl.HabitServiceImpl;
import ru.ylab.services.entities.impl.UserServiceImpl;

/**
 * Repositories configuration class.
 *
 * @author azatyamanaev
 */
@Getter
public class ServicesConfig {

    /**
     * Instance of an {@link LiquibaseService}.
     */
    private final LiquibaseService liquibaseService;

    /**
     * Instance of a {@link PasswordService}.
     */
    private final PasswordService passwordService;

    /**
     * Instance of an {@link UserService}.
     */
    private final UserService userService;

    /**
     * Instance of an {@link HabitService}.
     */
    private final HabitService habitService;

    /**
     * Instance of an {@link HabitHistoryService}.
     */
    private final HabitHistoryService habitHistoryService;

    /**
     * Instance of a {@link JwtService}.
     */
    private final JwtService jwtService;

    /**
     * Instance of an {@link AuthService}.
     */
    private final AuthService authService;

    /**
     * Creates new ServicesConfig.
     *
     * @param dataSourceConfig datasource
     * @param repositoriesConfig repositories
     * @param validatorsConfig validators
     */
    public ServicesConfig(DataSourceConfig dataSourceConfig, RepositoriesConfig repositoriesConfig,
                          ValidatorsConfig validatorsConfig) {
        this.liquibaseService = new LiquibaseServiceImpl(dataSourceConfig.getLiquibaseConfig()
                                                                         .liquibase(dataSourceConfig.getConnectionPool()));
        this.passwordService = new PasswordServiceImpl();
        this.userService = new UserServiceImpl(repositoriesConfig.getUserRepository(), passwordService,
                validatorsConfig.getUserFormValidator(), validatorsConfig.getUserSearchFormValidator());
        this.habitService = new HabitServiceImpl(repositoriesConfig.getHabitRepository(),
                validatorsConfig.getHabitFormValidator(),
                validatorsConfig.getHabitSearchFormValidator());
        this.habitHistoryService = new HabitHistoryServiceImpl(repositoriesConfig.getHabitHistoryRepository(), habitService,
                validatorsConfig.getHabitPercentageFormValidator());
        this.jwtService = new JwtServiceImpl(repositoriesConfig.getTokenRepository(), userService);
        this.authService = new AuthServiceImpl(passwordService, userService, jwtService,
                validatorsConfig.getSignInValidator(), validatorsConfig.getUserFormValidator());
    }
}
