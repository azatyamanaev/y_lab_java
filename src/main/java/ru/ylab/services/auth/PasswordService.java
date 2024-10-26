package ru.ylab.services.auth;

/**
 * Interface describing logic for hashing and verifying passwords.
 *
 * @author azatyamanaev
 */
public interface PasswordService {

    /**
     * Hashes password.
     *
     * @param password password to hash
     * @return hashed password
     */
    String hashPassword(String password);

    /**
     * Verifies password against hash.
     *
     * @param password password to verify
     * @param hashedPassword hash to verify against
     * @return whether password matches hash
     */
    boolean verifyPassword(String password, String hashedPassword);
}
