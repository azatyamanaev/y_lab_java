package ru.ylab.handlers;

import java.util.Scanner;

import ru.ylab.App;

/**
 * Class for handling user inputs for {@link Page#AUTHORIZED_USER_PAGE} page.
 *
 * @author azatyamanaev
 */
public class AuthorizedUserHandler extends AbstractHandler {

    /**
     * Creates new AbstractHandler
     *
     * @param scanner scanner for reading user input
     */
    public AuthorizedUserHandler(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void handleInput() {
        switch (scanner.next()) {
            case "1":
                App.redirect(Page.USER_PROFILE_PAGE);
                break;
            case "2":
                App.redirect(Page.HABITS_PAGE);
                break;
            case "3":
                App.logout();
                break;
            case "4":
                App.shutdown();
                break;
            default:
                System.out.println("Invalid input. Try again.");
                waitForInput();
        }
    }
}
