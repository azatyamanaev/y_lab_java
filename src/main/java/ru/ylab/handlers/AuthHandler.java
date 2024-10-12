package ru.ylab.handlers;

import java.util.Scanner;

import ru.ylab.App;
import ru.ylab.services.AuthService;

/**
 * Class for handling user inputs for {@link Page#AUTH_PAGE} page.
 *
 * @author azatyamanaev
 */
public class AuthHandler extends AbstractHandler {

    /**
     * Instance of an {@link AuthService}
     */
    private final AuthService authService;

    /**
     * Creates new AuthHandler
     *
     * @param authService AuthService instance
     */
    public AuthHandler(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void handleInput(Scanner scanner) {
        switch (scanner.next()) {
            case "1":
                authService.signIn(scanner);
                break;
            case "2":
                authService.signUp(scanner);
                break;
            case "3":
                App.shutdown();
                break;
            default:
                System.out.println("Invalid input. Try again.");
        }
    }
}
