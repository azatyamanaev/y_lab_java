package ru.ylab.services.entities.impl;

import java.util.List;

import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.in.UserForm;
import ru.ylab.dto.in.UserSearchForm;
import ru.ylab.exception.HttpException;
import ru.ylab.models.User;
import ru.ylab.repositories.UserRepository;
import ru.ylab.services.auth.PasswordService;
import ru.ylab.services.entities.UserService;
import ru.ylab.services.validation.Validator;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Service implementing {@link UserService}.
 *
 * @author azatyamanaev
 */
public class UserServiceImpl implements UserService {

    /**
     * Instance of an {@link UserRepository}.
     */
    private final UserRepository userRepository;

    /**
     * Instance of an {@link PasswordService}.
     */
    private final PasswordService passwordService;

    /**
     * Instance of an {@link Validator<SignUpForm>}.
     */
    private final Validator<SignUpForm> userFormValidator;

    /**
     * Instance of an {@link Validator<UserSearchForm>}.
     */
    private final Validator<UserSearchForm> userSearchFormValidator;

    /**
     * Creates new UserServiceImpl.
     *
     * @param userRepository  UserRepository instance
     * @param passwordService PasswordService instance
     * @param userFormValidator Validator<SignUpForm> instance
     * @param userSearchFormValidator Validator<UserSearchForm> instance
     */
    public UserServiceImpl(UserRepository userRepository, PasswordService passwordService,
                           Validator<SignUpForm> userFormValidator, Validator<UserSearchForm> userSearchFormValidator) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.userFormValidator = userFormValidator;
        this.userSearchFormValidator = userSearchFormValidator;
    }

    @Override
    public User get(Long id) {
        return userRepository.find(id)
                             .orElseThrow(() -> HttpException.badRequest().addDetail(ErrorConstants.NOT_FOUND, "user"));
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                             .orElseThrow(() -> HttpException.badRequest().addDetail(ErrorConstants.NOT_FOUND, "user"));
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public List<User> searchUsers(UserSearchForm form) {
        userSearchFormValidator.validate(form);
        return userRepository.search(form);
    }

    @Override
    public void createByAdmin(UserForm form) {
        userFormValidator.validate(form);
        User user = User.builder()
                        .name(form.getName())
                        .email(form.getEmail())
                        .password(passwordService.hashPassword(form.getPassword()))
                        .role(User.Role.valueOf(form.getRole()))
                        .build();

        userRepository.save(user);
    }

    @Override
    public void update(Long userId, SignUpForm form) {
        userFormValidator.validate(form);
        userRepository.update(userId, form);
    }

    @Override
    public void delete(Long userId) {
        userRepository.delete(userId);
    }
}
