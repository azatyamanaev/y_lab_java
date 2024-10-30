package ru.ylab.unit.testcontainers.servlets;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.dto.in.HabitForm;
import ru.ylab.dto.out.HabitDto;
import ru.ylab.unit.testcontainers.config.AbstractServletTest;
import ru.ylab.models.Habit;
import ru.ylab.models.User;
import ru.ylab.web.servlets.HabitsServlet;

import static org.mockito.Mockito.when;
import static ru.ylab.utils.constants.WebConstants.APP_CONTEXT_PATH;
import static ru.ylab.utils.constants.WebConstants.HABITS_URL;
import static ru.ylab.utils.constants.WebConstants.ONE_URL;
import static ru.ylab.utils.constants.WebConstants.SEARCH_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

public class HabitsServletTest extends AbstractServletTest {

    private HabitsServlet servlet;

    @BeforeEach
    public void setUpHttp() throws ServletException {
        writer = new StringWriter();
        mapper = appContext.getMappersConfig().getMapper();
        servlet = new HabitsServlet();
        servlet.init(config);
    }

    @DisplayName("Test(servlet): get habit for user")
    @Test
    public void testGetHabit() throws IOException, ServletException {
        request = mockRequest("GET", APP_CONTEXT_PATH + USER_URL + HABITS_URL + ONE_URL,
                null, Map.ofEntries(Map.entry("id", "-20")));
        when(request.getAttribute("currentUser")).thenReturn(User.builder()
                                                                 .id(-20L)
                                                                 .build());
        response = mockResponse(writer);
        servlet.service(request, response);

        HabitDto result = mapper.readValue(writer.toString(), HabitDto.class);
        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals("application/json", contentType);
        Assertions.assertEquals("h2_test", result.name());
    }

    @DisplayName("Test(servlet): get habits for user")
    @Test
    public void testGetHabits() throws IOException, ServletException {
        request = mockRequest("GET", APP_CONTEXT_PATH + USER_URL + HABITS_URL,
                null, null);
        when(request.getAttribute("currentUser")).thenReturn(User.builder()
                                                                 .id(-20L)
                                                                 .build());
        response = mockResponse(writer);
        servlet.service(request, response);

        List<HabitDto> result = mapper.readValue(writer.toString(), new TypeReference<>() {});
        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals("application/json", contentType);
        Assertions.assertEquals(3, result.size());
    }

    @DisplayName("Test(servlet): search habits by name for user")
    @Test
    public void testSearchHabitsByName() throws IOException, ServletException {
        request = mockRequest("GET", APP_CONTEXT_PATH + USER_URL + HABITS_URL + SEARCH_URL,
                null, Map.ofEntries(Map.entry("name", "h2")));
        when(request.getAttribute("currentUser")).thenReturn(User.builder()
                                                                 .id(-20L)
                                                                 .build());
        response = mockResponse(writer);
        servlet.service(request, response);

        List<HabitDto> result = mapper.readValue(writer.toString(), new TypeReference<>() {});
        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals("application/json", contentType);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("h2_test", result.get(0).name());
    }

    @DisplayName("Test(servlet): search habits by frequency for user")
    @Test
    public void testSearchHabitsByFrequency() throws IOException, ServletException {
        request = mockRequest("GET", APP_CONTEXT_PATH + USER_URL + HABITS_URL + SEARCH_URL,
                null, Map.ofEntries(Map.entry("frequency", "DAILY")));
        when(request.getAttribute("currentUser")).thenReturn(User.builder()
                                                                 .id(-20L)
                                                                 .build());
        response = mockResponse(writer);
        servlet.service(request, response);

        List<HabitDto> result = mapper.readValue(writer.toString(), new TypeReference<>() {});
        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals("application/json", contentType);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(Habit.Frequency.DAILY, result.get(0).frequency());
        Assertions.assertEquals(Habit.Frequency.DAILY, result.get(1).frequency());
    }

    @DisplayName("Test(servlet): create habit for user")
    @Test
    public void testCreateHabit() throws IOException, ServletException {
        HabitForm form = new HabitForm();
        form.setName("test_hb1");
        form.setFrequency("MONTHLY");
        form.setDescription("test_desc1");
        request = mockRequest("POST", APP_CONTEXT_PATH + USER_URL + HABITS_URL + ONE_URL,
                convertToReader(form), null);
        when(request.getAttribute("currentUser")).thenReturn(User.builder()
                                                                 .id(-30L)
                                                                 .build());
        response = mockResponse(writer);
        servlet.service(request, response);

        Assertions.assertEquals(201, statusCode);
        Assertions.assertEquals("application/json", contentType);
    }

    @DisplayName("Test(servlet): update habit for user")
    @Test
    public void testUpdateHabit() throws IOException, ServletException {
        HabitForm form = new HabitForm();
        form.setName("test_hb2");
        form.setFrequency("MONTHLY");
        form.setDescription("test_desc2");
        request = mockRequest("PUT", APP_CONTEXT_PATH + USER_URL + HABITS_URL + ONE_URL,
                convertToReader(form), Map.ofEntries(Map.entry("id", "-30")));
        when(request.getAttribute("currentUser")).thenReturn(User.builder()
                                                                 .id(-20L)
                                                                 .build());
        response = mockResponse(writer);
        servlet.service(request, response);

        Assertions.assertEquals(204, statusCode);
        Assertions.assertEquals("application/json", contentType);
    }

    @DisplayName("Test(servlet): delete habit for user")
    @Test
    public void testDeleteHabit() throws IOException, ServletException {
        request = mockRequest("DELETE", APP_CONTEXT_PATH + USER_URL + HABITS_URL + ONE_URL,
                null, Map.ofEntries(Map.entry("id", "-50")));
        when(request.getAttribute("currentUser")).thenReturn(User.builder()
                                                                 .id(-30L)
                                                                 .build());
        response = mockResponse(writer);
        servlet.service(request, response);

        Assertions.assertEquals(204, statusCode);
        Assertions.assertEquals("application/json", contentType);
    }
}
