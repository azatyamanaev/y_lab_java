package ru.ylab.mockito.controllers;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;
import ru.ylab.dto.in.PeriodForm;
import ru.ylab.dto.out.HabitCompletionPercent;
import ru.ylab.dto.out.HabitCompletionStreak;
import ru.ylab.dto.out.HabitHistoryProjection;
import ru.ylab.mockito.config.AbstractWebTest;
import ru.ylab.mockito.config.TestConfigurer;
import ru.ylab.models.User;
import ru.ylab.utils.constants.WebConstants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ylab.utils.constants.WebConstants.HABIT_HISTORY_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_PERCENTAGE_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_REPORT_URL;
import static ru.ylab.utils.constants.WebConstants.HABIT_STREAK_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

public class HabitHistoryControllerTest extends AbstractWebTest {

    @Autowired
    @Qualifier("mapper")
    private ObjectMapper mapper;

    @DisplayName("Test(controller): get habit history for user")
    @Test
    public void testGetHabitHistory() throws Exception {
        User user = TestConfigurer.getTestUser();
        Long id = 1L;
        when(habitHistoryService.getHabitHistory(user.getId(), id))
                .thenReturn(TestConfigurer.getHabitHistoryProjection());

        MvcResult result = this.mockMvc.perform(get(USER_URL + HABIT_HISTORY_URL + "/" + id)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .requestAttr("currentUser", user))
                                       .andExpect(status().isOk())
                                       .andReturn();

        HabitHistoryProjection dto = mapper.readValue(result.getResponse().getContentAsString(), HabitHistoryProjection.class);
        assertThat(dto).isNotNull();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(dto.getHabitName()).isEqualTo("h1_test");
            softly.assertThat(dto.getDays()).size().isEqualTo(3);
        });
    }

    @DisplayName("Test(controller): mark habit completed for user")
    @Test
    public void testMarkHabitCompleted() throws Exception {
        this.mockMvc.perform(put(USER_URL + HABIT_HISTORY_URL + "/-1")
                    .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                    .param("completed_on", "2014-10-30")
                    .requestAttr("currentUser", TestConfigurer.getTestUser()))
                    .andExpect(status().isNoContent());
    }

    @DisplayName("Test(controller): get habits streak for user")
    @Test
    public void testGetHabitStreak() throws Exception {
        User user = TestConfigurer.getTestUser();
        when(habitHistoryService.habitCompletionStreak(user.getId()))
                .thenReturn(TestConfigurer.habitCompletionStreak());

        MvcResult result = this.mockMvc.perform(get(USER_URL + HABIT_HISTORY_URL + HABIT_STREAK_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitCompletionStreak> dtos = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});
        assertThat(dtos).hasSize(3);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(dtos).contains(new HabitCompletionStreak("h1_test", 1));
            softly.assertThat(dtos).contains(new HabitCompletionStreak("h2_test", 0));
            softly.assertThat(dtos).contains(new HabitCompletionStreak("h3_test", 0));
        });

    }

    @DisplayName("Test(controller): get habits percentage for user")
    @Test
    public void testGetHabitPercentage() throws Exception {
        String from = "2024-07-01";
        String to = "2024-10-30";
        PeriodForm form = new PeriodForm();
        form.setFrom(LocalDate.parse(from));
        form.setTo(LocalDate.parse(to));
        User user = TestConfigurer.getTestUser();
        when(habitHistoryService.habitCompletionPercent(user.getId(), form))
                .thenReturn(TestConfigurer.habitCompletionPercent(form));

        MvcResult result = this.mockMvc.perform(get(USER_URL + HABIT_HISTORY_URL + HABIT_PERCENTAGE_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .param("from", from)
                                       .param("to", to)
                                       .requestAttr("currentUser", user))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitCompletionPercent> dtos = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});
        assertThat(dtos).hasSize(3);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(dtos.get(1).percent()).isEqualTo("0%");
            softly.assertThat(dtos.get(2).percent()).isEqualTo("0%");
        });
    }

    @DisplayName("Test(controller): get habits report for user")
    @Test
    public void testGetHabitReport() throws Exception {
        User user = TestConfigurer.getTestUser();
        when(habitHistoryService.habitCompletionReport(user.getId()))
                .thenReturn(TestConfigurer.habitCompletionReport());

        MvcResult result = this.mockMvc.perform(get(USER_URL + HABIT_HISTORY_URL + HABIT_REPORT_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .requestAttr("currentUser", user))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitHistoryProjection> dtos = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});
        assertThat(dtos).hasSize(3);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(dtos.get(0).getDays()).hasSize(3);
            softly.assertThat(dtos.get(1).getDays()).isEmpty();
            softly.assertThat(dtos.get(2).getDays()).isEmpty();
        });
    }
}
