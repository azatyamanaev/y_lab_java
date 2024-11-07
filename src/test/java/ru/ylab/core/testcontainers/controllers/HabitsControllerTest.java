package ru.ylab.core.testcontainers.controllers;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;
import ru.ylab.core.dto.in.HabitForm;
import ru.ylab.core.dto.out.HabitDto;
import ru.ylab.core.exception.Error;
import ru.ylab.core.models.Habit;
import ru.ylab.core.testcontainers.config.AbstractSpringTest;
import ru.ylab.core.testcontainers.config.TestConfigurer;
import ru.ylab.core.utils.constants.ErrorConstants;
import ru.ylab.core.utils.constants.WebConstants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ylab.core.utils.constants.WebConstants.HABITS_URL;
import static ru.ylab.core.utils.constants.WebConstants.SEARCH_URL;
import static ru.ylab.core.utils.constants.WebConstants.USER_URL;

public class HabitsControllerTest extends AbstractSpringTest {

    @Autowired
    @Qualifier("mapper")
    private ObjectMapper mapper;

    @DisplayName("Test(controller): get habit for user")
    @Test
    public void testGetHabit() throws Exception {
        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL + "/-2")
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        HabitDto dto = mapper.readValue(result.getResponse().getContentAsString(), HabitDto.class);
        assertThat(dto).isNotNull();
        assertThat(dto.name()).isEqualTo("h2_test");
    }

    @DisplayName("Test(controller): fail get habit for user not author")
    @Test
    public void testGetHabitFail() throws Exception {
        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL + "/-4")
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isBadRequest())
                                       .andReturn();

        Error error = mapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertThat(error).isNotNull();
        assertThat(error.getMessage()).isEqualTo(ErrorConstants.BAD_REQUEST);
        assertThat(error.getDetails().get(0).getType()).isEqualTo(ErrorConstants.NOT_AUTHOR);
        assertThat(error.getDetails().get(0).getTarget()).isEqualTo("user");
    }

    @DisplayName("Test(controller): get habits for user")
    @Test
    public void testGetHabits() throws Exception {
        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(dtos).isNotNull();
        assertThat(dtos).size().isEqualTo(3);
    }

    @DisplayName("Test(controller): search habits by name for user")
    @Test
    public void testSearchHabitsByName() throws Exception {
        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL + SEARCH_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .param("name", "h1")
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(dtos).isNotNull();
        assertThat(dtos).size().isEqualTo(1);
        assertThat(dtos.get(0).name()).startsWith("h1");
    }

    @DisplayName("Test(controller): search habits by frequency for user")
    @Test
    public void testSearchHabitsByFrequency() throws Exception {
        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL + SEARCH_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .param("frequency", "WEEKLY")
                                       .requestAttr("currentUser", TestConfigurer.getTestUser()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(dtos).isNotNull();
        assertThat(dtos).size().isEqualTo(1);
        assertThat(dtos.get(0).frequency()).isEqualTo(Habit.Frequency.WEEKLY);
    }

    @DisplayName("Test(controller): create habit for user")
    @Test
    public void testCreateHabit() throws Exception {
        HabitForm form = new HabitForm();
        form.setName("test_habit");
        form.setDescription("desc1");
        form.setFrequency(Habit.Frequency.MONTHLY);

        this.mockMvc.perform(post(USER_URL + HABITS_URL)
                    .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
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
                    .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                    .requestAttr("currentUser", TestConfigurer.getTestUser())
                    .contentType("application/json")
                    .content(mapper.writeValueAsString(form)))
                    .andExpect(status().isNoContent());
    }

    @DisplayName("Test(controller): delete habit for user")
    @Test
    public void testDeleteHabit() throws Exception {
        this.mockMvc.perform(delete(USER_URL + HABITS_URL + "/-1")
                    .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                    .requestAttr("currentUser", TestConfigurer.getTestUser()))
                    .andExpect(status().isNoContent());
    }
}
