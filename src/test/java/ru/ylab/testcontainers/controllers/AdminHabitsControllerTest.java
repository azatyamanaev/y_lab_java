package ru.ylab.testcontainers.controllers;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;
import ru.ylab.dto.out.HabitDto;
import ru.ylab.models.Habit;
import ru.ylab.testcontainers.config.AbstractSpringTest;
import ru.ylab.testcontainers.config.TestConfigurer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ylab.utils.constants.WebConstants.ADMIN_URL;
import static ru.ylab.utils.constants.WebConstants.HABITS_URL;
import static ru.ylab.utils.constants.WebConstants.SEARCH_URL;

public class AdminHabitsControllerTest extends AbstractSpringTest {

    @Autowired
    @Qualifier("mapper")
    private ObjectMapper mapper;

    @DisplayName("Test(controller): get habit for admin")
    @Test
    public void testGetHabit() throws Exception {
        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + HABITS_URL + "/-1")
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        HabitDto habit = mapper.readValue(result.getResponse().getContentAsString(), HabitDto.class);
        Assertions.assertNotNull(habit);
        Assertions.assertEquals("h1_test", habit.name());
    }

    @DisplayName("Test(controller): get habits for admin")
    @Test
    public void testGetHabits() throws Exception {
        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + HABITS_URL)
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(5, dtos.size());
    }

    @DisplayName("Test(controller): search habits by name for admin")
    @Test
    public void testSearchHabitsByName() throws Exception {
        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + HABITS_URL + SEARCH_URL)
                                       .param("name", "hb")
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(2, dtos.size());
        Assertions.assertTrue(dtos.get(0).name().contains("hb"));
    }

    @DisplayName("Test(controller): search habits by frequency for admin")
    @Test
    public void testSearchHabitsByFrequency() throws Exception {
        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + HABITS_URL + SEARCH_URL)
                                       .param("frequency", "DAILY")
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(2, dtos.size());
        Assertions.assertEquals(Habit.Frequency.DAILY, dtos.get(0).frequency());
        Assertions.assertEquals(Habit.Frequency.DAILY, dtos.get(1).frequency());
    }
}
