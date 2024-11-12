package ru.ylab.mockito.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;
import ru.ylab.dto.in.UserForm;
import ru.ylab.dto.in.UserSearchForm;
import ru.ylab.dto.out.UserDto;
import ru.ylab.models.User;
import ru.ylab.mockito.config.AbstractWebTest;
import ru.ylab.mockito.config.TestConfigurer;
import ru.ylab.utils.constants.WebConstants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ylab.utils.constants.WebConstants.ADMIN_URL;
import static ru.ylab.utils.constants.WebConstants.SEARCH_URL;
import static ru.ylab.utils.constants.WebConstants.USERS_URL;

public class AdminUsersControllerTest extends AbstractWebTest {

    @Autowired
    @Qualifier("mapper")
    private ObjectMapper mapper;

    @DisplayName("Test(controller): get user for admin")
    @Test
    public void testGetUser() throws Exception {
        Long id = 1L;
        when(userService.get(id)).thenReturn(TestConfigurer.getTestUser());

        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + USERS_URL + "/" + id)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_ADMIN_ACCESS)
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        UserDto dto = mapper.readValue(result.getResponse().getContentAsString(), UserDto.class);
        assertThat(dto).hasFieldOrPropertyWithValue("email", "a_test@mail.ru");
    }

    @DisplayName("Test(controller): search users by email for admin")
    @Test
    public void testSearchUsersByEmail() throws Exception {
        String email = "mail.ru";
        UserSearchForm form = new UserSearchForm();
        form.setEmail(email);
        when(userService.searchUsers(form)).thenReturn(
                TestConfigurer.getUsers()
                        .stream()
                        .filter(x -> x.getEmail().contains(email))
                        .collect(Collectors.toList()));

        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + USERS_URL + SEARCH_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_ADMIN_ACCESS)
                                       .param("email", email)
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<UserDto> dtos = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});
        assertThat(dtos).hasSize(3);
        assertThat(dtos.get(0).name()).isEqualTo("admin_test");
    }

    @DisplayName("Test(controller): search users by email and role for admin")
    @Test
    public void testSearchUsersByEmailAndRole() throws Exception {
        String email = "mail.ru";
        User.Role role = User.Role.ADMIN;
        UserSearchForm form = new UserSearchForm();
        form.setEmail(email);
        form.setRole(role);
        when(userService.searchUsers(form)).thenReturn(
                TestConfigurer.getUsers()
                              .stream()
                              .filter(x -> x.getEmail().contains(email) && x.getRole().equals(role))
                              .collect(Collectors.toList()));

        MvcResult result = this.mockMvc.perform(get(ADMIN_URL + USERS_URL + SEARCH_URL)
                                       .header("Authorization", "Bearer " + WebConstants.JWTOKEN_ADMIN_ACCESS)
                                       .param("email", email)
                                       .param("role", role.name())
                                       .requestAttr("currentUser", TestConfigurer.getTestAdmin()))
                                       .andExpect(status().isOk())
                                       .andReturn();

        List<UserDto> dtos = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {});
        assertThat(dtos).hasSize(1);
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

        when(userRepository.existsByEmail("c_user@mail.ru")).thenReturn(false);

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
