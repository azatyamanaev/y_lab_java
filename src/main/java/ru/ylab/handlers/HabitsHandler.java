package ru.ylab.handlers;

import java.util.Scanner;

import ru.ylab.App;

/**
 * Class for handling user inputs for {@link Page#HABITS_PAGE} page.
 *
 * @author azatyamanaev
 */
public class HabitsHandler extends AbstractHandler {

    /**
     * Creates new AbstractHandler
     *
     * @param scanner scanner for reading user input
     */
    public HabitsHandler(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void handleInput() {
        switch (scanner.next()) {
            case "1":
                App.redirect(Page.MANAGE_HABITS_PAGE);
                break;
            case "2":
                App.redirect(Page.HABIT_HISTORY_PAGE);
                break;
            case "3":
                App.redirect(Page.HABIT_STATISTICS_PAGE);
                break;
            case "4":
                App.redirect(Page.AUTHORIZED_USER_PAGE);
                break;
            case "5":
                App.logout();
                break;
            case "6":
                App.shutdown();
                break;
            default:
                System.out.println("Invalid input. Try again.");
        }
    }
}
