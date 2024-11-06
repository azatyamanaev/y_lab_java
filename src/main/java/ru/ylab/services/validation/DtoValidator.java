package ru.ylab.services.validation;

import ru.ylab.utils.RegexMatcher;
import ru.ylab.utils.StringUtil;

/**
 * Interface describing basic validation methods.
 *
 * @author azatyamanaev
 */
public interface DtoValidator {

    /**
     * Checks whether string is empty.
     *
     * @param value string to check
     * @return whether string is empty
     */
    default boolean isEmptyString(String value) {
        return StringUtil.isEmpty(value);
    }

    /**
     * Checks whether email is valid.
     *
     * @param email email to check
     * @return whether email is valid
     */
    default boolean validEmail(String email) {
        return RegexMatcher.matchEmail(email);
    }
}
