package ru.ylab.unit.testcontainers.servlets;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.dto.out.HabitCompletionPercent;
import ru.ylab.dto.out.HabitCompletionStreak;
import ru.ylab.dto.out.HabitHistoryProjection;
import ru.ylab.unit.testcontainers.config.AbstractServletTest;
import ru.ylab.models.User;
import ru.ylab.web.servlets.HabitHistoryServlet;

import static org.mockito.Mockito.when;
import static ru.ylab.utils.constants.WebConstants.APP_CONTEXT_PATH;
import static ru.ylab.utils.constants.WebConstants.HABIT_HISTORY_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_PERCENTAGE_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_REPORT_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_STREAK_URL;
import static ru.ylab.utils.constants.WebConstants.ONE_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

public class HabitHistoryServletTest extends AbstractServletTest {

    private HabitHistoryServlet servlet;

    @BeforeEach
    public void setUpHttp() throws ServletException {
        writer = new StringWriter();
        mapper = appContext.getMappersConfig().getMapper();
        servlet = new HabitHistoryServlet();
        servlet.init(config);
    }

    @DisplayName("Test(servlet): get habit history for user")
    @Test
    public void testGetHabitHistory() throws ServletException, IOException {
        request = mockRequest("GET", APP_CONTEXT_PATH + USER_URL + HABIT_HISTORY_URL + ONE_URL,
                null, Map.ofEntries(Map.entry("id", "-10")));
        when(request.getAttribute("currentUser")).thenReturn(User.builder()
                                                                 .id(-20L)
                                                                 .build());
        response = mockResponse(writer);
        servlet.service(request, response);

        HabitHistoryProjection result = mapper.readValue(writer.toString(), HabitHistoryProjection.class);
        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals("application/json", contentType);
        Assertions.assertEquals("h1_test", result.getHabitName());
        Assertions.assertEquals(3, result.getDays().size());
        Assertions.assertTrue(result.getDays().contains(LocalDate.parse("2024-10-01")));
    }

    @DisplayName("Test(servlet): get habits streak for user")
    @Test
    public void testGetHabitStreak() throws ServletException, IOException {
        request = mockRequest("GET", APP_CONTEXT_PATH + USER_URL + HABIT_HISTORY_URL + HABIT_STREAK_URL,
                null, null);
        when(request.getAttribute("currentUser")).thenReturn(User.builder()
                                                                 .id(-20L)
                                                                 .build());
        response = mockResponse(writer);
        servlet.service(request, response);

        List<HabitCompletionStreak> result = mapper.readValue(writer.toString(), new TypeReference<>() {});
        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals("application/json", contentType);
        Assertions.assertEquals(3, result.size());
        Assertions.assertTrue(result.contains(new HabitCompletionStreak("h1_test", 1)));
    }

    @DisplayName("Test(servlet): get habits percentage for period for user")
    @Test
    public void testGetHabitPercentage() throws ServletException, IOException {
        String from = "2024-07-01";
        String to = "2024-11-01";
        request = mockRequest("GET", APP_CONTEXT_PATH + USER_URL + HABIT_HISTORY_URL + HABIT_PERCENTAGE_URL,
                null, Map.ofEntries(Map.entry("from", from), Map.entry("to", to)));
        when(request.getAttribute("currentUser")).thenReturn(User.builder()
                                                                 .id(-20L)
                                                                 .build());
        response = mockResponse(writer);
        servlet.service(request, response);

        List<HabitCompletionPercent> result = mapper.readValue(writer.toString(), new TypeReference<>() {});
        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals("application/json", contentType);
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("0%", result.get(2).percent());
    }

    @DisplayName("Test(servlet): get habits report for user")
    @Test
    public void testGetHabitReport() throws ServletException, IOException {
        request = mockRequest("GET", APP_CONTEXT_PATH + USER_URL + HABIT_HISTORY_URL + HABIT_REPORT_URL,
                null, null);
        when(request.getAttribute("currentUser")).thenReturn(User.builder()
                                                                 .id(-20L)
                                                                 .build());
        response = mockResponse(writer);
        servlet.service(request, response);

        List<HabitHistoryProjection> result = mapper.readValue(writer.toString(), new TypeReference<>() {});
        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals("application/json", contentType);
        Assertions.assertEquals(3, result.size());
        Assertions.assertTrue(result.get(2).getDays().isEmpty());
    }
}
