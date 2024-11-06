package ru.ylab.testcontainers.controllers;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;
import ru.ylab.dto.out.HabitCompletionPercent;
import ru.ylab.dto.out.HabitCompletionStreak;
import ru.ylab.dto.out.HabitHistoryProjection;
import ru.ylab.testcontainers.config.AbstractSpringTest;
import ru.ylab.testcontainers.config.TestConfigurer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ylab.utils.constants.WebConstants.HABIT_HISTORY_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_PERCENTAGE_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_REPORT_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_STREAK_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

public class HabitHistoryControllerTest extends AbstractSpringTest {

    @Autowired
    @Qualifier("mapper")
    private ObjectMapper mapper;

    @DisplayName("Test(controller): get habit history for user")
    @Test
    public void testGetHabitHistory() throws Exception {
        MvcResult result = this.mockMvc.perform(get(USER_URL + HABIT_HISTORY_URL + "/-1")
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        HabitHistoryProjection dto = mapper.readValue(result.getResponse().getContentAsString(), HabitHistoryProjection.class);
        assertThat(dto).isNotNull();
        assertThat(dto.getHabitName()).isEqualTo("h1_test");
        assertThat(dto.getDays()).size().isEqualTo(3);
    }

    @DisplayName("Test(controller): mark habit completed for user")
    @Test
    public void testMarkHabitCompleted() throws Exception {
        this.mockMvc.perform(put(USER_URL + HABIT_HISTORY_URL + "/-1")
                    .param("completed_on", "2014-10-30")
                    .requestAttr("currentUser", TestConfigurer.getTestUser()))
                    .andExpect(status().isNoContent());
    }

    @DisplayName("Test(controller): get habits streak for user")
    @Test
    public void testGetHabitStreak() throws Exception {
        MvcResult result = this.mockMvc.perform(get(USER_URL + HABIT_HISTORY_URL + HABIT_STREAK_URL)
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitCompletionStreak> dtos = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});
        assertThat(dtos).isNotNull();
        assertThat(dtos).size().isEqualTo(3);
        assertThat(dtos).contains(new HabitCompletionStreak("h1_test", 1));
    }

    @DisplayName("Test(controller): get habits percentage for user")
    @Test
    public void testGetHabitPercentage() throws Exception {
        MvcResult result = this.mockMvc.perform(get(USER_URL + HABIT_HISTORY_URL + HABIT_PERCENTAGE_URL)
                                       .param("from", "2024-07-01")
                                       .param("to", "2024-10-30")
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitCompletionPercent> dtos = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});
        assertThat(dtos).isNotNull();
        assertThat(dtos).size().isEqualTo(3);
        assertThat(dtos.get(2).percent()).isEqualTo("0%");
    }

    @DisplayName("Test(controller): get habits report for user")
    @Test
    public void testGetHabitReport() throws Exception {
        MvcResult result = this.mockMvc.perform(get(USER_URL + HABIT_HISTORY_URL + HABIT_REPORT_URL)
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitHistoryProjection> dtos = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});
        assertThat(dtos).isNotNull();
        assertThat(dtos).size().isEqualTo(3);
        assertThat(dtos.get(0).getDays()).isNotEmpty();
    }
}
