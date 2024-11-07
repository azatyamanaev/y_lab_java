package ru.ylab.core.mockito.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;
import ru.ylab.core.dto.in.HabitForm;
import ru.ylab.core.dto.in.HabitSearchForm;
import ru.ylab.core.dto.out.HabitDto;
import ru.ylab.core.exception.Error;
import ru.ylab.core.exception.HttpException;
import ru.ylab.core.models.Habit;
import ru.ylab.core.mockito.config.AbstractWebTest;
import ru.ylab.core.mockito.config.TestConfigurer;
import ru.ylab.core.models.User;
import ru.ylab.core.utils.constants.ErrorConstants;
import ru.ylab.core.utils.constants.WebConstants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ylab.core.utils.constants.WebConstants.HABITS_URL;
import static ru.ylab.core.utils.constants.WebConstants.SEARCH_URL;
import static ru.ylab.core.utils.constants.WebConstants.USER_URL;

public class HabitsControllerTest extends AbstractWebTest {

    @Autowired
    @Qualifier("mapper")
    private ObjectMapper mapper;

    @DisplayName("Test(controller): get habit for user")
    @Test
    public void testGetHabit() throws Exception {
        User user = TestConfigurer.getTestUser();
        Long id = 1L;
        when(habitService.getForUser(user.getId(), id)).thenReturn(TestConfigurer.getOneHabit());

        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL + "/" + id)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .requestAttr("currentUser", user))
                                       .andExpect(status().isOk())
                                       .andReturn();

        HabitDto dto = mapper.readValue(result.getResponse().getContentAsString(), HabitDto.class);
        assertThat(dto).isNotNull();
        assertThat(dto.name()).isEqualTo("h1_test");
    }

    @DisplayName("Test(controller): fail get habit for user not author")
    @Test
    public void testGetHabitFail() throws Exception {
        User user = TestConfigurer.getTestUser();
        Long id = 4L;
        when(habitService.getForUser(user.getId(), id))
                .thenThrow(HttpException.badRequest().addDetail(ErrorConstants.NOT_AUTHOR, "user"));

        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL + "/" + id)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .requestAttr("currentUser", user))
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
        User user = TestConfigurer.getTestUser();
        when(habitService.getHabitsForUser(user.getId())).thenReturn(
                TestConfigurer.getHabits()
                              .stream()
                              .filter(x -> x.getUserId().equals(user.getId()))
                              .collect(Collectors.toList())
        );

        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .requestAttr("currentUser", user))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertThat(dtos).isNotNull();
        assertThat(dtos).size().isEqualTo(3);
    }

    @DisplayName("Test(controller): search habits by name for user")
    @Test
    public void testSearchHabitsByName() throws Exception {
        User user = TestConfigurer.getTestUser();
        String name = "h1";
        HabitSearchForm form = new HabitSearchForm();
        form.setName(name);
        when(habitService.searchHabitsForUser(user.getId(), form)).thenReturn(
                TestConfigurer.getHabits()
                              .stream()
                              .filter(x -> x.getUserId().equals(user.getId()) && x.getName().contains(name))
                              .collect(Collectors.toList())
        );


        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL + SEARCH_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .param("name", name)
                                       .requestAttr("currentUser", user))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertThat(dtos).isNotNull();
        assertThat(dtos).size().isEqualTo(1);
        assertThat(dtos.get(0).name()).startsWith("h1");
    }

    @DisplayName("Test(controller): search habits by frequency for user")
    @Test
    public void testSearchHabitsByFrequency() throws Exception {
        User user = TestConfigurer.getTestUser();
        Habit.Frequency frequency = Habit.Frequency.WEEKLY;
        HabitSearchForm form = new HabitSearchForm();
        form.setFrequency(frequency);
        when(habitService.searchHabitsForUser(user.getId(), form)).thenReturn(
                TestConfigurer.getHabits()
                              .stream()
                              .filter(x -> x.getUserId().equals(user.getId()) && x.getFrequency().equals(frequency))
                              .collect(Collectors.toList())
        );

        MvcResult result = this.mockMvc.perform(get(USER_URL + HABITS_URL + SEARCH_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .param("frequency", frequency.name())
                                       .requestAttr("currentUser", user))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<HabitDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
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
