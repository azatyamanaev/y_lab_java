package ru.ylab.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import ru.ylab.forms.UserSearchForm;
import ru.ylab.models.User;
import ru.ylab.repositories.Storage;
import ru.ylab.repositories.UserRepository;

/**
 * Class implementing {@link UserRepository}.
 *
 * @author azatyamanaev
 */
public class UserRepositoryImpl implements UserRepository {

    /**
     * Instance of a {@link Storage}
     */
    private final Storage storage;

    /**
     * Creates new UserRepositoryImpl.
     *
     * @param storage Storage instance
     */
    public UserRepositoryImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return storage.getUsers().values().stream()
                      .filter(x -> x.getEmail().equals(email))
                      .findAny();
    }

    @Override
    public User getByEmail(String email) {
        return findByEmail(email).orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(storage.getUsers().values());
    }

    @Override
    public List<User> search(@NotNull UserSearchForm form) {
        return storage.getUsers().values().stream()
                      .filter(getPredicates(form))
                      .collect(Collectors.toList());
    }

    @Override
    public User save(User user) {
        storage.getUsers().put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        storage.getUsers().put(user.getId(), user);
        return user;
    }

    @Override
    public boolean deleteByEmail(String email) {
        User user = getByEmail(email);
        if (user == null) {
            return false;
        } else {
            return storage.getUsers().remove(user.getId()) != null;
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

        if (form.getEmail() != null && !form.getEmail().isBlank()) {
            predicate = predicate.and(x -> x.getEmail().startsWith(form.getEmail()));
        }

        if (form.getRole() != null) {
            predicate = predicate.and(x -> x.getRole().equals(User.Role.valueOf(form.getRole())));
        }

        return predicate;
    }
}
