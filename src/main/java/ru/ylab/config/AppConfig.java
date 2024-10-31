package ru.ylab.config;

import liquibase.Liquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.ylab.config.datasource.LiquibaseConfig;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.settings.DbSettings;
import ru.ylab.settings.LiquibaseSettings;
import ru.ylab.utils.ConfigParser;

@Configuration
@ComponentScan(basePackages = "ru.ylab")
public class AppConfig {

    @Bean
    public DbSettings dbSettings() {
        return ConfigParser.parseDbSettings("dev");
    }

    @Bean
    public LiquibaseSettings liquibaseSettings() {
        return ConfigParser.parseLiquibaseSettings("dev");
    }

    @Bean
    public Liquibase liquibase(LiquibaseSettings settings, CPDataSource dataSource) {
        LiquibaseConfig config = new LiquibaseConfig(settings);
        return config.liquibase(dataSource);
    }
}
