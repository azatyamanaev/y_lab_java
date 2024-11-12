package ru.ylab.exception;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Class representing application Http exception.
 *
 * @author azatayamanaev
 */
@Getter
@Setter
public class HttpException extends BaseException {

    /**
     * Http status.
     */
    private int status;

    /**
     * Creates new HttpException.
     *
     * @param msg    exception message
     * @param cause  exception cause
     * @param error  error data
     * @param status http status
     */
    public HttpException(String msg, Throwable cause, @NotNull Error error, int status) {
        super(msg, cause, error);
        this.status = status;
    }

    /**
     * Creates new bad request exception with validation error message.
     *
     * @return HttpException instance
     */
    public static HttpException validationError() {
        return new HttpException(null, null,
                Error.builder()
                     .message(ErrorConstants.VALIDATION_ERROR)
                     .build(), 400);
    }

    /**
     * Creates new bad request exception with validation error message.
     *
     * @param msg   exception message
     * @param cause exception cause
     * @return HttpException instance
     */
    public static HttpException validationError(String msg, Throwable cause) {
        return new HttpException(msg, cause,
                Error.builder()
                     .message(ErrorConstants.VALIDATION_ERROR)
                     .build(), 400);
    }

    /**
     * Creates new bad request exception with bad request message.
     *
     * @return HttpException instance
     */
    public static HttpException badRequest() {
        return new HttpException(null, null,
                Error.builder()
                     .message(ErrorConstants.BAD_REQUEST)
                     .build(), 400);
    }

    /**
     * Creates new bad request exception with bad request message.
     *
     * @param msg   exception message
     * @param cause exception cause
     * @return HttpException instance
     */
    public static HttpException badRequest(String msg, Throwable cause) {
        return new HttpException(msg, cause,
                Error.builder()
                     .message(ErrorConstants.BAD_REQUEST)
                     .build(), 400);
    }

    /**
     * Creates new unauthorized exception with unauthorized message.
     *
     * @return HttpException instance
     */
    public static HttpException unauthorized() {
        return new HttpException(null, null,
                Error.builder()
                     .message(ErrorConstants.UNAUTHORIZED)
                     .build(), 401);
    }

    /**
     * Creates new unauthorized exception with unauthorized message.
     *
     * @param msg   exception message
     * @param cause exception cause
     * @return HttpException instance
     */
    public static HttpException unauthorized(String msg, Throwable cause) {
        return new HttpException(msg, cause,
                Error.builder()
                     .message(ErrorConstants.UNAUTHORIZED)
                     .build(), 401);
    }

    /**
     * Creates new forbidden exception with forbidden message.
     *
     * @return HttpException instance
     */
    public static HttpException forbidden() {
        return new HttpException(null, null,
                Error.builder()
                     .message(ErrorConstants.FORBIDDEN)
                     .build(), 403);
    }

    /**
     * Creates new forbidden exception with forbidden message.
     *
     * @param msg   exception message
     * @param cause exception cause
     * @return HttpException instance
     */
    public static HttpException forbidden(String msg, Throwable cause) {
        return new HttpException(msg, cause,
                Error.builder()
                     .message(ErrorConstants.FORBIDDEN)
                     .build(), 403);
    }

    /**
     * Creates new method not allowed exception.
     *
     * @return HttpException instance
     */
    public static HttpException methodNotAllowed() {
        return new HttpException(null, null,
                Error.builder()
                     .message(ErrorConstants.METHOD_NOT_ALLOWED)
                     .build(), 405);
    }

    /**
     * Creates new method not allowed exception.
     *
     * @param msg   exception message
     * @param cause exception cause
     * @return HttpException instance
     */
    public static HttpException methodNotAllowed(String msg, Throwable cause) {
        return new HttpException(msg, cause,
                Error.builder()
                     .message(ErrorConstants.METHOD_NOT_ALLOWED)
                     .build(), 405);
    }

    /**
     * Creates new server error exception with database error message.
     *
     * @param msg   exception message
     * @param cause exception cause
     * @return HttpException instance
     */
    public static HttpException databaseAccessError(String msg, Throwable cause) {
        return new HttpException(msg, cause,
                Error.builder()
                     .message(ErrorConstants.DATABASE_ACCESS_ERROR)
                     .build(), 500);
    }

    /**
     * Creates new liquibase error exception.
     *
     * @param msg   exception message
     * @param cause exception cause
     * @return HttpException instance
     */
    public static HttpException liquibaseError(String msg, Throwable cause) {
        return new HttpException(msg, cause,
                Error.builder()
                     .message(ErrorConstants.LIQUIBASE_ERROR)
                     .build(), 500);
    }

    /**
     * Creates new internal server error exception.
     *
     * @param msg   exception message
     * @param cause exception cause
     * @return HttpException instance
     */
    public static HttpException serverError(String msg, Throwable cause) {
        return new HttpException(msg, cause,
                Error.builder()
                     .message(ErrorConstants.INTERNAL_SERVER_ERROR)
                     .build(), 500);
    }
}
