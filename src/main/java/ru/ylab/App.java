package ru.ylab;

import java.util.Map;
import java.util.Scanner;

import lombok.Getter;
import lombok.Setter;
import ru.ylab.config.AppContext;
import ru.ylab.handlers.Page;
import ru.ylab.handlers.AbstractHandler;
import ru.ylab.models.Habit;
import ru.ylab.models.User;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.repositories.UserRepository;
import ru.ylab.utils.IdUtil;

/**
 * Class representing application.
 *
 * @author azaty
 */
public class App {

    /**
     * Parameter determining whether application is running.
     */
    private static boolean running = true;

    /**
     * Parameter determining which page application is currently on.
     */
    private static Page page;

    /**
     * Handler for the current page.
     */
    private static AbstractHandler handler;

    /**
     * Parameter storing data about authorized user.
     */
    @Setter
    @Getter
    private static User currentUser = null;

    /**
     * Parameter for storing app context.
     */
    @Getter
    private static final AppContext CONTEXT = AppContext.createContext();

    /**
     * Handles main process of an application.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        page = Page.AUTH_PAGE;
        handler = CONTEXT.getHandlers().get(Page.AUTH_PAGE);

        while (running) {
            handler.drawPage(currentUser, page.getOptions());
            handler.handleInput(scanner);
            System.out.print("\n\n");
        }
    }

    /**
     * Redirects to specified page.
     *
     * @param p redirect to this page
     */
    public static void redirect(Page p) {
        page = p;
        handler = CONTEXT.getHandlers().get(p);
    }

    /**
     * Logouts current user and redirects to auth page.
     */
    public static void logout() {
        setCurrentUser(null);
        redirect(Page.AUTH_PAGE);
    }

    /**
     * Stops the application.
     */
    public static void shutdown() {
        running = false;
    }

    static {
        Map<Long, User> users = UserRepository.getUsers();

        User user = new User(IdUtil.generateUserId(), "admin", "admin@mail.ru", "admin", User.Role.ADMIN);
        users.put(user.getId(), user);
        user = new User(IdUtil.generateUserId(), "user1", "a@mail.ru", "pass", User.Role.USER);
        users.put(user.getId(), user);
        user = new User(IdUtil.generateUserId(), "user2", "b@mail.ru", "pass", User.Role.USER);
        users.put(user.getId(), user);

        Map<Long, Habit> habits = HabitRepository.getHabits();

        Habit habit = new Habit(IdUtil.generateHabitId(), "h1", "desc1", Habit.Frequency.DAILY, 2L);
        habits.put(habit.getId(), habit);
        habit = new Habit(IdUtil.generateHabitId(), "h2", "desc2", Habit.Frequency.DAILY, 2L);
        habits.put(habit.getId(), habit);
        habit = new Habit(IdUtil.generateHabitId(), "h3", "desc3", Habit.Frequency.WEEKLY, 2L);
        habits.put(habit.getId(), habit);

        habit = new Habit(IdUtil.generateHabitId(), "hb1", "desc1", Habit.Frequency.DAILY, 3L);
        habits.put(habit.getId(), habit);
        habit = new Habit(IdUtil.generateHabitId(), "hb2", "desc2", Habit.Frequency.WEEKLY, 3L);
        habits.put(habit.getId(), habit);
        habit = new Habit(IdUtil.generateHabitId(), "hb3", "desc3", Habit.Frequency.DAILY, 3L);
        habits.put(habit.getId(), habit);
        habit = new Habit(IdUtil.generateHabitId(), "hb4", "desc4", Habit.Frequency.WEEKLY, 3L);
        habits.put(habit.getId(), habit);
    }
}
