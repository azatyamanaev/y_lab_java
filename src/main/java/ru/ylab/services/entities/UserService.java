package ru.ylab.services.entities;

import ru.ylab.models.User;

/**
 * Interface describing logic for working with users.
 *
 * @author azatyamanaev
 */
public interface UserService {

    /**
     * Gets user by id.
     *
     * @param id user id
     * @return user or null
     */
    User get(Long id);

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
     * @return saved user
     */
    User save(User user);

    /**
     * Gets users with filtering.
     *
     * @return list of users in string format
     */
    String getUsers();

    /**
     * Creates user according to user input.
     */
    void createByAdmin();

    /**
     * Deletes user according to user input.
     */
    void deleteByAdmin();

    /**
     * Edits user data according to user input.
     */
    void update();

    /**
     * Deletes user account or not depending on user input.
     * If user choose to delete account, redirects to auth page.
     */
    void delete();
}
