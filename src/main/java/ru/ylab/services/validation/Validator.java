package ru.ylab.services.validation;

import ru.ylab.exception.BaseException;
import ru.ylab.exception.HttpException;
import ru.ylab.utils.RegexMatcher;
import ru.ylab.utils.StringUtil;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Interface describing DTO validation logic.
 *
 * @param <T> DTO class to validate
 * @author azatyamanaev
 */
public interface Validator<T> {

    /**
     * Validates incoming DTO.
     *
     * @param data DTO data
     * @throws HttpException if error occurs during validation.
     */
    void validate(T data);

    /**
     * Checks if object is null.
     *
     * @param data object to check
     * @throws HttpException if object is null
     */
    default void isEmpty(T data) {
        if (data == null) {
            throw HttpException.validationError().addDetail(ErrorConstants.EMPTY_PARAM, "request body");
        }
    }

    /**
     * Checks whether string is empty and if so, adds detail to exception.
     *
     * @param value string to check
     * @param field field name
     * @param exception exception to which detail will be added
     * @return whether string is empty
     */
    default boolean isEmptyString(String value, String field, BaseException exception) {
        if (StringUtil.isEmpty(value)) {
            exception.addDetail(ErrorConstants.EMPTY_PARAM, field);
            return true;
        }
        return false;
    }

    /**
     * Checks whether email is valid.
     * If email is invalid, adds detail to exception
     *
     * @param email email to check
     * @param exception exception to which detail will be added
     * @return whether email is valid
     */
    default boolean validEmail(String email, BaseException exception) {
        if (email == null || RegexMatcher.matchEmail(email)) {
            return true;
        } else {
            exception.addDetail(ErrorConstants.INVALID_PARAMETER, "email");
            return false;
        }
    }
}
