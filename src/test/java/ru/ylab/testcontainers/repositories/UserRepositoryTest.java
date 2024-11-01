package ru.ylab.testcontainers.repositories;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.in.UserSearchForm;
import ru.ylab.models.User;
import ru.ylab.repositories.UserRepository;
import ru.ylab.testcontainers.config.AbstractSpringTest;

public class UserRepositoryTest extends AbstractSpringTest {

    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository = this.appContext.getBean(UserRepository.class);
    }

    @DisplayName("Test(repository): find user by id")
    @Test
    public void testFindById() {
        Assertions.assertTrue(userRepository.find(-1L).isPresent());
    }

    @DisplayName("Test(repository): fail to find user by id, user does not exist")
    @Test
    public void testFindByIdFail() {
        Assertions.assertTrue(userRepository.find(-10L).isEmpty());
    }

    @DisplayName("Test(repository): user exists by email")
    @Test
    public void testExistsByEmail() {
        Assertions.assertTrue(userRepository.existsByEmail("admin_test@mail.ru"));
    }

    @DisplayName("Test(repository): user does not exist by email")
    @Test
    public void testExistsByEmailFail() {
        Assertions.assertFalse(userRepository.existsByEmail("a123_test@mail.ru"));
    }

    @DisplayName("Test(repository): search users by role")
    @Test
    public void testSearchByRole() {
        UserSearchForm form = new UserSearchForm();
        form.setRole(User.Role.ADMIN);
        List<User> users = userRepository.search(form);
        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals(User.Role.ADMIN, users.get(0).getRole());
    }

    @DisplayName("Test(repository): search users by email")
    @Test
    public void testSearchByEmail() {
        UserSearchForm form = new UserSearchForm();
        form.setEmail("admin_test");
        List<User> users = userRepository.search(form);
        Assertions.assertEquals(1, users.size());
        Assertions.assertTrue(users.get(0).getEmail().contains("admin_test"));
    }

    @DisplayName("Test(repository): save user")
    @Test
    public void testSave() {
        Assertions.assertTrue(userRepository.save(User.builder()
                                                      .id(-3L)
                                                      .name("us3_test")
                                                      .email("us3_test@mail.ru")
                                                      .password("pass3")
                                                      .role(User.Role.USER).build()));
    }

    @DisplayName("Test(repository): update user")
    @Test
    public void testUpdate() {
        SignUpForm form = new SignUpForm();
        form.setEmail("b_test2@mail.ru");
        form.setName("user22");
        form.setPassword("pass22");
        Assertions.assertTrue(userRepository.update(-1L, form));
    }

    @DisplayName("Test(repository): delete user by id")
    @Test
    public void testDeleteById() {
        Assertions.assertTrue(userRepository.delete(-2L));
    }

    @DisplayName("Test(repository): fail to delete user by id, user does not exist")
    @Test
    public void testDeleteByIdFail() {
        Assertions.assertFalse(userRepository.delete(-10L));
    }
}
