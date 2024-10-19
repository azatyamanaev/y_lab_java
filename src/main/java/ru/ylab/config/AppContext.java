package ru.ylab.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import lombok.Getter;
import ru.ylab.config.datasource.LiquibaseConfig;
import ru.ylab.handlers.AbstractHandler;
import ru.ylab.handlers.AdminPanelHandler;
import ru.ylab.handlers.AuthHandler;
import ru.ylab.handlers.AuthorizedUserHandler;
import ru.ylab.handlers.HabitHistoryHandler;
import ru.ylab.handlers.HabitStatisticsHandler;
import ru.ylab.handlers.HabitsHandler;
import ru.ylab.handlers.ManageHabitsHandler;
import ru.ylab.handlers.Page;
import ru.ylab.handlers.UserProfileHandler;
import ru.ylab.handlers.UsersHandler;
import ru.ylab.repositories.HabitHistoryRepository;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.repositories.UserRepository;
import ru.ylab.repositories.impl.HabitHistoryRepositoryImpl;
import ru.ylab.repositories.impl.HabitRepositoryImpl;
import ru.ylab.repositories.impl.UserRepositoryImpl;
import ru.ylab.services.datasource.ConnectionPool;
import ru.ylab.services.datasource.LiquibaseService;
import ru.ylab.services.datasource.impl.BasicConnectionPool;
import ru.ylab.services.datasource.impl.LiquibaseServiceImpl;
import ru.ylab.services.entities.AuthService;
import ru.ylab.services.entities.HabitHistoryService;
import ru.ylab.services.entities.HabitService;
import ru.ylab.services.entities.UserService;
import ru.ylab.services.entities.impl.AuthServiceImpl;
import ru.ylab.services.entities.impl.HabitHistoryServiceImpl;
import ru.ylab.services.entities.impl.HabitServiceImpl;
import ru.ylab.services.entities.impl.UserServiceImpl;
import ru.ylab.settings.DbSettings;
import ru.ylab.settings.LiquibaseSettings;
import ru.ylab.utils.ConfigParser;

/**
 * Class representing application context.
 *
 * @author azatyamanaev
 */
@Getter
public class AppContext {

    /**
     * Scanner for reading user input.
     */
    protected final Scanner scanner;

    /**
     * Map for storing Handler implementations for each page.
     */
    private final Map<Page, AbstractHandler> handlers;

    /**
     * Instance of a {@link ConnectionPool}.
     */
    private final ConnectionPool connectionPool;

    /**
     * Instance of a {@link LiquibaseConfig}.
     */
    private final LiquibaseConfig liquibaseConfig;

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
     * Instance of an {@link LiquibaseService}.
     */
    private final LiquibaseService liquibaseService;

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
     * Instance of an {@link AuthService}.
     */
    private final AuthService authService;

    public AppContext() {
        DbSettings dbSettings = ConfigParser.parseDbSettings();
        LiquibaseSettings liquibaseSettings = ConfigParser.parseLiquibaseSettings();

        this.scanner = new Scanner(System.in);
        this.connectionPool = new BasicConnectionPool(dbSettings);
        this.liquibaseConfig = new LiquibaseConfig(liquibaseSettings);
        this.userRepository = new UserRepositoryImpl(connectionPool);
        this.habitRepository = new HabitRepositoryImpl(connectionPool);
        this.habitHistoryRepository = new HabitHistoryRepositoryImpl(connectionPool);
        this.liquibaseService = new LiquibaseServiceImpl(liquibaseConfig.liquibase(connectionPool));
        this.userService = new UserServiceImpl(scanner, userRepository);
        this.habitService = new HabitServiceImpl(scanner, habitRepository, habitHistoryRepository);
        this.authService = new AuthServiceImpl(scanner, userService);
        this.habitHistoryService = new HabitHistoryServiceImpl(scanner, habitRepository, habitHistoryRepository);

        this.handlers = new HashMap<>();
        handlers.put(Page.AUTH_PAGE, new AuthHandler(scanner, authService));
        handlers.put(Page.AUTHORIZED_USER_PAGE, new AuthorizedUserHandler(scanner));
        handlers.put(Page.USER_PROFILE_PAGE, new UserProfileHandler(scanner, userService));
        handlers.put(Page.HABITS_PAGE, new HabitsHandler(scanner));
        handlers.put(Page.MANAGE_HABITS_PAGE, new ManageHabitsHandler(scanner, habitService));
        handlers.put(Page.HABIT_HISTORY_PAGE, new HabitHistoryHandler(scanner, habitService, habitHistoryService));
        handlers.put(Page.HABIT_STATISTICS_PAGE, new HabitStatisticsHandler(scanner, habitHistoryService));
        handlers.put(Page.ADMIN_PANEL_PAGE, new AdminPanelHandler(scanner, habitService));
        handlers.put(Page.USERS_PAGE, new UsersHandler(scanner, userService));
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
