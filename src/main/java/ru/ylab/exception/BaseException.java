package ru.ylab.exception;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Class representing application exception.
 *
 * @author azatyamanaev
 */
@Getter
@Setter
public abstract class BaseException extends RuntimeException {

    /**
     * Exception error data.
     */
    private Error error;

    /**
     * Creates new BaseException.
     *
     * @param error error data
     */
    public BaseException(@NotNull Error error) {
        this.error = error;
    }

    /**
     * Adds details to exception error.
     *
     * @param type error type
     * @param target what object or field error occurred on
     * @return this instance
     */
    public BaseException addDetail(String type, String target) {
        if (error == null) {
            error = Error.builder()
                    .message(ErrorConstants.VALIDATION_ERROR)
                         .build();
        }
        error.getDetails().add(new ErrorDetail(type, target));
        return this;
    }

    /**
     * Checks if error details is empty.
     *
     * @return whether error details is empty
     */
    public boolean isEmpty() {
        return error.getDetails().isEmpty();
    }

    /**
     * Throws exception if error details is not empty
     */
    public void throwIfErrorsNotEmpty() {
        if (!isEmpty()) {
            throw this;
        }
    }
}
