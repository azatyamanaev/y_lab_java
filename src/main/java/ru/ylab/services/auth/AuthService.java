package ru.ylab.services.auth;

import ru.ylab.dto.in.SignInForm;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.out.SignInResult;

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
