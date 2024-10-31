package ru.ylab.testcontainers.servlets;

import ru.ylab.testcontainers.config.AbstractServletTest;

public class UsersServletTest extends AbstractServletTest {

//    private UsersServlet servlet;
//
//    @BeforeEach
//    public void setUpHttp() throws ServletException {
//        writer = new StringWriter();
//        mapper = appContext.getMappersConfig().getMapper();
//        servlet = new UsersServlet();
//        servlet.init(config);
//    }
//
//    @DisplayName("Test(servlet): get profile for user")
//    @Test
//    public void testGetProfile() throws IOException, ServletException {
//        request = mockRequest("GET", APP_CONTEXT_PATH + USER_URL + SELF_URL,
//                null, null);
//        when(request.getAttribute("currentUser")).thenReturn(User.builder()
//                                                                 .id(-20L)
//                                                                 .build());
//        response = mockResponse(writer);
//        servlet.service(request, response);
//
//        UserDto result = mapper.readValue(writer.toString(), UserDto.class);
//        Assertions.assertEquals(200, statusCode);
//        Assertions.assertEquals("application/json", contentType);
//        Assertions.assertEquals(-20L, result.id());
//        Assertions.assertEquals("user1", result.name());
//    }
//
//    @DisplayName("Test(servlet): update profile for user")
//    @Test
//    public void testUpdateProfile() throws IOException, ServletException {
//        SignUpForm form = new SignUpForm();
//        form.setName("user11");
//        form.setEmail("a_test11@mail.ru");
//        form.setPassword("1234");
//        request = mockRequest("PUT", APP_CONTEXT_PATH + USER_URL + SELF_URL,
//                convertToReader(form), null);
//        when(request.getAttribute("currentUser")).thenReturn(User.builder()
//                                                                 .id(-20L)
//                                                                 .build());
//        response = mockResponse(writer);
//        servlet.service(request, response);
//
//        Assertions.assertEquals(204, statusCode);
//        Assertions.assertEquals("application/json", contentType);
//    }
//
//    @DisplayName("Test(servlet): delete profile for user")
//    @Test
//    public void testDeleteProfile() throws IOException, ServletException {
//        request = mockRequest("DELETE", APP_CONTEXT_PATH + USER_URL + SELF_URL,
//                null,  null);
//        when(request.getAttribute("currentUser")).thenReturn(User.builder()
//                                                                 .id(-20L)
//                                                                 .build());
//        response = mockResponse(writer);
//        servlet.service(request, response);
//
//        Assertions.assertEquals(204, statusCode);
//        Assertions.assertEquals("application/json", contentType);
//    }
}
