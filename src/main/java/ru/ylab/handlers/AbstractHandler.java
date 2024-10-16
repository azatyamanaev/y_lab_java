package ru.ylab.handlers;

import java.util.Scanner;

import ru.ylab.models.User;

/**
 * Class for rendering page and handling user inputs.
 *
 * @author azatyamanaev
 */
public abstract class AbstractHandler {

    /**
     * Scanner for reading user input.
     */
    protected final Scanner scanner;

    /**
     * Creates new AbstractHandler
     *
     * @param scanner scanner for reading user input
     */
    public AbstractHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Draws page in console.
     *
     * @param user    current user
     * @param options options on current page
     */
    public void drawPage(User user, String options) {
        if (user == null) {
            System.out.println("You are unauthorized.");
        } else {
            System.out.println("You are authorized as " + user.getName() + ".");
        }
        System.out.println("OPTIONS");
        System.out.println(options);
        System.out.print("Enter your choice: ");
    }

    /**
     * Handles user input according to options field of enum {@link Page}.
     */
    public abstract void handleInput();
}
