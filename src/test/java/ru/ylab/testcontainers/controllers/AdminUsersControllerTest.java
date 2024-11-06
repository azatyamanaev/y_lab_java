package ru.ylab.testcontainers.controllers;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;
import ru.ylab.dto.in.UserForm;
import ru.ylab.dto.out.UserDto;
import ru.ylab.models.User;
import ru.ylab.testcontainers.config.AbstractSpringTest;
import ru.ylab.testcontainers.config.TestConfigurer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ylab.utils.constants.WebConstants.ADMIN_URL;
import static ru.ylab.utils.constants.WebConstants.SEARCH_URL;
import static ru.ylab.utils.constants.WebConstants.USERS_URL;

public class AdminUsersControllerTest extends AbstractSpringTest {

    @Autowired
    @Qualifier("mapper")
    private ObjectMapper mapper;


    @DisplayName("Test(controller): get user for admin")
    @Test
    public void testGetUser() throws Exception {
        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + USERS_URL + "/-1")
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
                                       .param("email", "mail.ru")
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<UserDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(dtos).isNotNull();
        assertThat(dtos).size().isEqualTo(3);
        assertThat(dtos.get(0).name()).isEqualTo("admin_test");
    }

    @DisplayName("Test(controller): search users by email and role for admin")
    @Test
    public void testSearchUsersByEmailAndRole() throws Exception {
        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + USERS_URL + SEARCH_URL)
                                       .param("email", "mail.ru")
                                       .param("role", "ADMIN")
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<UserDto> dtos = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
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
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin())
                                       .contentType("application/json")
                                       .content(mapper.writeValueAsString(form)))
                                       .andExpect(status().isCreated());
    }

    @DisplayName("Test(controller): delete user for admin")
    @Test
    public void testDeleteUser() throws Exception {
        this.mockMvc.perform(delete(ADMIN_URL + USERS_URL + "/-2")
                    .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                    .andExpect(status().isNoContent());
    }
}
