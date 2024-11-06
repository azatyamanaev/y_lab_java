package ru.ylab.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Class containing settings for connecting ot database.
 *
 * @param url Database url.
 * @param username Database username.
 * @param password Database password.
 * @author azatyamanaev
 */
@ConfigurationProperties(prefix = "datasource")
public record DbSettings(String url, String username, String password) { }
