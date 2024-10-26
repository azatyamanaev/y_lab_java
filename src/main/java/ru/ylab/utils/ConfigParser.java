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
    private static final String CONFIG_FILE_PATH = "/usr/local/tomcat/webapps/habits-app/WEB-INF/classes/application.properties";

    /**
     * Parses DbSettings instance from config file.
     *
     * @return DbSettings instance
     */
    public static DbSettings parseDbSettings() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(CONFIG_FILE_PATH));

            return new DbSettings(
                    properties.getProperty("datasource.url"),
                    properties.getProperty("datasource.username"),
                    properties.getProperty("datasource.password"));
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

            return new LiquibaseSettings(
                    properties.getProperty("liquibase.changelog.path"),
                    properties.getProperty("liquibase.changelog.schema"),
                    properties.getProperty("liquibase.default.schema"));
        } catch (IOException e) {
            System.out.println("Error when parsing config file.");
            throw new RuntimeException(e);
        }
    }
}
