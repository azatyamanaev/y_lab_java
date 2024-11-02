package ru.ylab.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;

@Configuration
@ComponentScan(basePackages = "org.springdoc.core")
@Import({org.springdoc.core.configuration.SpringDocConfiguration.class,
        org.springdoc.webmvc.core.configuration.SpringDocWebMvcConfiguration.class,
        org.springdoc.webmvc.ui.SwaggerConfig.class,
        org.springdoc.core.properties.SwaggerUiConfigProperties.class,
        org.springdoc.core.properties.SwaggerUiConfigProperties.class,
        org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class})
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                             .group("habits-app")
                             .packagesToScan("ru.ylab.web.controllers")
                             .build();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .specVersion(SpecVersion.V30)
                .addServersItem(new Server().url("http://localhost:8080/habits-app"))
                .info(
                        new Info()
                                .title("Habits app REST API")
                                .description("")
                                .version("v1")
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "jwtoken",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                                .in(SecurityScheme.In.HEADER)
                                                .name(HttpHeaders.AUTHORIZATION)
                                )
                );
    }
}

