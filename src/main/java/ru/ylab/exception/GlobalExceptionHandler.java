package ru.ylab.exception;

import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.ylab.utils.constants.ErrorConstants;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Error> baseException(BaseException exception) {
        log.trace("{}. Cause: ", exception.getClass().getSimpleName(), exception);
        return ResponseEntity.status(exception.getStatus())
                             .body(exception.getError());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> bindException(BindException exception) {
        log.trace("Validation exception. Cause: ", exception);
        return ResponseEntity.badRequest()
                             .body(Error.builder()
                                        .message(ErrorConstants.VALIDATION_ERROR)
                                        .details(extractDetails(exception.getBindingResult()))
                                        .build());
    }

    private List<ErrorDetail> extractDetails(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                            .stream()
                            .map(this::extractDetail)
                            .collect(Collectors.toList());
    }

    private ErrorDetail extractDetail(FieldError fieldError) {
        return ErrorDetail.builder()
                          .type(fieldError.getCode())
                          .target(fieldError.getField())
                          .build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> invalidRequestBody(HttpMessageNotReadableException exception, HttpServletRequest request) {
        log.warn("Failed to read {} request {} body",
                request.getMethod(),
                request.getRequestURI(),
                exception);

        return ResponseEntity.badRequest()
                             .body(Error.builder()
                                        .message(ErrorConstants.BAD_REQUEST)
                                        .details(List.of(ErrorDetail.builder()
                                                                    .type(ErrorConstants.INVALID_PARAMETER)
                                                                    .target("request body")
                                                                    .build()))
                                        .build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> invalidArgumentException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        log.warn("Failed to convert to correct type {} request {} parameter",
                request.getMethod(),
                request.getRequestURI(),
                exception);
        return ResponseEntity.badRequest()
                             .body(Error.builder()
                                        .message(ErrorConstants.BAD_REQUEST)
                                        .details(List.of(ErrorDetail.builder()
                                                                    .type(ErrorConstants.INVALID_PARAMETER)
                                                                    .target("request parameter")
                                                                    .build()))
                                        .build());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> expiredJwtException(ExpiredJwtException exception) {
        log.trace("Expired jwt exception. Cause: ", exception);
        return ResponseEntity.badRequest()
                             .body(Error.builder()
                                        .message(ErrorConstants.BAD_REQUEST)
                                        .details(List.of(ErrorDetail.builder()
                                                                    .type(ErrorConstants.TOKEN_EXPIRED)
                                                                    .target("access token")
                                                                    .build()))
                                        .build());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> serverException(Exception exception, HttpServletRequest request) {
        log.error("Failed to process {} request {}", request.getMethod(), request.getRequestURI(), exception);
        return ResponseEntity.internalServerError()
                             .body(Error.builder()
                                        .message(ErrorConstants.INTERNAL_SERVER_ERROR)
                                        .details(List.of(ErrorDetail.builder()
                                                                    .type(exception.getMessage())
                                                                    .build()))
                                        .build());
    }
}
