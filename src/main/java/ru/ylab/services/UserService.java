package ru.ylab.services;

import java.util.Scanner;

import ru.ylab.models.User;

/**
 * Interface describing logic for working with users.
 *
 * @author azatyamanaev
 */
public interface UserService {

    /**
     * Gets user by email.
     *
     * @param email user email
     * @return user or null
     */
    User getByEmail(String email);

    /**
     * Checks whether user exists by email.
     *
     * @param email user email
     * @return whether user exists
     */
    boolean existsByEmail(String email);

    /**
     * Saves user to storage.
     *
     * @param user user data
     */
    User save(User user);

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
