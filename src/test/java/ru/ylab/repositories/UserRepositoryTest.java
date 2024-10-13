package ru.ylab.repositories;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.ylab.forms.UserSearchForm;
import ru.ylab.models.User;

public class UserRepositoryTest {

    private Storage storage;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        storage = new Storage();
        userRepository = new UserRepository(storage);
        userRepository.save(new User(0L, "admin", "admin@mail.ru", "admin", User.Role.ADMIN));
        userRepository.save(new User(1L, "user1", "user1@mail.ru", "pass1", User.Role.USER));
        userRepository.save(new User(2L, "user2", "user2@mail.ru", "pass2", User.Role.USER));
    }

    @Test
    public void testGetByEmail() {
        User user = userRepository.getByEmail("admin@mail.ru");
        Assert.assertEquals("admin", user.getName());
    }

    @Test
    public void testGetByEmailFail() {
        Assert.assertNull(userRepository.getByEmail("a123@mail.ru"));
    }

    @Test
    public void testExistsByEmail() {
        Assert.assertTrue(userRepository.existsByEmail("admin@mail.ru"));
    }

    @Test
    public void testExistsByEmailFail() {
        Assert.assertFalse(userRepository.existsByEmail("a123@mail.ru"));
    }

    @Test
    public void testGetAll() {
        Assert.assertEquals(3, userRepository.getAll().size());
    }

    @Test
    public void testSearchByRole() {
        UserSearchForm form = new UserSearchForm();
        form.setRole(User.Role.USER.name());
        List<User> users = userRepository.search(form);
        Assert.assertEquals(2, users.size());
        Assert.assertEquals(User.Role.USER, users.get(0).getRole());
        Assert.assertEquals(User.Role.USER, users.get(1).getRole());
    }

    @Test
    public void testSearchByName() {
        UserSearchForm form = new UserSearchForm();
        form.setName("ad");
        List<User> users = userRepository.search(form);
        Assert.assertEquals(1, users.size());
        Assert.assertEquals("admin", users.get(0).getName());
    }

    @Test
    public void testSearchByEmail() {
        UserSearchForm form = new UserSearchForm();
        form.setEmail("user");
        List<User> users = userRepository.search(form);
        Assert.assertEquals(2, users.size());
        Assert.assertTrue(users.get(0).getEmail().startsWith("user"));
        Assert.assertTrue(users.get(1).getEmail().startsWith("user"));
    }

    @Test
    public void testSave() {
        userRepository.save(new User(3L, "user3", "user3@mail.ru", "pass3", User.Role.USER));
        User user = storage.getUsers().get(3L);
        Assert.assertEquals("user3", user.getName());

    }

    @Test
    public void testUpdate() {
        userRepository.update(new User(2L, "user22", "user2@mail.ru", "pass22", User.Role.USER));
        User user = storage.getUsers().get(2L);
        Assert.assertEquals("user22", user.getName());
        Assert.assertEquals("pass22", user.getPassword());

    }

    @Test
    public void testDeleteByEmail() {
        Assert.assertTrue(userRepository.deleteByEmail("user2@mail.ru"));
    }

    @Test
    public void testDeleteByEmailFail() {
        Assert.assertFalse(userRepository.deleteByEmail("a123@mail.ru"));
    }
}
