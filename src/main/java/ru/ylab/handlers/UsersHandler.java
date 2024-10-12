package ru.ylab.handlers;

import java.util.Scanner;

import ru.ylab.App;
import ru.ylab.services.UserService;

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
     * @param userService UserService instance
     */
    public UsersHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleInput(Scanner scanner) {
        switch (scanner.next()) {
            case "1":
                userService.getUsers(scanner);
                break;
            case "2":
                userService.createByAdmin(scanner);
                break;
            case "3":
                userService.deleteByAdmin(scanner);
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
        }
    }
}
