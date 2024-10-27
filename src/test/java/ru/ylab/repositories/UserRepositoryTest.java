package ru.ylab.repositories;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.in.UserSearchForm;
import ru.ylab.models.User;
import ru.ylab.repositories.impl.UserRepositoryImpl;

public class UserRepositoryTest extends AbstractRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepositoryImpl(dataSource);
    }

    @DisplayName("Test: find user by id")
    @Test
    public void testFindById() {
        Assertions.assertTrue(userRepository.find(0L).isPresent());
    }

    @DisplayName("Test: fail to find user by id, user does not exist")
    @Test
    public void testFindByIdFail() {
        Assertions.assertTrue(userRepository.find(100L).isEmpty());
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
        Assertions.assertTrue(userRepository.save(User.builder()
                                                      .name("us3")
                                                      .email("us3@mail.ru")
                                                      .password("pass3")
                                                      .role(User.Role.USER).build()));
    }

    @DisplayName("Test: update user")
    @Test
    public void testUpdate() {
        SignUpForm form = new SignUpForm();
        form.setEmail("user2@mail.ru");
        form.setName("user22");
        form.setPassword("pass22");
        Assertions.assertTrue(userRepository.update(-2L, form));
    }

    @DisplayName("Test: delete user by id")
    @Test
    public void testDeleteById() {
        Assertions.assertTrue(userRepository.delete(-1L));
    }

    @DisplayName("Test: fail to delete user by id, user does not exist")
    @Test
    public void testDeleteByIdFail() {
        Assertions.assertFalse(userRepository.delete(100L));
    }
}
