package ru.ylab.services;

import java.util.Scanner;

/**
 * Interface describing logic for working with users.
 *
 * @author azatyamanaev
 */
public interface UserService {

    /**
     * Gets users with filtering depending on user input.
     *
     * @param scanner scanner for reading user input
     */
    void getUsers(Scanner scanner);

    /**
     * Creates user according to user input.
     *
     * @param scanner scanner for reading user input
     */
    void createByAdmin(Scanner scanner);

    /**
     * Deletes user according to user input.
     *
     * @param scanner scanner for reading user input
     */
    void deleteByAdmin(Scanner scanner);

    /**
     * Edits user data according to user input.
     *
     * @param scanner scanner for reading user input
     */
    void update(Scanner scanner);

    /**
     * Deletes user account or not depending on user input.
     * If user choose to delete account, redirects to auth page.
     *
     * @param scanner scanner for reading user input
     */
    void delete(Scanner scanner);
}
