package ru.ylab.services.entities.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.dto.in.UserForm;
import ru.ylab.models.User;
import ru.ylab.repositories.UserRepository;
import ru.ylab.services.entities.UserService;

/**
 * Service implementing {@link UserService}.
 *
 * @author azatyamanaev
 */
@Slf4j
public class UserServiceImpl implements UserService {

    /**
     * Instance of an {@link UserRepository}.
     */
    private final UserRepository userRepository;

    /**
     * Creates new UserServiceImpl.
     *
     * @param userRepository UserRepository instance
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User get(Long id) {
        return userRepository.get(id);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public String getUsers() {
        return "";
    }

    @Override
    public void createByAdmin() {
        UserForm form = new UserForm();

        User.Role role = User.Role.ADMIN;

        User user = new User();
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setRole(role);

        userRepository.save(user);
        log.info("User created.");
    }

    @Override
    public void deleteByAdmin() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
