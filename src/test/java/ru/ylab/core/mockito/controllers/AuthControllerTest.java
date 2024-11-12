package ru.ylab.core.mockito.controllers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.web.servlet.MvcResult;
import ru.ylab.core.dto.in.SignInForm;
import ru.ylab.core.dto.in.SignUpForm;
import ru.ylab.core.dto.out.SignInResult;
import ru.ylab.core.exception.Error;
import ru.ylab.core.exception.HttpException;
import ru.ylab.core.mockito.config.AbstractWebTest;
import ru.ylab.core.mockito.config.TestConfigurer;
import ru.ylab.core.models.RefreshToken;
import ru.ylab.core.models.User;
import ru.ylab.core.utils.constants.ErrorConstants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ylab.core.utils.constants.WebConstants.AUTH_URL;
import static ru.ylab.core.utils.constants.WebConstants.REFRESH_TOKEN_URL;
import static ru.ylab.core.utils.constants.WebConstants.SIGN_IN_URL;
import static ru.ylab.core.utils.constants.WebConstants.SIGN_UP_URL;

public class AuthControllerTest extends AbstractWebTest {

    @Autowired
    @Qualifier("mapper")
    private ObjectMapper mapper;

    @DisplayName("Test(controller): sign in")
    @Test
    public void testSignIn() throws Exception {
        SignInForm form = new SignInForm();
        form.setEmail("a_test@mail.ru");
        form.setPassword("123");

        when(userService.getByEmail("a_test@mail.ru")).thenReturn(TestConfigurer.getTestUser());

        MvcResult result = this.mockMvc.perform(post(AUTH_URL + SIGN_IN_URL)
                                       .contentType("application/json")
                                       .content(mapper.writeValueAsString(form))).andDo(print())
                                       .andExpect(status().isOk())
                                       .andReturn();

        SignInResult res = mapper.readValue(result.getResponse().getContentAsString(), SignInResult.class);
        assertThat(res).isNotNull();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(res.access()).isNotNull();
            softly.assertThat(res.refresh()).isNotNull();
        });
    }

    @DisplayName("Test(controller): sign in fail on incorrect password")
    @Test
    public void testSignInFailIncorrectPassword() throws Exception {
        SignInForm form = new SignInForm();
        form.setEmail("a_test@mail.ru");
        form.setPassword("1234");

        when(userService.getByEmail("a_test@mail.ru")).thenReturn(TestConfigurer.getTestUser());

        MvcResult result = this.mockMvc.perform(post(AUTH_URL + SIGN_IN_URL)
                                       .contentType("application/json")
                                       .content(mapper.writeValueAsString(form)))
                                       .andExpect(status().isBadRequest())
                                       .andReturn();

        Error error = mapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertThat(error).isNotNull();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(error.getMessage()).isEqualTo(ErrorConstants.BAD_REQUEST);
            softly.assertThat(error.getDetails().get(0).getType()).isEqualTo(ErrorConstants.INVALID_PARAM);
            softly.assertThat(error.getDetails().get(0).getTarget()).isEqualTo("password");
        });
    }

    @DisplayName("Test(controller): sign in fail on user not found")
    @Test
    public void testSignInFailOnUserNotFound() throws Exception {
        SignInForm form = new SignInForm();
        form.setEmail("some_user@mail.ru");
        form.setPassword("123");

        when(userService.getByEmail("some_user@mail.ru"))
                .thenThrow(HttpException.badRequest().addDetail(ErrorConstants.NOT_FOUND, "user"));

        MvcResult result = this.mockMvc.perform(post(AUTH_URL + SIGN_IN_URL)
                                       .contentType("application/json")
                                       .content(mapper.writeValueAsString(form)))
                                       .andExpect(status().isBadRequest())
                                       .andReturn();

        Error error = mapper.readValue(result.getResponse().getContentAsString(), Error.class);
        assertThat(error).isNotNull();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(error.getMessage()).isEqualTo(ErrorConstants.BAD_REQUEST);
            softly.assertThat(error.getDetails().get(0).getType()).isEqualTo(ErrorConstants.NOT_FOUND);
            softly.assertThat(error.getDetails().get(0).getTarget()).isEqualTo("user");
        });
    }

    @DisplayName("Test(controller): sign up")
    @Test
    public void testSignUp() throws Exception {
        SignUpForm form = new SignUpForm();
        form.setName("some_user");
        form.setEmail("some_user@mail.ru");
        form.setPassword("pass");

        User user = User.builder()
                        .id(3L)
                        .email(form.getEmail())
                        .name(form.getName())
                        .role(User.Role.USER)
                        .build();

        when(userService.getByEmail("some_user@mail.ru")).thenReturn(user);

        MvcResult result = this.mockMvc.perform(post(AUTH_URL + SIGN_UP_URL)
                                       .contentType("application/json")
                                       .content(mapper.writeValueAsString(form)))
                                       .andExpect(status().isOk())
                                       .andReturn();

        SignInResult res = mapper.readValue(result.getResponse().getContentAsString(), SignInResult.class);
        assertThat(res).isNotNull();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(res.access()).isNotNull();
            softly.assertThat(res.refresh()).isNotNull();
        });
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
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(error.getMessage()).isEqualTo(ErrorConstants.VALIDATION_ERROR);
            softly.assertThat(error.getDetails().get(0).getType()).isEqualTo(ErrorConstants.EMPTY_PARAM);
            softly.assertThat(error.getDetails().get(0).getTarget()).isEqualTo("name");
        });
    }

    @DisplayName("Test(controller): refresh access token")
    @Test
    public void testRefreshToken() throws Exception {
        SignInForm form = new SignInForm();
        form.setEmail("a_test@mail.ru");
        form.setPassword("123");

        when(userService.getByEmail("a_test@mail.ru")).thenReturn(TestConfigurer.getTestUser());

        MvcResult mvcResult = this.mockMvc.perform(post(AUTH_URL + SIGN_IN_URL)
                                          .contentType("application/json")
                                          .content(mapper.writeValueAsString(form)))
                                          .andReturn();
        SignInResult response = mapper.readValue(mvcResult.getResponse().getContentAsString(), SignInResult.class);

        when(refreshTokenRepository.findByToken(response.refresh()))
                .thenReturn(Optional.of(
                        new RefreshToken(1L, form.getEmail(), 1L,
                                Instant.now().minus(1, ChronoUnit.DAYS),
                                Instant.now().minus(14, ChronoUnit.DAYS))
                ));
        when(userService.get(1L)).thenReturn(TestConfigurer.getTestUser());

        this.mockMvc.perform(get(AUTH_URL + REFRESH_TOKEN_URL).param("token", response.refresh()))
                    .andExpect(status().isOk())
                    .andExpect(result -> assertThat(result.getResponse().getContentAsString()).isNotBlank());
    }
}
