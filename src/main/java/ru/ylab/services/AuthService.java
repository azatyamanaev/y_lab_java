package ru.ylab.services;

import java.util.Scanner;

import ru.ylab.App;
import ru.ylab.handlers.Page;
import ru.ylab.forms.SignInForm;
import ru.ylab.forms.UserForm;
import ru.ylab.models.User;
import ru.ylab.repositories.UserRepository;
import ru.ylab.utils.IdUtil;
import ru.ylab.utils.RegexMatcher;

/**
 * Service implementing authorization logic.
 *
 * @author azatyamanaev
 */
public class AuthService {

    /**
     * Instance of an {@link UserRepository}.
     */
    private final UserRepository userRepository;

    /**
     * Creates new AuthService.
     *
     * @param userRepository UserRepository instance
     */
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Handles user sign in depending on user input.
     *
     * @param scanner scanner for reading user input
     */
    public void signIn(Scanner scanner) {
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

        User user = userRepository.getByEmail(form.getEmail());
        if (user != null && !user.getPassword().equals(form.getPassword())) {
            user = null;
        }

        if (user != null) {
            System.out.println("You are signed in!");
            App.setCurrentUser(user);
            if (user.getRole().equals(User.Role.USER)) {
                App.redirect(Page.AUTHORIZED_USER_PAGE);
            } else {
                App.redirect(Page.ADMIN_PANEL_PAGE);
            }
        } else {
            System.out.println("Incorrect email or password. Try again.");
        }
    }

    /**
     * Handles user sign up depending on user input.
     *
     * @param scanner scanner for reading user input
     */
    public void signUp(Scanner scanner) {
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
        if (userRepository.existsByEmail(form.getEmail())) {
            user = null;
        } else {
            user = new User(
                    IdUtil.generateUserId(),
                    form.getName(),
                    form.getEmail(),
                    form.getPassword(),
                    User.Role.USER);
            userRepository.save(user);
        }

        if (user != null) {
            System.out.println("You are signed up!");
            App.setCurrentUser(user);
            App.redirect(Page.AUTHORIZED_USER_PAGE);
        } else {
            System.out.println("User with email " + email + " already exists. Try again.");
        }
    }
}
