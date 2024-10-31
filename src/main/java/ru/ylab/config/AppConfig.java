package ru.ylab.config;

import liquibase.Liquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.yaml.snakeyaml.Yaml;
import ru.ylab.config.datasource.LiquibaseConfig;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.settings.DbSettings;
import ru.ylab.settings.LiquibaseSettings;

@Configuration
@EnableAspectJAutoProxy
@PropertySource(value = "classpath:application.yml", factory = YmlPropertySourceFactory.class)
@ComponentScan(basePackages = "ru.ylab")
public class AppConfig {

    @Bean
    public Yaml yaml() {
        return new Yaml();
    }

    @Bean
    public DbSettings dbSettings(Yaml yaml, Environment env) {
        String host = System.getenv("DB_HOST");
        String port = System.getenv("DB_PORT");
        String dbName = System.getenv("DB_NAME");

        String url = env.getProperty("datasource.url");
        url = url.replace("{DB_HOST}", host);
        url = url.replace("{DB_PORT}", port);
        url = url.replace("{DB_NAME}", dbName);

        return new DbSettings(
                url,
                env.getProperty("datasource.username"),
                env.getProperty("datasource.password"));
    }

    @Bean
    public LiquibaseSettings liquibaseSettings(Environment env) {
        return new LiquibaseSettings(
                env.getProperty("liquibase.changelog.path"),
                env.getProperty("liquibase.changelog.schema"),
                env.getProperty("liquibase.default.schema"));
    }

    @Bean
    public Liquibase liquibase(LiquibaseSettings settings, CPDataSource dataSource) {
        LiquibaseConfig config = new LiquibaseConfig(settings);
        return config.liquibase(dataSource);
    }
}
