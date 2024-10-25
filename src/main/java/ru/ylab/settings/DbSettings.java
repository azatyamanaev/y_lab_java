package ru.ylab.settings;

/**
 * Class containing settings for connecting ot database.
 *
 * @param url Database url.
 * @param username Database username.
 * @param password Database password.
 * @author azatyamanaev
 */
public record DbSettings(String url, String username, String password) { }
