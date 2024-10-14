package ru.ylab.services;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.ylab.App;
import ru.ylab.handlers.Page;
import ru.ylab.models.User;
import ru.ylab.repositories.UserRepository;
import ru.ylab.services.impl.AuthServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository = mock(UserRepository.class);
    private AuthService authService = new AuthServiceImpl(userRepository);
    private User user;


    @BeforeEach
    public void setUp() {
        user = new User(
                1L,
                "user1",
                "a@mail.ru",
                "pass",
                User.Role.USER);
    }

    @Test
    public void testSignInUser() {
        when(userRepository.getByEmail("a@mail.ru")).thenReturn(user);
        Scanner scanner = new Scanner(new ByteArrayInputStream("a@mail.ru\npass".getBytes(StandardCharsets.UTF_8)));

        authService.signIn(scanner);
        Assertions.assertNotNull(App.getCurrentUser());
        Assertions.assertEquals("user1", App.getCurrentUser().getName());
        Assertions.assertEquals(Page.AUTHORIZED_USER_PAGE, App.getPage());
    }

    @Test
    public void testSignInFailWrongEmail() {
        when(userRepository.getByEmail("a@mail.ru")).thenReturn(user);
        Scanner scanner = new Scanner(new ByteArrayInputStream("a1@mail.ru\npass".getBytes(StandardCharsets.UTF_8)));

        authService.signIn(scanner);
        Assertions.assertNull(App.getCurrentUser());
    }

    @Test
    public void testSignInFailWrongPassword() {
        when(userRepository.getByEmail("a@mail.ru")).thenReturn(user);
        Scanner scanner = new Scanner(new ByteArrayInputStream("a@mail.ru\npass1".getBytes(StandardCharsets.UTF_8)));

        authService.signIn(scanner);
        Assertions.assertNull(App.getCurrentUser());
    }

    @Test
    public void testSignUp() {
        when(userRepository.existsByEmail("a@mail.ru")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        Scanner scanner = new Scanner(new ByteArrayInputStream("a@mail.ru\nuser1\npass1".getBytes(StandardCharsets.UTF_8)));

        authService.signUp(scanner);
        Assertions.assertNotNull(App.getCurrentUser());
        Assertions.assertEquals("user1", App.getCurrentUser().getName());
        Assertions.assertEquals(Page.AUTHORIZED_USER_PAGE, App.getPage());
    }

    @Test
    public void testSignUpFailEmailExists() {
        when(userRepository.existsByEmail("a@mail.ru")).thenReturn(true);
        Scanner scanner = new Scanner(new ByteArrayInputStream("a@mail.ru\nuser1\npass1".getBytes(StandardCharsets.UTF_8)));

        authService.signUp(scanner);
        Assertions.assertNull(App.getCurrentUser());
    }
}
