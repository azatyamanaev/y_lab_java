package ru.ylab.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import ru.ylab.settings.DbSettings;
import ru.ylab.settings.LiquibaseSettings;
import ru.ylab.utils.constants.AppConstants;

/**
 * Class for parsing application config.
 *
 * @author azatyamanaev
 */
public class ConfigParser {

    /**
     * Path to application config path relative to project root.
     */
    private static final String DOCKER_CONFIG_PATH = "/usr/local/tomcat/webapps/habits-app/WEB-INF/classes/application.properties";
    private static final String LOCAL_CONFIG_PATH = "./src/main/resources/application.properties";
    private static final String TEST_CONFIG_PATH = "./src/test/resources/application.properties";

    /**
     * Parses DbSettings instance from config file.
     *
     * @param profile app profile
     * @return DbSettings instance
     */
    public static DbSettings parseDbSettings(String profile) {
        try {
            Properties properties = new Properties();
            loadProperties(properties, profile);
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
     * @param profile app profile
     * @return LiquibaseSettings instance
     */
    public static LiquibaseSettings parseLiquibaseSettings(String profile) {
        try {
            Properties properties = new Properties();
            loadProperties(properties, profile);
            return new LiquibaseSettings(
                    properties.getProperty("liquibase.changelog.path"),
                    properties.getProperty("liquibase.changelog.schema"),
                    properties.getProperty("liquibase.default.schema"));
        } catch (IOException e) {
            System.out.println("Error when parsing config file.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads properties according to profile.
     *
     * @param properties properties instance
     * @param profile app profile
     * @throws IOException if file read error occurs
     */
    public static void loadProperties(Properties properties, String profile) throws IOException {
        switch (profile) {
            case AppConstants.DEV_PROFILE:
                String currentDir = System.getProperty("user.dir");
                if (currentDir.contains("y_lab_java")) {
                    properties.load(new FileReader(LOCAL_CONFIG_PATH));
                } else if (currentDir.contains("tomcat")){
                    properties.load(new FileReader(DOCKER_CONFIG_PATH));
                }
                break;
            case AppConstants.TEST_PROFILE:
                properties.load(new FileReader(TEST_CONFIG_PATH));
                break;
        }
    }
}
