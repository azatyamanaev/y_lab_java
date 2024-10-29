package ru.ylab.junit.servlets;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.dto.in.SignInForm;
import ru.ylab.dto.in.SignUpForm;
import ru.ylab.dto.out.SignInResult;
import ru.ylab.services.auth.JWToken;
import ru.ylab.web.servlets.AuthServlet;

import static ru.ylab.utils.constants.WebConstants.APP_CONTEXT_PATH;
import static ru.ylab.utils.constants.WebConstants.AUTH_URL;
import static ru.ylab.utils.constants.WebConstants.REFRESH_TOKEN_URL;
import static ru.ylab.utils.constants.WebConstants.SIGN_IN_URL;
import static ru.ylab.utils.constants.WebConstants.SIGN_UP_URL;

public class AuthServletTest extends AbstractServletTest {

    private AuthServlet servlet;

    @BeforeEach
    public void setUpHttp() throws ServletException {
        writer = new StringWriter();
        mapper = appContext.getMappersConfig().getMapper();
        servlet = new AuthServlet();
        servlet.init(config);
    }

    @DisplayName("Test(servlet): sign in")
    @Test
    public void testSignIn() throws ServletException, IOException {
        SignInForm form = new SignInForm();
        form.setEmail("a_test@mail.ru");
        form.setPassword("123");
        request = mockRequest("POST", APP_CONTEXT_PATH + AUTH_URL + SIGN_IN_URL,
                convertToReader(form), null);
        response = mockResponse(writer);
        servlet.service(request, response);

        SignInResult result = mapper.readValue(writer.toString(), SignInResult.class);
        JWToken token = appContext.getServicesConfig().getJwtService().parse(result.access());
        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals("application/json", contentType);
        Assertions.assertEquals(form.getEmail(), token.getUsername());
    }

    @DisplayName("Test(servlet): sign up")
    @Test
    public void testSignUp() throws IOException, ServletException {
        SignUpForm form = new SignUpForm();
        form.setEmail("certUp@mail.ru");
        form.setName("cname");
        form.setPassword("345");
        request = mockRequest("POST", APP_CONTEXT_PATH + AUTH_URL + SIGN_UP_URL,
                convertToReader(form), null);
        response = mockResponse(writer);
        servlet.service(request, response);

        SignInResult result = mapper.readValue(writer.toString(), SignInResult.class);
        JWToken token = appContext.getServicesConfig().getJwtService().parse(result.access());
        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals("application/json", contentType);
        Assertions.assertEquals(form.getEmail(), token.getUsername());
    }

    @DisplayName("Test(servlet): refresh access token")
    @Test
    public void testRefresh() throws IOException, ServletException {
        SignUpForm form = new SignUpForm();
        form.setEmail("certRefresh@mail.ru");
        form.setName("cname");
        form.setPassword("345");
        request = mockRequest("POST", APP_CONTEXT_PATH + AUTH_URL + SIGN_UP_URL,
                convertToReader(form), null);
        response = mockResponse(writer);
        servlet.service(request, response);

        SignInResult result = mapper.readValue(writer.toString(), SignInResult.class);
        request = mockRequest("GET", APP_CONTEXT_PATH + AUTH_URL + REFRESH_TOKEN_URL,
                null, Map.ofEntries(Map.entry("token", result.refresh())));
        writer = new StringWriter();
        response = mockResponse(writer);
        servlet.service(request, response);
        JWToken token = appContext.getServicesConfig().getJwtService().parse(
                mapper.readValue(writer.toString(), String.class));
        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals("application/json", contentType);
        Assertions.assertEquals(form.getEmail(), token.getUsername());
    }

}
