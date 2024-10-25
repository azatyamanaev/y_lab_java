package ru.ylab.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing user.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * User id.
     */
    private Long id;

    /**
     * User name.
     */
    private String name;

    /**
     * User email.
     */
    private String email;

    /**
     * User password.
     */
    private String password;

    /**
     * User role.
     */
    private Role role;

    /**
     * Translates User instance to string.
     *
     * @return User instance in string format
     */
    @Override
    public String toString() {
        return "User{ id: " + id + ", name: " + name + ", email: " + email + ", role: " + role + " }";
    }

    /**
     * Enum for all user roles.
     */
    public enum Role {

        /**
         * Role USER. User can edit his profile and manage(view, edit, complete, see statistics) his habits.
         */
        USER,

        /**
         * Role ADMIN. Admin can view habits and manage(create, delete) users.
         */
        ADMIN
    }
}
