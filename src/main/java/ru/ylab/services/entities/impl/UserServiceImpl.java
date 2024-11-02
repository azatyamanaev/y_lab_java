package ru.ylab.services.entities.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.in.UserForm;
import ru.ylab.dto.in.UserSearchForm;
import ru.ylab.exception.HttpException;
import ru.ylab.models.User;
import ru.ylab.repositories.UserRepository;
import ru.ylab.services.auth.PasswordService;
import ru.ylab.services.entities.UserService;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Service implementing {@link UserService}.
 *
 * @author azatyamanaev
 */
@RequiredArgsConstructor
@Service("userService")
public class UserServiceImpl implements UserService {

    /**
     * Instance of an {@link UserRepository}.
     */
    private final UserRepository userRepository;

    /**
     * Instance of an {@link PasswordService}.
     */
    private final PasswordService passwordService;

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
        return userRepository.search(form);
    }

    @Override
    public void createByAdmin(UserForm form) {
        User user = User.builder()
                        .name(form.getName())
                        .email(form.getEmail())
                        .password(passwordService.hashPassword(form.getPassword()))
                        .role(form.getRole())
                        .build();

        userRepository.save(user);
    }

    @Override
    public void update(Long userId, SignUpForm form) {
        userRepository.update(userId, form);
    }

    @Override
    public void delete(Long userId) {
        userRepository.delete(userId);
    }
}
