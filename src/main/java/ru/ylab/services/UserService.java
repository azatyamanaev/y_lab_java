package ru.ylab.services;

import java.util.List;
import java.util.Scanner;

import ru.ylab.App;
import ru.ylab.handlers.Page;
import ru.ylab.forms.UserForm;
import ru.ylab.forms.UserSearchForm;
import ru.ylab.models.User;
import ru.ylab.repositories.UserRepository;
import ru.ylab.utils.IdUtil;
import ru.ylab.utils.InputParser;
import ru.ylab.utils.RegexMatcher;

/**
 * Service implementing CRUD logic for working with users.
 *
 * @author azatyamanaev
 */
public class UserService {

    /**
     * Instance of an {@link UserRepository}.
     */
    private final UserRepository userRepository;

    /**
     * Creates new UserService.
     *
     * @param userRepository UserRepository instance
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Gets users with filtering depending on user input.
     *
     * @param scanner scanner for reading user input
     */
    public void getUsers(Scanner scanner) {
        System.out.println("Do you want to use filters?(y/n)");
        String in = scanner.next();

        while (!"y".equals(in) && !"n".equals(in)) {
            System.out.print("Invalid input. Please write 'y' or 'n': ");
            in = scanner.next();
        }

        List<User> users;
        if ("y".equals(in)) {
            users = userRepository.search(getUserFilters(scanner));
        } else {
            users = userRepository.getAll();
        }

        for (User user : users) {
            System.out.println(user);
        }
        InputParser.parseCKey(scanner);
    }

    /**
     * Creates user according to user input.
     *
     * @param scanner scanner for reading user input
     */
    public void createByAdmin(Scanner scanner) {
        UserForm form = new UserForm();
        System.out.print("Enter email: ");
        String email = scanner.next();
        while (!RegexMatcher.matchEmail(email)) {
            System.out.println("Invalid email format. Email must consist of english letters and numbers and end in @mail.ru.");
            System.out.print("Enter email: ");
            email = scanner.next();
        }
        if (userRepository.existsByEmail(email)) {
            System.out.println("User with email " + email + " already exists.");
            return;
        }
        form.setEmail(email);

        System.out.print("Enter name: ");
        form.setName(scanner.next());

        System.out.print("Enter password: ");
        form.setPassword(scanner.next());

        System.out.print("Enter role(1 - USER, 2 - ADMIN): ");
        String role = InputParser.parseRole(scanner);

        User user = new User(
                IdUtil.generateUserId(),
                form.getName(),
                form.getEmail(),
                form.getPassword(),
                User.Role.valueOf(role));

        userRepository.save(user);
        System.out.println("User created.");
        InputParser.parseCKey(scanner);
    }

    /**
     * Deletes user according to user input.
     *
     * @param scanner scanner for reading user input
     */
    public void deleteByAdmin(Scanner scanner) {
        System.out.print("Enter email of user to delete: ");
        String email = scanner.next();
        while (!RegexMatcher.matchEmail(email)) {
            System.out.println("Invalid email format. Email must consist of english letters and numbers and end in @mail.ru.");
            System.out.print("Enter email of user to delete: ");
            email = scanner.next();
        }
        if (!userRepository.existsByEmail(email)) {
            System.out.println("User with email " + email + " not found.");
        } else {
            userRepository.deleteByEmail(email);
            System.out.println("User deleted.");
        }
        InputParser.parseCKey(scanner);
    }

    /**
     * Edits user data according to user input.
     *
     * @param scanner scanner for reading user input
     */
    public void update(Scanner scanner) {
        UserForm form = new UserForm();
        System.out.print("Enter email: ");
        form.setEmail(scanner.next());
        System.out.print("Enter name: ");
        form.setName(scanner.next());
        System.out.print("Enter password: ");
        form.setPassword(scanner.next());

        User user = userRepository.getByEmail(App.getCurrentUser().getEmail());

        user.setName(form.getName());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        userRepository.update(user);

        App.setCurrentUser(user);
        System.out.println("Account updated");
        InputParser.parseCKey(scanner);
    }

    /**
     * Deletes user account or not depending on user input.
     * If user choose to delete account, redirects to auth page.
     *
     * @param scanner scanner for reading user input
     */
    public void delete(Scanner scanner) {
        System.out.println("Are you sure you want to delete your account?(y/n)");
        String in = scanner.next();

        while (!"y".equals(in) && !"n".equals(in)) {
            System.out.print("Invalid input. Please write 'y' or 'n': ");
            in = scanner.next();
        }
        if ("y".equals(in)) {
            userRepository.deleteByEmail(App.getCurrentUser().getEmail());
            App.setCurrentUser(null);
            App.redirect(Page.AUTH_PAGE);
        }
    }

    /**
     * Forms instance of {@link UserSearchForm} according to user input.
     *
     * @param scanner scanner for reading user input
     * @return created instance of a UserSearchForm
     */
    private UserSearchForm getUserFilters(Scanner scanner) {
        UserSearchForm form = new UserSearchForm();
        System.out.print("Enter name: ");
        form.setName(scanner.next());

        System.out.print("Enter email: ");
        form.setEmail(scanner.next());

        System.out.print("Enter role(1 - USER, 2 - ADMIN): ");
        form.setRole(InputParser.parseRole(scanner));

        return form;
    }
}
