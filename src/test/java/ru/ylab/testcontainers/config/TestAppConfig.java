package ru.ylab.testcontainers.config;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import liquibase.Liquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import ru.ylab.config.YmlPropertySourceFactory;
import ru.ylab.config.datasource.LiquibaseConfig;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.settings.DbSettings;
import ru.ylab.settings.LiquibaseSettings;
import ru.ylab.utils.constants.AppConstants;

@Profile(AppConstants.TEST_PROFILE)
@Configuration
@EnableAspectJAutoProxy
@PropertySources({
        @PropertySource(value = "classpath:application.yml", factory = YmlPropertySourceFactory.class),
        @PropertySource(value = "classpath:application-test.yml", factory = YmlPropertySourceFactory.class)
})
@ComponentScan("ru.ylab")
public class TestAppConfig {

    @Bean("mapper")
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

    @Bean
    public DbSettings dbSettings(Environment env) {
        return new DbSettings(
                env.getProperty("datasource.url"),
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
