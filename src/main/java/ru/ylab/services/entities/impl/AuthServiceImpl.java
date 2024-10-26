package ru.ylab.services.entities.impl;

import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.App;
import ru.ylab.forms.SignInForm;
import ru.ylab.forms.UserForm;
import ru.ylab.handlers.Page;
import ru.ylab.models.User;
import ru.ylab.services.entities.AuthService;
import ru.ylab.services.entities.UserService;
import ru.ylab.utils.RegexMatcher;

/**
 * Service implementing {@link AuthService}.
 *
 * @author azatyamanaev
 */
@Slf4j
public class AuthServiceImpl implements AuthService {

    /**
     * Scanner for reading user input.
     */
    private final Scanner scanner;

    /**
     * Instance of an {@link UserService}.
     */
    private final UserService userService;

    /**
     * Creates new AuthServiceImpl.
     *
     * @param scanner     scanner for reading user input
     * @param userService UserService instance
     */
    public AuthServiceImpl(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    @Override
    public void signIn() {
        SignInForm form = new SignInForm();
        System.out.print("Enter email: ");
        String email = scanner.next();
        while (!RegexMatcher.matchEmail(email)) {
            System.out.println("Invalid email format. Email must consist of english letters and numbers and end in @mail.ru");
            System.out.print("Enter email: ");
            email = scanner.next();
        }
        form.setEmail(email);

        System.out.print("Enter password: ");
        form.setPassword(scanner.next());

        User user = userService.getByEmail(form.getEmail());
        if (user != null && !user.getPassword().equals(form.getPassword())) {
            user = null;
        }

        if (user != null) {
            log.info("User {} signed in!", user.getEmail());
            App.setCurrentUser(user);
            if (user.getRole().equals(User.Role.USER)) {
                App.redirect(Page.AUTHORIZED_USER_PAGE);
            } else {
                App.redirect(Page.ADMIN_PANEL_PAGE);
            }
        } else {
            log.warn("Incorrect email or password.");
        }
    }

    @Override
    public void signUp() {
        UserForm form = new UserForm();

        System.out.print("Enter email: ");
        String email = scanner.next();
        while (!RegexMatcher.matchEmail(email)) {
            System.out.println("Invalid email format. Email must consist of english letters and numbers and end in @mail.ru.");
            System.out.print("Enter email: ");
            email = scanner.next();
        }
        form.setEmail(email);

        System.out.print("Enter name: ");
        form.setName(scanner.next());

        System.out.print("Enter password: ");
        form.setPassword(scanner.next());

        User user;
        if (userService.existsByEmail(form.getEmail())) {
            user = null;
        } else {
            user = new User();
            user.setEmail(form.getEmail());
            user.setName(form.getName());
            user.setPassword(form.getPassword());
            user.setRole(User.Role.USER);
            userService.save(user);
        }

        if (user != null) {
            log.info("User {} signed up!", user.getEmail());
            App.setCurrentUser(user);
            App.redirect(Page.AUTHORIZED_USER_PAGE);
        } else {
            log.warn("User with email {} already exists.", email);
        }
    }
}
