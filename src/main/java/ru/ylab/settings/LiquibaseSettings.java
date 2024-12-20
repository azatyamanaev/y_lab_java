package ru.ylab.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Class containing Liquibase settings.
 *
 * @param changelogPath Path to changelog.xml file.
 * @param changelogSchema Default schema for liquibase.
 * @param defaultSchema Default schema for entities.
 * @author azatyamanaev
 */
@ConfigurationProperties(prefix = "liquibase")
public record LiquibaseSettings(String changelogPath, String changelogSchema, String defaultSchema) { }
