package ru.ylab.services.impl;

import java.util.List;
import java.util.Scanner;

import ru.ylab.App;
import ru.ylab.handlers.Page;
import ru.ylab.forms.UserForm;
import ru.ylab.forms.UserSearchForm;
import ru.ylab.models.User;
import ru.ylab.repositories.UserRepository;
import ru.ylab.services.UserService;
import ru.ylab.utils.IdUtil;
import ru.ylab.utils.InputParser;
import ru.ylab.utils.RegexMatcher;

/**
 * Service implementing {@link UserService}.
 *
 * @author azatyamanaev
 */
public class UserServiceImpl implements UserService {

    /**
     * Scanner for reading user input.
     */
    private final Scanner scanner;

    /**
     * Instance of an {@link UserRepository}.
     */
    private final UserRepository userRepository;

    /**
     * Creates new UserServiceImpl.
     *
     * @param scanner        scanner for reading user input
     * @param userRepository UserRepository instance
     */
    public UserServiceImpl(Scanner scanner, UserRepository userRepository) {
        this.scanner = scanner;
        this.userRepository = userRepository;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public String getUsers() {
        System.out.println("Do you want to use filters?(y/n)");
        String in = scanner.next();

        while (!"y".equals(in) && !"n".equals(in)) {
            System.out.print("Invalid input. Please write 'y' or 'n': ");
            in = scanner.next();
        }

        List<User> users;
        if ("y".equals(in)) {
            users = userRepository.search(getUserFilters());
        } else {
            users = userRepository.getAll();
        }

        StringBuilder response = new StringBuilder();

        for (User user : users) {
            response.append(user).append("\n");
        }
        return response.toString();
    }

    @Override
    public void createByAdmin() {
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
    }

    @Override
    public void deleteByAdmin() {
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
    }

    @Override
    public void update() {
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
    }

    @Override
    public void delete() {
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
            System.out.println("Account deleted");
        }
    }

    /**
     * Forms instance of {@link UserSearchForm} according to user input.
     *
     * @return created instance of a UserSearchForm
     */
    private UserSearchForm getUserFilters() {
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
