package ru.ylab.services.auth.impl;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.dto.in.SignInForm;
import ru.ylab.dto.in.UserForm;
import ru.ylab.dto.out.SignInResult;
import ru.ylab.models.User;
import ru.ylab.services.auth.AuthService;
import ru.ylab.services.auth.JwtService;
import ru.ylab.services.auth.PasswordService;
import ru.ylab.services.entities.UserService;

/**
 * Service implementing {@link AuthService}.
 *
 * @author azatyamanaev
 */
@Slf4j
public class AuthServiceImpl implements AuthService {

    /**
     * Instance of a {@link PasswordService}.
     */
    private final PasswordService passwordService;

    /**
     * Instance of an {@link UserService}.
     */
    private final UserService userService;

    /**
     * Instance of a {@link JwtService}
     */
    private final JwtService jwtService;

    /**
     * Creates new AuthServiceImpl.
     *
     * @param passwordService PasswordService instance
     * @param userService UserService instance
     */
    public AuthServiceImpl(PasswordService passwordService, UserService userService,
                           JwtService jwtService) {
        this.passwordService = passwordService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public SignInResult signIn(SignInForm form) {
        User user = userService.getByEmail(form.getEmail());
        SignInResult result;
        if (user == null) {
            log.warn("User with email {} not found", form.getEmail());
            return null;
        }

        if (passwordService.verifyPassword(form.getPassword(), user.getPassword())) {
            return jwtService.createToken(user);
        } else {
            log.warn("Incorrect password");
            return null;
        }
    }

    @Override
    public SignInResult signUp(UserForm form) {
        User user;
        SignInResult result = null;
        if (userService.existsByEmail(form.getEmail())) {
            log.warn("User with email {} already exists.", "email");
        } else {
            user = new User();
            user.setName(form.getName());
            user.setEmail(form.getEmail());
            user.setPassword(passwordService.hashPassword(form.getPassword()));
            user.setRole(User.Role.USER);
            userService.save(user);
            user = userService.getByEmail(form.getEmail());

            result = jwtService.createToken(user);
            log.info("User {} signed up!", user.getEmail());
        }
        return result;
    }
}
