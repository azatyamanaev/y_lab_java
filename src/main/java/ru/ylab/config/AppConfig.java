package ru.ylab.config;

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
import ru.ylab.config.datasource.LiquibaseConfig;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.settings.LiquibaseSettings;
import ru.ylab.utils.constants.AppConstants;

@Profile(AppConstants.DEV_PROFILE)
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "ru.ylab")
@ConfigurationPropertiesScan("ru.ylab.settings")
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        MutablePropertySources sources = new MutablePropertySources();
        PropertySource<?> propertySource1 = new YmlPropertySourceFactory().createPropertySource("default-yml",
                new EncodedResource(new ClassPathResource("application.yml")));
        sources.addFirst(propertySource1);
        configurer.setPropertySources(sources);
        return configurer;
    }

    @Bean
    public Liquibase liquibase(LiquibaseSettings settings, CPDataSource dataSource) {
        LiquibaseConfig config = new LiquibaseConfig(settings);
        return config.liquibase(dataSource);
    }
}
