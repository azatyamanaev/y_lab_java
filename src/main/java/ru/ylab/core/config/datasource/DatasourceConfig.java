package ru.ylab.core.config.datasource;

import javax.sql.DataSource;

import liquibase.Liquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ylab.core.services.datasource.impl.BasicCPDataSource;
import ru.ylab.core.settings.DbSettings;
import ru.ylab.core.settings.LiquibaseSettings;

@Configuration
public class DatasourceConfig {

    @Bean
    public Liquibase liquibase(LiquibaseSettings settings, DataSource dataSource) {
        LiquibaseConfig config = new LiquibaseConfig(settings);
        return config.liquibase(dataSource);
    }

    @Bean
    public DataSource dataSource(DbSettings settings) {
        BasicCPDataSource dataSource = new BasicCPDataSource(settings.url(), settings.username(), settings.password());
        dataSource.setAutoCommit(false);
        return dataSource;
    }
}
