package ru.ylab.config.datasource;

import javax.sql.DataSource;

import liquibase.Liquibase;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.ylab.services.datasource.impl.BasicCPDataSource;
import ru.ylab.settings.DbSettings;
import ru.ylab.settings.LiquibaseSettings;

/**
 * Class containing dataSource configuration.
 *
 * @author azatyamanaev
 */
@Configuration
@ConfigurationPropertiesScan("ru.ylab.settings")
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
