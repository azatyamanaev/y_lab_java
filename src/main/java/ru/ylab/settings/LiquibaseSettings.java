package ru.ylab.settings;

import lombok.Data;

/**
 * Class containing Liquibase settings.
 *
 * @author azatyamanaev
 */
@Data
public class LiquibaseSettings {

    /**
     * Path to changelog.xml file.
     */
    private String location;

    /**
     * Default schema for liquibase.
     */
    private String changelogSchema;

    /**
     * Default schema for entities.
     */
    private String defaultSchema;
}
