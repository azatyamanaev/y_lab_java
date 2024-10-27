package ru.ylab.services.auth.impl;

import ru.ylab.dto.in.SignInForm;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.out.SignInResult;
import ru.ylab.exception.HttpException;
import ru.ylab.models.User;
import ru.ylab.services.auth.AuthService;
import ru.ylab.services.auth.JwtService;
import ru.ylab.services.auth.PasswordService;
import ru.ylab.services.entities.UserService;
import ru.ylab.services.validation.Validator;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Service implementing {@link AuthService}.
 *
 * @author azatyamanaev
 */
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
     * Instance of a {@link Validator<SignInForm>}.
     */
    private final Validator<SignInForm> signInValidator;

    /**
     * Instance of a {@link Validator< SignUpForm >}.
     */
    private final Validator<SignUpForm> signUpValidator;

    /**
     * Creates new AuthServiceImpl.
     *
     * @param passwordService PasswordService instance
     * @param userService     UserService instance
     * @param signUpValidator Validator<SignInForm> instance
     * @param signInValidator Validator<SignUpForm> instance
     */
    public AuthServiceImpl(PasswordService passwordService, UserService userService,
                           JwtService jwtService, Validator<SignInForm> signInValidator,
                           Validator<SignUpForm> signUpValidator) {
        this.passwordService = passwordService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.signInValidator = signInValidator;
        this.signUpValidator = signUpValidator;
    }

    @Override
    public SignInResult signIn(SignInForm form) {
        signInValidator.validate(form);

        User user = userService.getByEmail(form.getEmail());

        if (passwordService.verifyPassword(form.getPassword(), user.getPassword())) {
            return jwtService.createToken(user);
        } else {
            throw HttpException.badRequest().addDetail(ErrorConstants.INVALID_PARAMETER, "password");
        }
    }

    @Override
    public SignInResult signUp(SignUpForm form) {
        signUpValidator.validate(form);

        User user = User.builder()
                        .name(form.getName())
                        .email(form.getEmail())
                        .role(User.Role.USER)
                        .password(passwordService.hashPassword(form.getPassword()))
                        .build();

        userService.save(user);
        user = userService.getByEmail(form.getEmail());
        return jwtService.createToken(user);
    }
}
