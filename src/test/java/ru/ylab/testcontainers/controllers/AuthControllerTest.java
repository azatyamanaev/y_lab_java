package ru.ylab.testcontainers.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;
import ru.ylab.dto.in.SignInForm;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.out.SignInResult;
import ru.ylab.exception.Error;
import ru.ylab.testcontainers.config.AbstractSpringTest;
import ru.ylab.utils.constants.ErrorConstants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ylab.utils.constants.WebConstants.AUTH_URL;
import static ru.ylab.utils.constants.WebConstants.REFRESH_TOKEN_URL;
import static ru.ylab.utils.constants.WebConstants.SIGN_IN_URL;
import static ru.ylab.utils.constants.WebConstants.SIGN_UP_URL;

public class AuthControllerTest extends AbstractSpringTest {

    @Autowired
    @Qualifier("mapper")
    private ObjectMapper mapper;

    @DisplayName("Test(controller): sign in")
    @Test
    public void testSignIn() throws Exception {
        SignInForm form = new SignInForm();
        form.setEmail("a_test@mail.ru");
        form.setPassword("123");

        MvcResult result = this.mockMvc.perform(post(AUTH_URL + SIGN_IN_URL)
                                       .contentType("application/json")
                                       .content(mapper.writeValueAsString(form)))
                                       .andExpect(status().isOk())
                                       .andReturn();

        SignInResult res = mapper.readValue(result.getResponse().getContentAsString(), SignInResult.class);
        assertThat(res).isNotNull();
        assertThat(res.access()).isNotNull();
        assertThat(res.refresh()).isNotNull();
    }

    @DisplayName("Test(controller): sign in fail on incorrect password")
    @Test
    public void testSignInFailIncorrectPassword() throws Exception {
        SignInForm form = new SignInForm();
        form.setEmail("a_test@mail.ru");
        form.setPassword("1234");

        MvcResult result = this.mockMvc.perform(post(AUTH_URL + SIGN_IN_URL)
                                       .contentType("application/json")
                                       .content(mapper.writeValueAsString(form)))
                                       .andExpect(status().isBadRequest())
                                       .andReturn();

        Error error = mapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertThat(error).isNotNull();
        assertThat(error.getMessage()).isEqualTo(ErrorConstants.BAD_REQUEST);
        assertThat(error.getDetails().get(0).getType()).isEqualTo(ErrorConstants.INVALID_PARAMETER);
        assertThat(error.getDetails().get(0).getTarget()).isEqualTo("password");
    }

    @DisplayName("Test(controller): sign in fail on user not found")
    @Test
    public void testSignInFailOnUserNotFound() throws Exception {
        SignInForm form = new SignInForm();
        form.setEmail("some_user@mail.ru");
        form.setPassword("123");

        MvcResult result = this.mockMvc.perform(post(AUTH_URL + SIGN_IN_URL)
                                       .contentType("application/json")
                                       .content(mapper.writeValueAsString(form)))
                                       .andExpect(status().isBadRequest())
                                       .andReturn();

        Error error = mapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertThat(error).isNotNull();
        assertThat(error.getMessage()).isEqualTo(ErrorConstants.BAD_REQUEST);
        assertThat(error.getDetails().get(0).getType()).isEqualTo(ErrorConstants.NOT_FOUND);
        assertThat(error.getDetails().get(0).getTarget()).isEqualTo("user");
    }

    @DisplayName("Test(controller): sign up")
    @Test
    public void testSignUp() throws Exception {
        SignUpForm form = new SignUpForm();
        form.setName("some_user");
        form.setEmail("some_user@mail.ru");
        form.setPassword("pass");

        MvcResult result = this.mockMvc.perform(post(AUTH_URL + SIGN_UP_URL)
                                       .contentType("application/json")
                                       .content(mapper.writeValueAsString(form)))
                                       .andExpect(status().isOk())
                                       .andReturn();

        SignInResult res = mapper.readValue(result.getResponse().getContentAsString(), SignInResult.class);
        assertThat(res).isNotNull();
        assertThat(res.access()).isNotNull();
        assertThat(res.refresh()).isNotNull();
    }

    @DisplayName("Test(controller): sign up fail on empty name")
    @Test
    public void testSignUpFailOnEmptyName() throws Exception {
        SignUpForm form = new SignUpForm();
        form.setEmail("some_user@mail.ru");
        form.setPassword("pass");

        MvcResult result = this.mockMvc.perform(post(AUTH_URL + SIGN_UP_URL)
                                       .contentType("application/json")
                                       .content(mapper.writeValueAsString(form)))
                                       .andExpect(status().isBadRequest())
                                       .andReturn();

        Error error = mapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertThat(error).isNotNull();
        assertThat(error.getMessage()).isEqualTo(ErrorConstants.VALIDATION_ERROR);
        assertThat(error.getDetails().get(0).getType()).isEqualTo(ErrorConstants.EMPTY_PARAM);
        assertThat(error.getDetails().get(0).getTarget()).isEqualTo("name");
    }

    @DisplayName("Test(controller): refresh access token")
    @Test
    public void testRefreshToken() throws Exception {
        SignInForm form = new SignInForm();
        form.setEmail("a_test@mail.ru");
        form.setPassword("123");

        MvcResult mvcResult = this.mockMvc.perform(post(AUTH_URL + SIGN_IN_URL)
                                          .contentType("application/json")
                                          .content(mapper.writeValueAsString(form)))
                                          .andReturn();

        SignInResult response = mapper.readValue(mvcResult.getResponse().getContentAsString(), SignInResult.class);
        this.mockMvc.perform(get(AUTH_URL + REFRESH_TOKEN_URL).param("token", response.refresh()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isNotBlank());
    }
}