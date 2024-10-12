package ru.ylab.handlers;

import java.util.Scanner;

import ru.ylab.App;
import ru.ylab.services.UserService;

/**
 * Class for handling user inputs for {@link Page#USER_PROFILE_PAGE} page.
 *
 * @author azatyamanaev
 */
public class UserProfileHandler extends AbstractHandler {

    /**
     * Instance of an {@link UserService}
     */
    private final UserService userService;

    /**
     * Creates new UserProfileHandler
     *
     * @param userService UserService instance
     */
    public UserProfileHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handleInput(Scanner scanner) {
        switch (scanner.next()) {
            case "1":
                userService.update(scanner);
                break;
            case "2":
                userService.delete(scanner);
                break;
            case "3":
                App.redirect(Page.AUTHORIZED_USER_PAGE);
                break;
            case "4":
                App.logout();
                break;
            case "5":
                App.shutdown();
                break;
            default:
                System.out.println("Invalid input. Try again.");
        }
    }
}
