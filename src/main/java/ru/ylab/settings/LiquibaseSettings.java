package ru.ylab.settings;

/**
 * Class containing Liquibase settings.
 *
 * @param changelogPath Path to changelog.xml file.
 * @param changelogSchema Default schema for liquibase.
 * @param defaultSchema Default schema for entities.
 * @author azatyamanaev
 */
public record LiquibaseSettings(String changelogPath, String changelogSchema, String defaultSchema) { }
