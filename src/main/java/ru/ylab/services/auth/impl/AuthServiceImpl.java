package ru.ylab.services.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ylab.dto.in.SignInForm;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.out.SignInResult;
import ru.ylab.exception.HttpException;
import ru.ylab.models.User;
import ru.ylab.services.auth.AuthService;
import ru.ylab.services.auth.JwtService;
import ru.ylab.services.auth.PasswordService;
import ru.ylab.services.entities.UserService;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Service implementing {@link AuthService}.
 *
 * @author azatyamanaev
 */
@RequiredArgsConstructor
@Service
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

    @Override
    public SignInResult signIn(SignInForm form) {
        User user = userService.getByEmail(form.getEmail());

        if (passwordService.verifyPassword(form.getPassword(), user.getPassword())) {
            return jwtService.createToken(user);
        } else {
            throw HttpException.badRequest().addDetail(ErrorConstants.INVALID_PARAMETER, "password");
        }
    }

    @Override
    public SignInResult signUp(SignUpForm form) {
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
