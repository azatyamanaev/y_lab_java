package ru.ylab.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import ru.ylab.forms.UserSearchForm;
import ru.ylab.models.User;

/**
 * Class for working with user storage.
 *
 * @author azatyamanaev
 */
public class UserRepository {

    /**
     * Map for storing users data.
     */
    private static final Map<Long, User> users = new HashMap<>();

    /**
     * Finds user by name.
     *
     * @param email user email
     * @return {@code Optional<User>}
     */
    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                    .filter(x -> x.getEmail().equals(email))
                    .findAny();
    }

    /**
     * Gets user by email.
     *
     * @param email user email
     * @return user or null
     */
    public User getByEmail(String email) {
        return findByEmail(email).orElse(null);
    }

    /**
     * Checks whether user exists by email.
     *
     * @param email user email
     * @return whether user exists
     */
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    /**
     * Gets all users.
     *
     * @return list of all users
     */
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    /**
     * Searches users with specified filters.
     *
     * @param form filters to apply
     * @return list of users
     */
    public List<User> search(@NotNull UserSearchForm form) {
        return users.values().stream()
                    .filter(getPredicates(form))
                    .collect(Collectors.toList());
    }

    /**
     * Saves user to storage.
     *
     * @param user instance of User to save
     * @return saved user
     */
    public User save(User user) {
        users.put(user.getId(), user);
        return user;
    }

    /**
     * Update user in storage.
     *
     * @param user instance of User to update
     * @return updated user
     */
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    /**
     * Deletes user from storage by email.
     *
     * @param email user email
     * @return whether deletion is successful
     */
    public boolean deleteByEmail(String email) {
        User user = getByEmail(email);
        if (user == null) {
            return false;
        } else {
            return users.remove(user.getId()) != null;
        }
    }

    /**
     * Forms predicate according to passed UserSearchForm.
     *
     * @param form form to create predicate from
     * @return created instance of a Predicate
     */
    private Predicate<User> getPredicates(@NotNull UserSearchForm form) {
        Predicate<User> predicate = (x) -> true;

        if (form.getName() != null && !form.getName().isBlank()) {
            predicate = predicate.and(x -> x.getName().startsWith(form.getName()));
        }

        if (form.getName() != null && !form.getEmail().isBlank()) {
            predicate = predicate.and(x -> x.getEmail().startsWith(form.getEmail()));
        }

        if (form.getRole() != null) {
            predicate = predicate.and(x -> x.getRole().equals(User.Role.valueOf(form.getRole())));
        }

        return predicate;
    }
}
