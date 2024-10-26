package ru.ylab.repositories;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import ru.ylab.dto.in.UserSearchForm;
import ru.ylab.models.User;

/**
 * Interface describing logic for working with user storage.
 *
 * @author azatyamanaev
 */
public interface UserRepository {

    /**
     * Gets user by id.
     *
     * @param id user id
     * @return user or null
     */
    User get(Long id);

    /**
     * Finds user by name.
     *
     * @param email user email
     * @return {@code Optional<User>}
     */
    Optional<User> findByEmail(String email);

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
     * Gets all users.
     *
     * @return list of all users
     */
    List<User> getAll();

    /**
     * Searches users with specified filters.
     *
     * @param form filters to apply
     * @return list of users
     */
    List<User> search(@NotNull UserSearchForm form);

    /**
     * Saves user to storage.
     *
     * @param user instance of User to save
     * @return saved user
     */
    User save(User user);

    /**
     * Update user in storage.
     *
     * @param user instance of User to update
     * @return updated user
     */
    User update(User user);

    /**
     * Deletes user from storage by email.
     *
     * @param email user email
     * @return whether deletion is successful
     */
    boolean deleteByEmail(String email);
}
