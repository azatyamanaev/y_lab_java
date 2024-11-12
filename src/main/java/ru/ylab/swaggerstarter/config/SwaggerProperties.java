package ru.ylab.swaggerstarter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger starter properties
 *
 * @param enabled whether this starter should be enabled
 * @param groupName openapi group name
 * @param packages packages where Rest Controllers are
 * @param serverUrl app server url
 * @param title app OpenAPI title
 * @param description app OpenAPI description
 * @param version app OpenAPI version
 * @author azatyamanaev
 */
@ConfigurationProperties(prefix = "spring.swagger-starter")
public record SwaggerProperties(Boolean enabled, String groupName, String packages, String serverUrl,
                                String title, String description, String version) { }
