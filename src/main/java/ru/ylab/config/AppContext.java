package ru.ylab.config;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import ru.ylab.handlers.AdminPanelHandler;
import ru.ylab.handlers.AuthHandler;
import ru.ylab.handlers.AuthorizedUserHandler;
import ru.ylab.handlers.HabitHistoryHandler;
import ru.ylab.handlers.HabitStatisticsHandler;
import ru.ylab.handlers.HabitsHandler;
import ru.ylab.handlers.AbstractHandler;
import ru.ylab.handlers.ManageHabitsHandler;
import ru.ylab.handlers.Page;
import ru.ylab.handlers.UserProfileHandler;
import ru.ylab.handlers.UsersHandler;
import ru.ylab.repositories.HabitHistoryRepository;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.repositories.UserRepository;
import ru.ylab.repositories.impl.HabitHistoryRepositoryImpl;
import ru.ylab.repositories.impl.HabitRepositoryImpl;
import ru.ylab.repositories.Storage;
import ru.ylab.repositories.impl.UserRepositoryImpl;
import ru.ylab.services.AuthService;
import ru.ylab.services.HabitHistoryService;
import ru.ylab.services.HabitService;
import ru.ylab.services.UserService;
import ru.ylab.services.impl.AuthServiceImpl;
import ru.ylab.services.impl.HabitHistoryServiceImpl;
import ru.ylab.services.impl.HabitServiceImpl;
import ru.ylab.services.impl.UserServiceImpl;

/**
 * Class representing application context.
 *
 * @author azatyamanaev
 */
@Getter
public class AppContext {

    /**
     * Map for storing Handler implementations for each page.
     */
    private final Map<Page, AbstractHandler> handlers;

    /**
     * Instance of a {@link Storage}
     */
    private final Storage storage;

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
        this.storage = new Storage();
        this.userRepository = new UserRepositoryImpl(storage);
        this.habitRepository = new HabitRepositoryImpl(storage);
        this.habitHistoryRepository = new HabitHistoryRepositoryImpl(storage);
        this.userService = new UserServiceImpl(userRepository);
        this.habitService = new HabitServiceImpl(habitRepository, habitHistoryRepository);
        this.authService = new AuthServiceImpl(userRepository);
        this.habitHistoryService = new HabitHistoryServiceImpl(habitRepository, habitHistoryRepository);

        this.handlers = new HashMap<>();
        handlers.put(Page.AUTH_PAGE, new AuthHandler(authService));
        handlers.put(Page.AUTHORIZED_USER_PAGE, new AuthorizedUserHandler());
        handlers.put(Page.USER_PROFILE_PAGE, new UserProfileHandler(userService));
        handlers.put(Page.HABITS_PAGE, new HabitsHandler());
        handlers.put(Page.MANAGE_HABITS_PAGE, new ManageHabitsHandler(habitService));
        handlers.put(Page.HABIT_HISTORY_PAGE, new HabitHistoryHandler(habitService, habitHistoryService));
        handlers.put(Page.HABIT_STATISTICS_PAGE, new HabitStatisticsHandler(habitHistoryService));
        handlers.put(Page.ADMIN_PANEL_PAGE, new AdminPanelHandler(habitService));
        handlers.put(Page.USERS_PAGE, new UsersHandler(userService));
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
