package ru.ylab.mockito.servlets;

import ru.ylab.mockito.conifg.WebAppConfig;

public class AdminHabitsServletMockitoTest extends WebAppConfig {

//    private HabitService habitService;
//    private AdminHabitsServlet servlet;
//
//    @BeforeEach
//    public void setUpDependencies() throws ServletException {
//        writer = new StringWriter();
//        mapper = MappersConfig.mapper();
//        servlet = new AdminHabitsServlet();
//
//        habitService = mock(HabitService.class);
//        when(servicesConfig.getHabitService()).thenReturn(habitService);
//        servlet.init(config);
//        initHabits();
//    }
//
//    @DisplayName("Test(servlet): get habit for admin")
//    @Test
//    public void test() throws IOException, ServletException {
//        when(habitService.get(-1L)).thenReturn(habits.get(-1L));
//
//        mockRequest("GET", APP_CONTEXT_PATH + ADMIN_URL + HABITS_URL + ONE_URL,
//                null, Map.ofEntries(Map.entry("id", "-1")));
//        mockResponse(writer);
//        servlet.service(request, response);
//
//        HabitDto result = mapper.readValue(writer.toString(), HabitDto.class);
//        Assertions.assertEquals(200, statusCode);
//        Assertions.assertEquals("application/json", contentType);
//        Assertions.assertEquals("test_habit_1", result.name());
//        Assertions.assertEquals(Habit.Frequency.DAILY, result.frequency());
//    }
//
//    @DisplayName("Test(servlet): search habits by name for admin")
//    @Test
//    public void testSearchHabitsByName() throws ServletException, IOException {
//        HabitSearchForm form = new HabitSearchForm();
//        form.setName("test");
//        when(habitService.search(form)).thenReturn(habits.values().stream()
//                                                         .filter(x -> x.getName().contains("test"))
//                                                         .toList());
//
//        mockRequest("GET", APP_CONTEXT_PATH + ADMIN_URL + HABITS_URL + SEARCH_URL,
//                null, Map.ofEntries(Map.entry("name", "test")));
//        mockResponse(writer);
//        servlet.service(request, response);
//
//        Assertions.assertEquals(200, statusCode);
//        Assertions.assertEquals("application/json", contentType);
//        List<HabitDto> result = mapper.readValue(writer.toString(), new TypeReference<>() {
//        });
//        Assertions.assertEquals(5, result.size());
//        Assertions.assertTrue(result.get(0).name().startsWith("test"));
//    }
//
//    @DisplayName("Test(servlet): search habits by name and frequency for admin")
//    @Test
//    public void testSearchHabitsByNameAndFrequency() throws ServletException, IOException {
//        HabitSearchForm form = new HabitSearchForm();
//        form.setName("test");
//        form.setFrequency("DAILY");
//        when(habitService.search(form)).thenReturn(habits.values().stream()
//                                                         .filter(x -> x.getName().contains("test")
//                                                                 && x.getFrequency().equals(Habit.Frequency.DAILY))
//                                                         .toList());
//
//        mockRequest("GET", APP_CONTEXT_PATH + ADMIN_URL + HABITS_URL + SEARCH_URL,
//                null, Map.ofEntries(Map.entry("name", "test"), Map.entry("frequency", "DAILY")));
//        mockResponse(writer);
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
