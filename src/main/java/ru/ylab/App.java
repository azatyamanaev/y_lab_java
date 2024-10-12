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
}
