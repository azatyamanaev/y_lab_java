package ru.ylab.core.services.entities;

import java.util.List;

import ru.ylab.core.dto.in.SignUpForm;
import ru.ylab.core.dto.in.UserForm;
import ru.ylab.core.dto.in.UserSearchForm;
import ru.ylab.core.exception.HttpException;
import ru.ylab.core.models.User;

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
     * @return user
     * @throws HttpException if user does not exist
     */
    User get(Long id) throws HttpException;

    /**
     * Gets user by email.
     *
     * @param email user email
     * @return user
     * @throws HttpException if user does not exist
     */
    User getByEmail(String email) throws HttpException;

    /**
     * Saves user to storage.
     *
     * @param user user data
     */
    void save(User user);

    /**
     * Gets all users.
     *
     * @return list of all users
     */
    List<User> getAll();

    /**
     * Search users with filters.
     *
     * @param form filters to apply
     * @return list of users
     */
    List<User> searchUsers(UserSearchForm form);

    /**
     * Creates user according to user input.
     *
     * @param form user data
     */
    void createByAdmin(UserForm form);

    /**
     * Edits user data.
     *
     * @param userId user id
     * @param form user data
     */
    void update(Long userId, SignUpForm form);

    /**
     * Deletes user.
     *
     * @param userId user id
     */
    void delete(Long userId);
}
