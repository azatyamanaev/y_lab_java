package ru.ylab.repositories;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.forms.UserSearchForm;
import ru.ylab.models.User;
import ru.ylab.repositories.impl.UserRepositoryImpl;

public class UserRepositoryTest extends AbstractRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepositoryImpl(dataSource);
    }

    @DisplayName("Test: get user by email")
    @Test
    public void testGetByEmail() {
        User user = userRepository.getByEmail("admin@mail.ru");
        Assertions.assertEquals("admin", user.getName());
    }

    @DisplayName("Test: fail to get user by email, user does not exist")
    @Test
    public void testGetByEmailFail() {
        Assertions.assertNull(userRepository.getByEmail("a123@mail.ru"));
    }

    @DisplayName("Test: user exists by email")
    @Test
    public void testExistsByEmail() {
        Assertions.assertTrue(userRepository.existsByEmail("admin@mail.ru"));
    }

    @DisplayName("Test: user does not exist by email")
    @Test
    public void testExistsByEmailFail() {
        Assertions.assertFalse(userRepository.existsByEmail("a123@mail.ru"));
    }

    @DisplayName("Test: search users by role")
    @Test
    public void testSearchByRole() {
        UserSearchForm form = new UserSearchForm();
        form.setRole(User.Role.ADMIN.name());
        List<User> users = userRepository.search(form);
        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals(User.Role.ADMIN, users.get(0).getRole());
    }

    @DisplayName("Test: search users by name")
    @Test
    public void testSearchByName() {
        UserSearchForm form = new UserSearchForm();
        form.setName("ad");
        List<User> users = userRepository.search(form);
        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals("admin", users.get(0).getName());
    }

    @DisplayName("Test: search users by email")
    @Test
    public void testSearchByEmail() {
        UserSearchForm form = new UserSearchForm();
        form.setEmail("user");
        List<User> users = userRepository.search(form);
        Assertions.assertEquals(2, users.size());
        Assertions.assertTrue(users.get(0).getEmail().startsWith("user"));
        Assertions.assertTrue(users.get(1).getEmail().startsWith("user"));
    }

    @DisplayName("Test: save user")
    @Test
    public void testSave() {
        userRepository.save(new User(3L, "us3", "us3@mail.ru", "pass3", User.Role.USER));
        User user = userRepository.getByEmail("us3@mail.ru");
        Assertions.assertEquals("us3", user.getName());
    }

    @DisplayName("Test: update user")
    @Test
    public void testUpdate() {
        userRepository.update(new User(-2L, "user22", "user2@mail.ru", "pass22", User.Role.USER));
        User user = userRepository.getByEmail("user2@mail.ru");
        Assertions.assertEquals("user22", user.getName());
        Assertions.assertEquals("pass22", user.getPassword());
    }

    @DisplayName("Test: delete user by email")
    @Test
    public void testDeleteByEmail() {
        Assertions.assertTrue(userRepository.deleteByEmail("user2@mail.ru"));
    }

    @DisplayName("Test: fail to delete user by email, user does not exist")
    @Test
    public void testDeleteByEmailFail() {
        Assertions.assertFalse(userRepository.deleteByEmail("a123@mail.ru"));
    }
}
