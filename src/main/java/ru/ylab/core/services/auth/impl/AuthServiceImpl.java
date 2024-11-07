package ru.ylab.core.services.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ylab.core.dto.in.SignInForm;
import ru.ylab.core.dto.in.SignUpForm;
import ru.ylab.core.dto.out.SignInResult;
import ru.ylab.core.exception.HttpException;
import ru.ylab.core.models.User;
import ru.ylab.core.services.auth.AuthService;
import ru.ylab.core.services.auth.JwtService;
import ru.ylab.core.services.auth.PasswordService;
import ru.ylab.core.services.entities.UserService;
import ru.ylab.core.utils.constants.ErrorConstants;

/**
 * Service implementing {@link AuthService}.
 *
 * @author azatyamanaev
 */
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordService passwordService;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public SignInResult signIn(SignInForm form) {
        User user = userService.getByEmail(form.getEmail());

        if (passwordService.verifyPassword(form.getPassword(), user.getPassword())) {
            return jwtService.createToken(user);
        } else {
            throw HttpException.badRequest().addDetail(ErrorConstants.INVALID_PARAM, "password");
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
