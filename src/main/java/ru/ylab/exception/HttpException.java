package ru.ylab.exception;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
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
     * @param error error data
     * @param status http status
     */
    public HttpException(@NotNull Error error, int status) {
        super(error);
        this.status = status;
    }

    /**
     * Creates new bad request exception with validation error message.
     *
     * @return HttpException instance
     */
    public static HttpException validationError() {
        return new HttpException(Error.builder()
                                      .message(ErrorConstants.VALIDATION_ERROR)
                                      .build(), 400);
    }

    /**
     * Creates new bad request exception with bad request message.
     *
     * @return HttpException instance
     */
    public static HttpException badRequest() {
        return new HttpException(Error.builder()
                                      .message(ErrorConstants.BAD_REQUEST)
                                      .build(), 400);
    }

    /**
     * Creates new unauthorized exception with unauthorized message.
     *
     * @return HttpException instance
     */
    public static HttpException unauthorized() {
        return new HttpException(Error.builder()
                                      .message(ErrorConstants.UNAUTHORIZED)
                                      .build(), 401);
    }

    /**
     * Creates new forbidden exception with forbidden message.
     *
     * @return HttpException instance
     */
    public static HttpException forbidden() {
        return new HttpException(Error.builder()
                                      .message(ErrorConstants.FORBIDDEN)
                                      .build(), 403);
    }

    /**
     * Creates new method not allowed exception.
     *
     * @return HttpException instance
     */
    public static HttpException methodNotAllowed() {
        return new HttpException(Error.builder()
                                      .message(ErrorConstants.METHOD_NOT_ALLOWED)
                                      .build(), 405);
    }

    /**
     * Creates new server error exception with database error message.
     *
     * @return HttpException instance
     */
    public static HttpException databaseAccessError() {
        return new HttpException(Error.builder()
                                      .message(ErrorConstants.DATABASE_ACCESS_ERROR)
                                      .build(), 500);
    }

    /**
     * Creates new server error exception.
     *
     * @return HttpException instance
     */
    public static HttpException serverError(String message) {
        return new HttpException(Error.builder()
                                      .message(message)
                                      .build(), 500);
    }
}
