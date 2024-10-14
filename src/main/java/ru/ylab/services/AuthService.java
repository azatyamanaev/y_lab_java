package ru.ylab.services;

import java.util.Scanner;

/**
 * Interface describing authorization logic.
 *
 * @author azatyamanaev
 */
public interface AuthService {

    /**
     * Handles user sign in depending on user input.
     *
     * @param scanner scanner for reading user input
     */
    void signIn(Scanner scanner);

    /**
     * Handles user sign up depending on user input.
     *
     * @param scanner scanner for reading user input
     */
    void signUp(Scanner scanner);
}
