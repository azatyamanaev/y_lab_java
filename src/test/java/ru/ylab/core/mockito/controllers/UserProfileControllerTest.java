package ru.ylab.core.mockito.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;
import ru.ylab.core.dto.in.SignUpForm;
import ru.ylab.core.dto.out.UserDto;
import ru.ylab.core.mockito.config.AbstractWebTest;
import ru.ylab.core.mockito.config.TestConfigurer;
import ru.ylab.core.models.User;
import ru.ylab.core.utils.constants.WebConstants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ylab.core.utils.constants.WebConstants.SELF_URL;
import static ru.ylab.core.utils.constants.WebConstants.USER_URL;

public class UserProfileControllerTest extends AbstractWebTest {

    @Autowired
    @Qualifier("mapper")
    private ObjectMapper mapper;

    @DisplayName("Test(controller): get profile for user")
    @Test
    public void testGetProfile() throws Exception {
        User user = TestConfigurer.getTestUser();
        when(userService.get(user.getId())).thenReturn(TestConfigurer.getTestUser());

        MvcResult result = this.mockMvc.perform(get(USER_URL + SELF_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                                       .requestAttr("currentUser", user))
                                       .andExpect(status().isOk())
                                       .andReturn();

        UserDto dto = mapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertThat(dto).hasFieldOrPropertyWithValue("name", "user1");
    }

    @DisplayName("Test(controller): update profile for user")
    @Test
    public void testUpdateProfile() throws Exception {
        SignUpForm form = new SignUpForm();
        form.setName("user11");
        form.setEmail("user11_test@mail.ru");
        form.setPassword("1234");

        this.mockMvc.perform(put(USER_URL + SELF_URL)
                    .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                    .requestAttr("currentUser", TestConfigurer.getTestUser())
                    .contentType("application/json")
                    .content(mapper.writeValueAsString(form)))
                    .andExpect(status().isNoContent());
    }

    @DisplayName("Test(controller): delete profile for user")
    @Test
    public void testDeleteProfile() throws Exception {
        this.mockMvc.perform(delete(USER_URL + SELF_URL)
                    .header("Authorization", "Bearer " + WebConstants.JWTOKEN_USER_ACCESS)
                    .requestAttr("currentUser", TestConfigurer.getTestUser()))
                    .andExpect(status().isNoContent());
    }
}
