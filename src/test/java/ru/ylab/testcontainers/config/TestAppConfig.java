package ru.ylab.testcontainers.config;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import liquibase.Liquibase;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import ru.ylab.config.YmlPropertySourceFactory;
import ru.ylab.config.datasource.LiquibaseConfig;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.settings.LiquibaseSettings;
import ru.ylab.utils.constants.AppConstants;

@Profile(AppConstants.TEST_PROFILE)
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "ru.ylab")
@ConfigurationPropertiesScan("ru.ylab.settings")
public class TestAppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        MutablePropertySources sources = new MutablePropertySources();
        PropertySource<?> propertySource1 = new YmlPropertySourceFactory().createPropertySource("default-yml",
                new EncodedResource(new ClassPathResource("application.yml")));
        PropertySource<?> propertySource2 = new YmlPropertySourceFactory().createPropertySource("test-yml",
                new EncodedResource(new ClassPathResource("application-test.yml")));
        sources.addFirst(propertySource1);
        sources.addFirst(propertySource2);
        configurer.setPropertySources(sources);
        return configurer;
    }

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
    public Liquibase liquibase(LiquibaseSettings settings, CPDataSource dataSource) {
        LiquibaseConfig config = new LiquibaseConfig(settings);
        return config.liquibase(dataSource);
    }
}
