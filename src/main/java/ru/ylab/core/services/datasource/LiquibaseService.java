package ru.ylab.core.services.datasource;

/**
 * Interface describing logic for working with Liquibase.
 *
 * @author azatyamanaev
 */
public interface LiquibaseService {

    /**
     * Migrates database according to changelog.xml file.
     */
    void migrate();
}
