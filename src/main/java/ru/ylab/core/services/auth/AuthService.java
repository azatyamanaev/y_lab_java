package ru.ylab.core.services.auth;

import ru.ylab.core.dto.in.SignInForm;
import ru.ylab.core.dto.in.SignUpForm;
import ru.ylab.core.dto.out.SignInResult;

/**
 * Interface describing authorization logic.
 *
 * @author azatyamanaev
 */
public interface AuthService {

    /**
     * Handles user sign in.
     *
     * @param form sign in data
     * @return access and refresh tokens
     */
    SignInResult signIn(SignInForm form);

    /**
     * Handles user sign up.
     *
     * @param form user data
     * @return access and refresh tokens
     */
    SignInResult signUp(SignUpForm form);
}
