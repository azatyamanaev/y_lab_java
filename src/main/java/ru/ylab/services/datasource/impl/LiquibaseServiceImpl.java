package ru.ylab.services.datasource.impl;

import javax.annotation.PostConstruct;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ylab.exception.HttpException;
import ru.ylab.services.datasource.LiquibaseService;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Service implementing {@link LiquibaseService}
 *
 * @author azatyamanaev
 */
@Service
@RequiredArgsConstructor
public class LiquibaseServiceImpl implements LiquibaseService {

    private final Liquibase liquibase;

    @Override
    public void migrate() {
        try {
            liquibase.update();
        } catch (LiquibaseException e) {
            throw HttpException.liquibaseError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.MIGRATION_ERROR, "liquibase");
        }
    }

    @PostConstruct
    public void init() {
        this.migrate();
    }
}
