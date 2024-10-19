package ru.ylab;

import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.ylab.config.AppContext;
import ru.ylab.handlers.AbstractHandler;
import ru.ylab.handlers.Page;
import ru.ylab.models.User;

/**
 * Class representing application.
 *
 * @author azatyamanaev
 */
@Slf4j
public class App {

    /**
     * Parameter determining whether application is running.
     */
    private static boolean running = true;

    /**
     * Parameter determining which page application is currently on.
     */
    @Getter
    private static Page page;

    /**
     * Handler for the current page.
     */
    @Getter
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
        CONTEXT.getLiquibaseService().migrate();
        page = Page.AUTH_PAGE;
        handler = CONTEXT.getHandlers().get(Page.AUTH_PAGE);

        while (running) {
            handler.drawPage(currentUser, page.getOptions());
            handler.handleInput();
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
     *
     * @throws SQLException if error occurs during connection closure
     */
    public static void shutdown() {
        try {
            CONTEXT.getConnectionPool().shutdown();
        } catch (SQLException e) {
            log.error("Error during shutdown {}", e.getMessage());
            throw new RuntimeException(e);
        }
        running = false;
    }
}
