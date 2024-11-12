package ru.ylab.mockito.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.dto.out.HabitDto;
import ru.ylab.mockito.config.AbstractWebTest;
import ru.ylab.mockito.config.TestConfigurer;
import ru.ylab.models.Habit;
import ru.ylab.utils.constants.WebConstants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ylab.utils.constants.WebConstants.ADMIN_URL;
import static ru.ylab.utils.constants.WebConstants.HABITS_URL;
import static ru.ylab.utils.constants.WebConstants.SEARCH_URL;

public class AdminHabitsControllerTest extends AbstractWebTest {

    @Autowired
    @Qualifier("mapper")
    private ObjectMapper mapper;

    @DisplayName("Test(controller): get habit for admin")
    @Test
    public void testGetHabit() throws Exception {
        Long id = 1L;
        when(habitService.get(id)).thenReturn(TestConfigurer.getOneHabit());

        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + HABITS_URL + "/" + id)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_ADMIN_ACCESS)
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        HabitDto habit = mapper.readValue(result.getResponse().getContentAsString(), HabitDto.class);
        assertThat(habit).hasFieldOrPropertyWithValue("name", "h1_test");
    }

    @DisplayName("Test(controller): get habits for admin")
    @Test
    public void testGetHabits() throws Exception {
        when(habitService.getAll()).thenReturn(TestConfigurer.getHabits());

        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + HABITS_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_ADMIN_ACCESS)
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});
        assertThat(dtos).hasSize(6);

    }

    @DisplayName("Test(controller): search habits by name for admin")
    @Test
    public void testSearchHabitsByName() throws Exception {
        String name = "hb";
        HabitSearchForm form = new HabitSearchForm();
        form.setName(name);
        when(habitService.search(form)).thenReturn(
                TestConfigurer.getHabits()
                              .stream()
                              .filter(x -> x.getName().contains(name))
                              .collect(Collectors.toList()));

        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + HABITS_URL + SEARCH_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_ADMIN_ACCESS)
                                       .param("name", name)
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});
        assertThat(dtos).hasSize(3);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(dtos.get(0).name()).startsWith("hb");
            softly.assertThat(dtos.get(1).name()).startsWith("hb");
            softly.assertThat(dtos.get(2).name()).startsWith("hb");
        });
    }

    @DisplayName("Test(controller): search habits by frequency for admin")
    @Test
    public void testSearchHabitsByFrequency() throws Exception {
        Habit.Frequency frequency = Habit.Frequency.DAILY;
        HabitSearchForm form = new HabitSearchForm();
        form.setFrequency(frequency);
        when(habitService.search(form)).thenReturn(
                TestConfigurer.getHabits()
                              .stream()
                              .filter(x -> x.getFrequency().equals(frequency))
                              .collect(Collectors.toList()));

        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + HABITS_URL + SEARCH_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_ADMIN_ACCESS)
                                       .param("frequency", "DAILY")
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});
        assertThat(dtos).hasSize(2);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(dtos.get(0).frequency()).isEqualTo(Habit.Frequency.DAILY);
            softly.assertThat(dtos.get(1).frequency()).isEqualTo(Habit.Frequency.DAILY);
        });

    }
}
