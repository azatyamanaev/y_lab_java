package ru.ylab.services.datasource.impl;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import ru.ylab.exception.HttpException;
import ru.ylab.services.datasource.LiquibaseService;
import ru.ylab.utils.constants.ErrorConstants;

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
            throw HttpException.liquibaseError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.MIGRATION_ERROR, "liquibase");
        }
    }
}
