package ru.ylab.handlers;

import java.util.Scanner;

import ru.ylab.App;
import ru.ylab.services.entities.AuthService;

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
     * @param scanner     scanner for reading user input
     * @param authService AuthService instance
     */
    public AuthHandler(Scanner scanner, AuthService authService) {
        super(scanner);
        this.authService = authService;
    }

    @Override
    public void handleInput() {
        switch (scanner.next()) {
            case "1":
                authService.signIn();
                waitForInput();
                break;
            case "2":
                authService.signUp();
                waitForInput();
                break;
            case "3":
                App.shutdown();
                break;
            default:
                System.out.println("Invalid input. Try again.");
                waitForInput();
        }
    }
}
