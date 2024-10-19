package ru.ylab.services.entities;

/**
 * Interface describing authorization logic.
 *
 * @author azatyamanaev
 */
public interface AuthService {

    /**
     * Handles user sign in depending on user input.
     */
    void signIn();

    /**
     * Handles user sign up depending on user input.
     */
    void signUp();
}
