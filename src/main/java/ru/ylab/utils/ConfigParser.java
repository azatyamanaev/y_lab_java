package ru.ylab.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import ru.ylab.settings.DbSettings;
import ru.ylab.settings.LiquibaseSettings;

/**
 * Class for parsing application config.
 *
 * @author azatyamanaev
 */
public class ConfigParser {

    /**
     * Path to application config path relative to project root.
     */
    private static final String CONFIG_FILE_PATH = "./src/main/resources/application.properties";

    /**
     * Parses DbSettings instance from config file.
     *
     * @return DbSettings instance
     */
    public static DbSettings parseDbSettings() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(CONFIG_FILE_PATH));

            DbSettings settings = new DbSettings();
            settings.setUrl(properties.getProperty("datasource.url"));
            settings.setUsername(properties.getProperty("datasource.username"));
            settings.setPassword(properties.getProperty("datasource.password"));
            return settings;
        } catch (IOException e) {
            System.out.println("Error when parsing config file.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Parses LiquibaseSettings instance from config file.
     *
     * @return LiquibaseSettings instance
     */
    public static LiquibaseSettings parseLiquibaseSettings() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(CONFIG_FILE_PATH));

            LiquibaseSettings settings = new LiquibaseSettings();
            settings.setLocation(properties.getProperty("liquibase.changelog.path"));
            settings.setChangelogSchema(properties.getProperty("liquibase.changelog.schema"));
            settings.setDefaultSchema(properties.getProperty("liquibase.default.schema"));

            return settings;
        } catch (IOException e) {
            System.out.println("Error when parsing config file.");
            throw new RuntimeException(e);
        }
    }
}
