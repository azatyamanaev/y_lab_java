package ru.ylab.core.testcontainers.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@SpringBootApplication(scanBasePackages = {
        "ru.ylab.core.config.datasource",
        "ru.ylab.core.services.datasource",
        "ru.ylab.core.repositories",
        "ru.ylab.core.settings"},
        exclude = {LiquibaseAutoConfiguration.class})
public class TestDbConfig {
}
