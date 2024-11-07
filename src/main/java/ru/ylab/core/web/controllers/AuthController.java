package ru.ylab.core.web.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ylab.core.dto.in.SignInForm;
import ru.ylab.core.dto.in.SignUpForm;
import ru.ylab.core.dto.out.SignInResult;
import ru.ylab.core.services.auth.AuthService;
import ru.ylab.core.services.auth.JwtService;

import static ru.ylab.core.utils.constants.WebConstants.AUTH_URL;
import static ru.ylab.core.utils.constants.WebConstants.REFRESH_TOKEN_URL;
import static ru.ylab.core.utils.constants.WebConstants.SIGN_IN_URL;
import static ru.ylab.core.utils.constants.WebConstants.SIGN_UP_URL;

/**
 * Controller for handling authorization HTTP requests.
 *
 * @author azatyamanaev
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(AUTH_URL)
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    /**
     * Gets new access token for user and writes it to response.
     */
    @GetMapping(REFRESH_TOKEN_URL)
    public ResponseEntity<String> refreshToken(@RequestParam("token") String token) {
        return ResponseEntity.ok(jwtService.generateAccess(token));
    }

    /**
     * Signs in user and writes access and refresh tokens to response.
     */
    @PostMapping(SIGN_IN_URL)
    public ResponseEntity<SignInResult> signIn(@Valid @RequestBody SignInForm form) {
        SignInResult signInResult = authService.signIn(form);
        return ResponseEntity.ok(signInResult);
    }

    /**
     * Signs up user and writes access and refresh tokens to response.
     */
    @PostMapping(SIGN_UP_URL)
    public ResponseEntity<SignInResult> signUp(@Valid @RequestBody SignUpForm form) {
        SignInResult signInResult = authService.signUp(form);
        return ResponseEntity.ok(signInResult);
    }
}
