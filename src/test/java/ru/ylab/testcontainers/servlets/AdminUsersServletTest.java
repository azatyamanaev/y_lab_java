package ru.ylab.testcontainers.servlets;

import ru.ylab.testcontainers.config.AbstractServletTest;

public class AdminUsersServletTest extends AbstractServletTest {


//    private AdminUsersServlet servlet;
//
//    @BeforeEach
//    public void setUpHttp() throws ServletException {
//        writer = new StringWriter();
//        mapper = appContext.getMappersConfig().getMapper();
//        servlet = new AdminUsersServlet();
//        servlet.init(config);
//    }
//
//    @DisplayName("Test(servlet): get user for admin")
//    @Test
//    public void testGetUser() throws IOException, ServletException {
//        request = mockRequest("GET", APP_CONTEXT_PATH + ADMIN_URL + USERS_URL + ONE_URL,
//                null, Map.ofEntries(Map.entry("id", "-20")));
//        response = mockResponse(writer);
//        servlet.service(request, response);
//
//        Assertions.assertEquals(200, statusCode);
//        Assertions.assertEquals("application/json", contentType);
//        UserDto result = mapper.readValue(writer.toString(), UserDto.class);
//        Assertions.assertEquals("a_test@mail.ru", result.email());
//    }
//
//    @DisplayName("Test(servlet): search users by email for admin")
//    @Test
//    public void testSearchUsersByEmail() throws IOException, ServletException {
//        request = mockRequest("GET", APP_CONTEXT_PATH + ADMIN_URL + USERS_URL + SEARCH_URL,
//                null, Map.ofEntries(Map.entry("email", "test")));
//        response = mockResponse(writer);
//        servlet.service(request, response);
//
//        Assertions.assertEquals(200, statusCode);
//        Assertions.assertEquals("application/json", contentType);
//        List<UserDto> result = mapper.readValue(writer.toString(), new TypeReference<>() {});
//        Assertions.assertEquals(3, result.size());
//    }
//
//    @DisplayName("Test(servlet): search users by email and role for admin")
//    @Test
//    public void testSearchUsersByEmailAndRole() throws IOException, ServletException {
//        request = mockRequest("GET", APP_CONTEXT_PATH + ADMIN_URL + USERS_URL + SEARCH_URL,
//                null, Map.ofEntries(Map.entry("email", "test"), Map.entry("role", "ADMIN")));
//        response = mockResponse(writer);
//        servlet.service(request, response);
//
//        Assertions.assertEquals(200, statusCode);
//        Assertions.assertEquals("application/json", contentType);
//        List<UserDto> result = mapper.readValue(writer.toString(), new TypeReference<>() {});
//        Assertions.assertEquals(1, result.size());
//        Assertions.assertEquals("admin_test", result.get(0).name());
//    }
//
//    @DisplayName("Test(servlet): create user for admin")
//    @Test
//    public void testCreateUser() throws ServletException, IOException {
//        UserForm form = new UserForm();
//        form.setEmail("c_test@mail.ru");
//        form.setName("cname");
//        form.setPassword("pass");
//        form.setRole("USER");
//        request = mockRequest("POST", APP_CONTEXT_PATH + ADMIN_URL + USERS_URL + ONE_URL,
//                convertToReader(form), null);
//        response = mockResponse(writer);
//        servlet.service(request, response);
//
//        Assertions.assertEquals(201, statusCode);
//        Assertions.assertEquals("application/json", contentType);
//    }
//
//    @DisplayName("Test(servlet): delete user for admin")
//    @Test
//    public void testDeleteUser() throws ServletException, IOException {
//        request = mockRequest("DELETE", APP_CONTEXT_PATH + ADMIN_URL + USERS_URL + ONE_URL,
//                null, Map.ofEntries(Map.entry("id", "-30")));
//        response = mockResponse(writer);
//        servlet.service(request, response);
//
//        Assertions.assertEquals(204, statusCode);
//        Assertions.assertEquals("application/json", contentType);
//    }
}
