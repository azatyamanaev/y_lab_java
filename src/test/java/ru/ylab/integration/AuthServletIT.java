package ru.ylab.integration;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.config.MappersConfig;
import ru.ylab.config.WebAppConfig;
import ru.ylab.dto.in.SignInForm;
import ru.ylab.dto.out.SignInResult;

public class AuthServletIT extends WebAppConfig {

    private ObjectMapper mapper;
    private HttpClient client;
    private HttpResponse<String> response;

    @BeforeEach
    public void setUp() {
        client = HttpClient.newHttpClient();
        mapper = MappersConfig.mapper();
    }

    @DisplayName("Test(integration): sign in")
    @Test
    public void testSignIn() throws IOException, InterruptedException {
        int port = dockerCompose.getServicePort(TOMCAT_NAME, TOMCAT_PORT);
        String host = dockerCompose.getServiceHost(TOMCAT_NAME, TOMCAT_PORT);
        SignInForm form = new SignInForm();
        form.setEmail("a@mail.ru");
        form.setPassword("123");
        HttpRequest request = httpRequest("http://" + host + ":" + port + "/habits-app" + "/auth/signIn",
                "POST", form);
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        SignInResult result = mapper.readValue(response.body(), SignInResult.class);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertNotNull(result);
    }

}
