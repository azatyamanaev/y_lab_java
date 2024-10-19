package ru.ylab.settings;

import lombok.Data;

/**
 * Class containing settings for connecting ot database.
 *
 * @author azatyamanaev
 */
@Data
public class DbSettings {

    /**
     * Database url.
     */
    private String url;

    /**
     * Database username.
     */
    private String username;

    /**
     * Database password.
     */
    private String password;
}
