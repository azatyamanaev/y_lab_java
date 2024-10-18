package ru.ylab.handlers;

import java.util.Scanner;

import ru.ylab.App;
import ru.ylab.services.HabitService;

/**
 * Class for handling user inputs for {@link Page#MANAGE_HABITS_PAGE} page.
 *
 * @author azatyamanaev
 */
public class ManageHabitsHandler extends AbstractHandler {

    /**
     * Instance of a {@link HabitService}
     */
    private final HabitService habitService;

    /**
     * Creates new ManageHabitsHandler
     *
     * @param scanner      scanner for reading user input
     * @param habitService HabitService instance
     */
    public ManageHabitsHandler(Scanner scanner, HabitService habitService) {
        super(scanner);
        this.habitService = habitService;
    }

    @Override
    public void handleInput() {
        switch (scanner.next()) {
            case "1":
                response = habitService.getHabits();
                System.out.println(response);
                waitForInput();
                break;
            case "2":
                habitService.create();
                waitForInput();
                break;
            case "3":
                habitService.update();
                waitForInput();
                break;
            case "4":
                habitService.deleteByName();
                waitForInput();
                break;
            case "5":
                App.redirect(Page.AUTHORIZED_USER_PAGE);
                break;
            case "6":
                App.logout();
                break;
            case "7":
                App.shutdown();
                break;
            default:
                System.out.println("Invalid input. Try again.");
                waitForInput();
        }
    }
}
