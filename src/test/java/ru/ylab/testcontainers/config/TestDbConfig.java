package ru.ylab.testcontainers.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@SpringBootApplication(scanBasePackages = {
        "ru.ylab.config.datasource",
        "ru.ylab.services.datasource",
        "ru.ylab.repositories",
        "ru.ylab.settings"},
        exclude = {LiquibaseAutoConfiguration.class})
public class TestDbConfig {
}
