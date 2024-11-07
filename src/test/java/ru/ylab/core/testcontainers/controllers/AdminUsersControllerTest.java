package ru.ylab.core.testcontainers.controllers;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;
import ru.ylab.core.dto.in.UserForm;
import ru.ylab.core.dto.out.UserDto;
import ru.ylab.core.models.User;
import ru.ylab.core.testcontainers.config.AbstractSpringTest;
import ru.ylab.core.testcontainers.config.TestConfigurer;
import ru.ylab.core.utils.constants.WebConstants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ylab.core.utils.constants.WebConstants.ADMIN_URL;
import static ru.ylab.core.utils.constants.WebConstants.SEARCH_URL;
import static ru.ylab.core.utils.constants.WebConstants.USERS_URL;

public class AdminUsersControllerTest extends AbstractSpringTest {

    @Autowired
    @Qualifier("mapper")
    private ObjectMapper mapper;

    @DisplayName("Test(controller): get user for admin")
    @Test
    public void testGetUser() throws Exception {
        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + USERS_URL + "/-1")
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_ADMIN_ACCESS)
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        UserDto dto = mapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertThat(dto).isNotNull();
        assertThat(dto.email()).isEqualTo("a_test@mail.ru");
    }

    @DisplayName("Test(controller): search users by email for admin")
    @Test
    public void testSearchUsersByEmail() throws Exception {
        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + USERS_URL + SEARCH_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_ADMIN_ACCESS)
                                       .param("email", "mail.ru")
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<UserDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertThat(dtos).isNotNull();
        assertThat(dtos).size().isEqualTo(3);
        assertThat(dtos.get(0).name()).isEqualTo("admin_test");
    }

    @DisplayName("Test(controller): search users by email and role for admin")
    @Test
    public void testSearchUsersByEmailAndRole() throws Exception {
        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + USERS_URL + SEARCH_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_ADMIN_ACCESS)
                                       .param("email", "mail.ru")
                                       .param("role", "ADMIN")
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<UserDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertThat(dtos).isNotNull();
        assertThat(dtos).size().isEqualTo(1);
        assertThat(dtos.get(0).name()).isEqualTo("admin_test");
    }

    @DisplayName("Test(controller): create user for admin")
    @Test
    public void testCreateUser() throws Exception {
        UserForm form = new UserForm();
        form.setEmail("c_user@mail.ru");
        form.setName("cname");
        form.setPassword("pass");
        form.setRole(User.Role.USER);

        this.mockMvc.perform(post(ADMIN_URL + USERS_URL)
                    .header("Authorization", "Bearer " + WebConstants.JWTOKEN_ADMIN_ACCESS)
                    .requestAttr("currentUser", TestConfigurer.getTestAdmin())
                    .contentType("application/json")
                    .content(mapper.writeValueAsString(form)))
                    .andExpect(status().isCreated());
    }

    @DisplayName("Test(controller): delete user for admin")
    @Test
    public void testDeleteUser() throws Exception {
        this.mockMvc.perform(delete(ADMIN_URL + USERS_URL + "/-2")
                    .header("Authorization", "Bearer " + WebConstants.JWTOKEN_ADMIN_ACCESS)
                    .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                    .andExpect(status().isNoContent());
    }
}
