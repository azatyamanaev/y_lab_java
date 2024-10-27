package ru.ylab.config;

import lombok.Getter;
import ru.ylab.config.datasource.DataSourceConfig;
import ru.ylab.repositories.HabitHistoryRepository;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.repositories.RefreshTokenRepository;
import ru.ylab.repositories.UserRepository;
import ru.ylab.repositories.impl.HabitHistoryRepositoryImpl;
import ru.ylab.repositories.impl.HabitRepositoryImpl;
import ru.ylab.repositories.impl.RefreshTokenRepositoryImpl;
import ru.ylab.repositories.impl.UserRepositoryImpl;

/**
 * Repositories configuration class.
 *
 * @author azatyamanaev
 */
@Getter
public class RepositoriesConfig {

    /**
     * Instance of an {@link UserRepository}.
     */
    private final UserRepository userRepository;

    /**
     * Instance of an {@link HabitRepository}.
     */
    private final HabitRepository habitRepository;

    /**
     * Instance of an {@link HabitHistoryRepository}.
     */
    private final HabitHistoryRepository habitHistoryRepository;

    /**
     * Instance of a {@link RefreshTokenRepository}.
     */
    private final RefreshTokenRepository tokenRepository;

    /**
     * Creates new RepositoriesConfig.
     *
     * @param config datasource
     */
    public RepositoriesConfig(DataSourceConfig config) {
        this.userRepository = new UserRepositoryImpl(config.getDataSource());
        this.habitRepository = new HabitRepositoryImpl(config.getDataSource());
        this.habitHistoryRepository = new HabitHistoryRepositoryImpl(config.getDataSource());
        this.tokenRepository = new RefreshTokenRepositoryImpl(config.getDataSource());
    }
}
