package ru.ylab.services.datasource.impl;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import ru.ylab.services.datasource.LiquibaseService;

/**
 * Service implementing {@link LiquibaseService}
 *
 * @author azatyamanaev
 */
public class LiquibaseServiceImpl implements LiquibaseService {

    /**
     * Liquibase instance.
     */
    private final Liquibase liquibase;

    /**
     * Creates new LiquibaseServiceImpl.
     *
     * @param liquibase Liquibase instance
     */
    public LiquibaseServiceImpl(Liquibase liquibase) {
        this.liquibase = liquibase;
    }

    @Override
    public void migrate() {
        try {
            liquibase.update();
        } catch (LiquibaseException e) {
            System.out.println("Error when migrating database " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
