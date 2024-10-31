package ru.ylab.settings;

import lombok.Getter;
import lombok.Setter;

/**
 * Class containing settings for connecting ot database.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
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
