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
import ru.ylab.dto.in.HabitForm;
import ru.ylab.dto.out.HabitDto;
import ru.ylab.exception.Error;
import ru.ylab.models.Habit;
import ru.ylab.testcontainers.config.AbstractSpringTest;
import ru.ylab.testcontainers.config.TestConfigurer;
import ru.ylab.utils.constants.ErrorConstants;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ylab.utils.constants.WebConstants.HABITS_URL;
import static ru.ylab.utils.constants.WebConstants.SEARCH_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

public class HabitsControllerTest extends AbstractSpringTest {

    @Autowired
    @Qualifier("mapper")
    private ObjectMapper mapper;

    @DisplayName("Test(controller): get habit for user")
    @Test
    public void testGetHabit() throws Exception {
        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL + "/-2")
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        HabitDto dto = mapper.readValue(result.getResponse().getContentAsString(), HabitDto.class);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals("h2_test", dto.name());
    }

    @DisplayName("Test(controller): fail get habit for user not author")
    @Test
    public void testGetHabitFail() throws Exception {
        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL + "/-4")
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isBadRequest())
                                       .andReturn();

        Error error = mapper.readValue(result.getResponse().getContentAsString(), Error.class);
        Assertions.assertNotNull(error);
        Assertions.assertEquals(ErrorConstants.BAD_REQUEST, error.getMessage());
        Assertions.assertEquals(ErrorConstants.NOT_AUTHOR, error.getDetails().get(0).getType());
        Assertions.assertEquals("user", error.getDetails().get(0).getTarget());
    }

    @DisplayName("Test(controller): get habits for user")
    @Test
    public void testGetHabits() throws Exception {
        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL)
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(3, dtos.size());
    }

    @DisplayName("Test(controller): search habits by name for user")
    @Test
    public void testSearchHabitsByName() throws Exception {
        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL + SEARCH_URL)
                                       .param("name", "h1")
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(1, dtos.size());
        Assertions.assertTrue(dtos.get(0).name().contains("h1"));
    }

    @DisplayName("Test(controller): search habits by frequency for user")
    @Test
    public void testSearchHabitsByFrequency() throws Exception {
        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL + SEARCH_URL)
                                       .param("frequency", "WEEKLY")
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertNotNull(dtos);
        Assertions.assertEquals(1, dtos.size());
        Assertions.assertEquals(Habit.Frequency.WEEKLY, dtos.get(0).frequency());
    }

    @DisplayName("Test(controller): create habit for user")
    @Test
    public void testCreateHabit() throws Exception {
        HabitForm form = new HabitForm();
        form.setName("test_habit");
        form.setDescription("desc1");
        form.setFrequency(Habit.Frequency.MONTHLY);

        this.mockMvc.perform(post(USER_URL + HABITS_URL)
                    .requestAttr("currentUser", TestConfigurer.getTestUser())
                    .contentType("application/json")
                    .content(mapper.writeValueAsString(form)))
                    .andExpect(status().isCreated());
    }

    @DisplayName("Test(controller): update habit for user")
    @Test
    public void testUpdateHabit() throws Exception {
        HabitForm form = new HabitForm();
        form.setName("test_habit");
        form.setDescription("desc1");
        form.setFrequency(Habit.Frequency.MONTHLY);

        this.mockMvc.perform(put(USER_URL + HABITS_URL + "/-1")
                    .requestAttr("currentUser", TestConfigurer.getTestUser())
                    .contentType("application/json")
                    .content(mapper.writeValueAsString(form)))
                    .andExpect(status().isNoContent());
    }

    @DisplayName("Test(controller): delete habit for user")
    @Test
    public void testDeleteHabit() throws Exception {
        this.mockMvc.perform(delete(USER_URL + HABITS_URL + "/-1")
                    .requestAttr("currentUser", TestConfigurer.getTestUser()))
                    .andExpect(status().isNoContent());
    }
}
