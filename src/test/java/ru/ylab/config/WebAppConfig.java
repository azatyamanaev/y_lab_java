package ru.ylab.config;

import java.io.File;
import java.net.URI;
import java.net.http.HttpRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.DockerComposeContainer;

public abstract class WebAppConfig {

    protected static final String APP_CONTEXT = "habits-app";

    protected static final int POSTGRES_PORT = 5432;
    protected static final String POSTGRES_NAME = "postgres-db";
    protected static final int TOMCAT_PORT = 8080;
    protected static final String TOMCAT_NAME = "tomcat";

    protected static DockerComposeContainer dockerCompose;

    @BeforeAll
    public static void init() {
        dockerCompose = new DockerComposeContainer(new File("./test-compose.yml"))
                .withExposedService(POSTGRES_NAME, POSTGRES_PORT)
                .withExposedService(TOMCAT_NAME, TOMCAT_PORT);
        dockerCompose.start();
    }

    @AfterAll
    public static void destroy() {
        dockerCompose.stop();
    }

    public HttpRequest httpRequest(String url, String method, Object body) throws JsonProcessingException {
        ObjectMapper mapper = MappersConfig.mapper();
        HttpRequest.BodyPublisher publisher;
        if (body != null) {
            publisher = HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(body));
        } else {
            publisher = HttpRequest.BodyPublishers.noBody();
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method(method, publisher)
                .build();

        return request;
    }
}
