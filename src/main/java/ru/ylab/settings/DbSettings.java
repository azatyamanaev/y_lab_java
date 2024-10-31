package ru.ylab.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class containing settings for connecting ot database.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
@AllArgsConstructor
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
