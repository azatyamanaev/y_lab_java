package ru.ylab.handlers;

import java.util.Scanner;

import ru.ylab.App;
import ru.ylab.services.HabitService;

/**
 * Class for handling user inputs for {@link Page#ADMIN_PANEL_PAGE} page.
 *
 * @author azatyamanaev
 */
public class AdminPanelHandler extends AbstractHandler {

    /**
     * Instance of a {@link HabitService}
     */
    private final HabitService habitService;

    /**
     * Creates new AdminPanelHandler
     *
     * @param scanner      scanner for reading user input
     * @param habitService HabitService instance
     */
    public AdminPanelHandler(Scanner scanner, HabitService habitService) {
        super(scanner);
        this.habitService = habitService;
    }

    @Override
    public void handleInput() {
        switch (scanner.next()) {
            case "1":
                App.redirect(Page.USERS_PAGE);
                break;
            case "2":
                habitService.getHabits();
                break;
            case "3":
                App.logout();
                break;
            case "4":
                App.shutdown();
                break;
            default:
                System.out.println("Invalid input. Try again.");
        }
    }
}
