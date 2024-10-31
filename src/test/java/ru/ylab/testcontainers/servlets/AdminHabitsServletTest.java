package ru.ylab.testcontainers.servlets;

import ru.ylab.testcontainers.config.AbstractServletTest;

public class AdminHabitsServletTest extends AbstractServletTest {

//    private AdminHabitsServlet servlet;
//
//    @BeforeEach
//    public void setUpHttp() throws ServletException {
//        writer = new StringWriter();
//        mapper = appContext.getMappersConfig().getMapper();
//        servlet = new AdminHabitsServlet();
//        servlet.init(config);
//    }
//
//    @DisplayName("Test(servlet): get habit for admin")
//    @Test
//    public void testGetHabit() throws ServletException, IOException {
//        request = mockRequest("GET", APP_CONTEXT_PATH + ADMIN_URL + HABITS_URL + ONE_URL,
//                null, Map.ofEntries(Map.entry("id", "-10")));
//        response = mockResponse(writer);
//        servlet.service(request, response);
//
//        Assertions.assertEquals(200, statusCode);
//        Assertions.assertEquals("application/json", contentType);
//        HabitDto result = mapper.readValue(writer.toString(), HabitDto.class);
//        Assertions.assertEquals("h1_test", result.name());
//        Assertions.assertEquals(Habit.Frequency.DAILY, result.frequency());
//    }
//
//    @DisplayName("Test(servlet): search habits by name for admin")
//    @Test
//    public void testSearchHabitsByName() throws ServletException, IOException {
//        request = mockRequest("GET", APP_CONTEXT_PATH + ADMIN_URL + HABITS_URL + SEARCH_URL,
//                null, Map.ofEntries(Map.entry("name", "test")));
//        response = mockResponse(writer);
//        servlet.service(request, response);
//
//        Assertions.assertEquals(200, statusCode);
//        Assertions.assertEquals("application/json", contentType);
//        List<HabitDto> result = mapper.readValue(writer.toString(), new TypeReference<>() {});
//        Assertions.assertEquals(5, result.size());
//        Assertions.assertTrue(result.get(0).name().contains("test"));
//    }
//
//    @DisplayName("Test(servlet): search habits by name and frequency for admin")
//    @Test
//    public void testSearchHabitsByNameAndFrequency() throws ServletException, IOException {
//        request = mockRequest("GET", APP_CONTEXT_PATH + ADMIN_URL + HABITS_URL + SEARCH_URL,
//                null, Map.ofEntries(Map.entry("name", "test"), Map.entry("frequency", "DAILY")));
//        response = mockResponse(writer);
//        servlet.service(request, response);
//
//        Assertions.assertEquals(200, statusCode);
//        Assertions.assertEquals("application/json", contentType);
//        List<HabitDto> result = mapper.readValue(writer.toString(), new TypeReference<>() {});
//        Assertions.assertEquals(2, result.size());
//        Assertions.assertTrue(result.get(0).frequency().equals(Habit.Frequency.DAILY));
//
//    }

}
