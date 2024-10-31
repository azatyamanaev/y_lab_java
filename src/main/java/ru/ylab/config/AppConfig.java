package ru.ylab.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import liquibase.Liquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.yaml.snakeyaml.Yaml;
import ru.ylab.config.datasource.LiquibaseConfig;
import ru.ylab.exception.HttpException;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.settings.DbSettings;
import ru.ylab.settings.LiquibaseSettings;
import ru.ylab.utils.constants.ErrorConstants;

@Slf4j
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "ru.ylab")
public class AppConfig {

    @Bean
    public Yaml yaml() {
        return new Yaml();
    }

    @Bean
    public DbSettings dbSettings(Yaml yaml) {
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream("application.yml");) {
            Map<String, Object> map = yaml.load(stream);

            Map<String, String> datasource = (Map<String, String>) map.get("datasource");

            String host = System.getenv("DB_HOST");
            String port = System.getenv("DB_PORT");
            String dbName = System.getenv("DB_NAME");

            String url = datasource.get("url");
            url = url.replace("{DB_HOST}", host);
            url = url.replace("{DB_PORT}", port);
            url = url.replace("{DB_NAME}", dbName);

            DbSettings settings = new DbSettings();
            settings.setUrl(url);
            settings.setUsername(datasource.get("username"));
            settings.setPassword(datasource.get("password"));

            return settings;
        } catch (IOException e) {
            throw HttpException.serverError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.PARSE_ERROR, "application.yml");
        }
    }

    @Bean
    public LiquibaseSettings liquibaseSettings(Yaml yaml) {
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream("application.yml");) {
            Map<String, Object> map = yaml.load(stream);
            Map<String, String> liquibase = (Map<String, String>) map.get("liquibase");

            LiquibaseSettings settings = new LiquibaseSettings(
                    liquibase.get("changelogPath"),
                    liquibase.get("changelogSchema"),
                    liquibase.get("defaultSchema"));
            return settings;
        } catch (IOException e) {
            throw HttpException.serverError(e.getMessage(), e.getCause())
                               .addDetail(ErrorConstants.PARSE_ERROR, "application.yml");
        }
    }

    @Bean
    public Liquibase liquibase(LiquibaseSettings settings, CPDataSource dataSource) {
        LiquibaseConfig config = new LiquibaseConfig(settings);
        return config.liquibase(dataSource);
    }
}
