package ru.ylab.services.auth.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.extern.slf4j.Slf4j;
import ru.ylab.services.auth.PasswordService;

/**
 * Class implementing {@link PasswordService}.
 *
 * @author azatyamanaev
 */
@Slf4j
public class PasswordServiceImpl implements PasswordService {

    @Override
    public String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(8, password.toCharArray());
    }

    @Override
    public boolean verifyPassword(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }
}
