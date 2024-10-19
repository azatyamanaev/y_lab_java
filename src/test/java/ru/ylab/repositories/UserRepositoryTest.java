package ru.ylab.repositories;

public class UserRepositoryTest {

//    private Storage storage;
//    private UserRepository userRepository;
//
//    @BeforeEach
//    public void setUp() {
//        storage = new Storage();
//        userRepository = new UserRepositoryImpl(storage);
//        userRepository.save(new User(0L, "admin", "admin@mail.ru", "admin", User.Role.ADMIN));
//        userRepository.save(new User(1L, "user1", "user1@mail.ru", "pass1", User.Role.USER));
//        userRepository.save(new User(2L, "user2", "user2@mail.ru", "pass2", User.Role.USER));
//    }
//
//    @Test
//    public void testGetByEmail() {
//        User user = userRepository.getByEmail("admin@mail.ru");
//        Assertions.assertEquals("admin", user.getName());
//    }
//
//    @Test
//    public void testGetByEmailFail() {
//        Assertions.assertNull(userRepository.getByEmail("a123@mail.ru"));
//    }
//
//    @Test
//    public void testExistsByEmail() {
//        Assertions.assertTrue(userRepository.existsByEmail("admin@mail.ru"));
//    }
//
//    @Test
//    public void testExistsByEmailFail() {
//        Assertions.assertFalse(userRepository.existsByEmail("a123@mail.ru"));
//    }
//
//    @Test
//    public void testGetAll() {
//        Assertions.assertEquals(3, userRepository.getAll().size());
//    }
//
//    @Test
//    public void testSearchByRole() {
//        UserSearchForm form = new UserSearchForm();
//        form.setRole(User.Role.USER.name());
//        List<User> users = userRepository.search(form);
//        Assertions.assertEquals(2, users.size());
//        Assertions.assertEquals(User.Role.USER, users.get(0).getRole());
//        Assertions.assertEquals(User.Role.USER, users.get(1).getRole());
//    }
//
//    @Test
//    public void testSearchByName() {
//        UserSearchForm form = new UserSearchForm();
//        form.setName("ad");
//        List<User> users = userRepository.search(form);
//        Assertions.assertEquals(1, users.size());
//        Assertions.assertEquals("admin", users.get(0).getName());
//    }
//
//    @Test
//    public void testSearchByEmail() {
//        UserSearchForm form = new UserSearchForm();
//        form.setEmail("user");
//        List<User> users = userRepository.search(form);
//        Assertions.assertEquals(2, users.size());
//        Assertions.assertTrue(users.get(0).getEmail().startsWith("user"));
//        Assertions.assertTrue(users.get(1).getEmail().startsWith("user"));
//    }
//
//    @Test
//    public void testSave() {
//        userRepository.save(new User(3L, "user3", "user3@mail.ru", "pass3", User.Role.USER));
//        User user = storage.getUsers().get(3L);
//        Assertions.assertEquals("user3", user.getName());
//
//    }
//
//    @Test
//    public void testUpdate() {
//        userRepository.update(new User(2L, "user22", "user2@mail.ru", "pass22", User.Role.USER));
//        User user = storage.getUsers().get(2L);
//        Assertions.assertEquals("user22", user.getName());
//        Assertions.assertEquals("pass22", user.getPassword());
//
//    }
//
//    @Test
//    public void testDeleteByEmail() {
//        Assertions.assertTrue(userRepository.deleteByEmail("user2@mail.ru"));
//    }
//
//    @Test
//    public void testDeleteByEmailFail() {
//        Assertions.assertFalse(userRepository.deleteByEmail("a123@mail.ru"));
//    }
}
