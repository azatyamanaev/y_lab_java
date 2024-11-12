package ru.ylab.testcontainers.repositories;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.in.UserSearchForm;
import ru.ylab.models.User;
import ru.ylab.repositories.UserRepository;
import ru.ylab.testcontainers.config.AbstractDbTest;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends AbstractDbTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("Test(repository): find user by id")
    @Test
    public void testFindById() {
        assertThat(userRepository.find(-1L)).isPresent();
    }

    @DisplayName("Test(repository): fail to find user by id, user does not exist")
    @Test
    public void testFindByIdFail() {
        assertThat(userRepository.find(-10L)).isNotPresent();
    }

    @DisplayName("Test(repository): user exists by email")
    @Test
    public void testExistsByEmail() {
        assertThat(userRepository.existsByEmail("admin_test@mail.ru")).isTrue();
    }

    @DisplayName("Test(repository): user does not exist by email")
    @Test
    public void testExistsByEmailFail() {
        assertThat(userRepository.existsByEmail("a123_test@mail.ru")).isFalse();
    }

    @DisplayName("Test(repository): search users by role")
    @Test
    public void testSearchByRole() {
        UserSearchForm form = new UserSearchForm();
        form.setRole(User.Role.ADMIN);
        List<User> users = userRepository.search(form);
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getRole()).isEqualTo(User.Role.ADMIN);
    }

    @DisplayName("Test(repository): search users by email")
    @Test
    public void testSearchByEmail() {
        UserSearchForm form = new UserSearchForm();
        form.setEmail("admin_test");
        List<User> users = userRepository.search(form);
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getEmail()).startsWith("admin_test");
    }

    @DisplayName("Test(repository): save user")
    @Test
    public void testSave() {
        assertThat(userRepository.save(User.builder()
                                           .id(-3L)
                                           .name("us3_test")
                                           .email("us3_test@mail.ru")
                                           .password("pass3")
                                           .role(User.Role.USER).build())).isTrue();
    }

    @DisplayName("Test(repository): update user")
    @Test
    public void testUpdate() {
        SignUpForm form = new SignUpForm();
        form.setEmail("b_test2@mail.ru");
        form.setName("user22");
        form.setPassword("pass22");
        assertThat(userRepository.update(-1L, form)).isTrue();
    }

    @DisplayName("Test(repository): delete user by id")
    @Test
    public void testDeleteById() {
        assertThat(userRepository.delete(-2L)).isTrue();
    }

    @DisplayName("Test(repository): fail to delete user by id, user does not exist")
    @Test
    public void testDeleteByIdFail() {
        assertThat(userRepository.delete(-10L)).isFalse();
    }
}
