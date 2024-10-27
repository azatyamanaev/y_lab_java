package ru.ylab.repositories;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.in.UserSearchForm;
import ru.ylab.models.User;

/**
 * Interface describing logic for working with user storage.
 *
 * @author azatyamanaev
 */
public interface UserRepository {

    /**
     * Finds user by id.
     *
     * @param id user id
     * @return {@code Optional<User>}
     */
    Optional<User> find(Long id);

    /**
     * Finds user by email.
     *
     * @param email user email
     * @return {@code Optional<User>}
     */
    Optional<User> findByEmail(String email);

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
     * @return whether save is successful
     */
    boolean save(User user);

    /**
     * Update user in storage.
     *
     * @param userId user id
     * @param form user data
     * @return whether update is successful
     */
    boolean update(Long userId, SignUpForm form);

    /**
     * Deletes user from storage by id.
     *
     * @param userId user id
     * @return whether deletion is successful
     */
    boolean delete(Long userId);
}
