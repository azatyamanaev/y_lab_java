package ru.ylab.settings;

/**
 * Class containing Liquibase settings.
 *
 * @param location Path to changelog.xml file.
 * @param changelogSchema Default schema for liquibase.
 * @param defaultSchema Default schema for entities.
 * @author azatyamanaev
 */
public record LiquibaseSettings(String location, String changelogSchema, String defaultSchema) { }
