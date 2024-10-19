package ru.ylab.handlers;

import java.util.Scanner;

import ru.ylab.App;
import ru.ylab.services.entities.UserService;

/**
 * Class for handling user inputs for {@link Page#USERS_PAGE} page.
 *
 * @author azatyamanaev
 */
public class UsersHandler extends AbstractHandler {

    /**
     * Instance of an {@link UserService}
     */
    private final UserService userService;

    /**
     * Creates new UsersHandler
     *
     * @param scanner     scanner for reading user input
     * @param userService UserService instance
     */
    public UsersHandler(Scanner scanner, UserService userService) {
        super(scanner);
        this.userService = userService;
    }

    @Override
    public void handleInput() {
        switch (scanner.next()) {
            case "1":
                response = userService.getUsers();
                System.out.println(response);
                waitForInput();
                break;
            case "2":
                userService.createByAdmin();
                waitForInput();
                break;
            case "3":
                userService.deleteByAdmin();
                waitForInput();
                break;
            case "4":
                App.redirect(Page.ADMIN_PANEL_PAGE);
                break;
            case "5":
                App.logout();
                break;
            case "6":
                App.shutdown();
                break;
            default:
                System.out.println("Invalid input. Try again.");
                waitForInput();
        }
    }
}
