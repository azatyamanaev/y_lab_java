package ru.spring.swaggerstarter.config;

import java.util.List;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;

/**
 * Class containing swagger configuration.
 *
 * @author azatyamanaev
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@Import(value = {SpringDocConfiguration.class})
public class SwaggerConfiguration {

    public static final String SECURITY_SCHEME_NAME = "JWT Authentication";

    @Bean
    public GroupedOpenApi api(SwaggerProperties properties) {
        return GroupedOpenApi.builder()
                             .group(properties.groupName())
                             .packagesToScan(properties.packages())
                             .build();
    }

    @Bean
    public OpenAPI openApi(SwaggerProperties properties) {
        return new OpenAPI()
                .addServersItem(new Server().url(properties.serverUrl()))
                .info(
                        new Info()
                                .title(properties.title())
                                .description(properties.description())
                                .version(properties.version())
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        SECURITY_SCHEME_NAME,
                                        authorization()
                                )
                )
                .security(List.of(new SecurityRequirement().addList(SECURITY_SCHEME_NAME)));
    }

    private SecurityScheme authorization() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);
    }
}
