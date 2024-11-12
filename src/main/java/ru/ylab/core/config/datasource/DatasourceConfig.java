package ru.ylab.core.config.datasource;

import javax.sql.DataSource;

import liquibase.Liquibase;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.ylab.core.services.datasource.impl.BasicCPDataSource;
import ru.ylab.core.settings.DbSettings;
import ru.ylab.core.settings.LiquibaseSettings;

/**
 * Class containing dataSource configuration.
 *
 * @author azatyamanaev
 */
@Configuration
@ConfigurationPropertiesScan("ru.ylab.core.settings")
public class DatasourceConfig {

    /**
     * Creates {@link Liquibase} bean.
     *
     * @return {@link Liquibase} bean
     */
    @Bean
    @Primary
    public Liquibase liquibase(LiquibaseSettings settings, DataSource dataSource) {
        LiquibaseConfig config = new LiquibaseConfig(settings);
        return config.liquibase(dataSource);
    }

    /**
     * Creates {@link DataSource} bean from {@link BasicCPDataSource} instance.
     *
     * @param settings database connection settings
     * @return {@link DataSource} bean
     */
    @Bean
    public DataSource customDataSource(DbSettings settings) {
        BasicCPDataSource dataSource = new BasicCPDataSource(settings.url(), settings.username(), settings.password());
        dataSource.setAutoCommit(false);
        return dataSource;
    }
}
