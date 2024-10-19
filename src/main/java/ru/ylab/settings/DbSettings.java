package ru.ylab.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class containing settings for connecting ot database.
 *
 * @author azatyamanaev
 */
@Builder
@Data
@NoArgsConstructor
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
