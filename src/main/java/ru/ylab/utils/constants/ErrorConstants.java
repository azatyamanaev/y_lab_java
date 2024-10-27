package ru.ylab.utils.constants;

/**
 * Class containing error constants
 *
 * @author azatyamanaev
 */
public interface ErrorConstants {

    String ERROR_HANDLER_NAME = "errorHandler";
    String ERROR_HANDLER_URL = "/error-handler";
    String AUTHORIZATION_EMPTY = "Authorization header missing";
    String NOT_FOUND = "Resource not found";
    String TOKEN_EXPIRED = "Token expired";
    String INVALID_PARAMETER = "Invalid parameter";
    String VALIDATION_ERROR = "Validation error";
    String EMPTY_PARAM = "Empty parameter";
    String ALREADY_EXISTS = "Already exists";
    String NOT_AUTHOR = "Not habit author";

    String SELECT_ERROR = "Select error";
    String CREATE_ERROR = "Create error";
    String UPDATE_ERROR = "Update error";
    String DELETE_ERROR = "Delete error";
    String MIGRATION_ERROR = "Migration error";
    String CONFIGURATION_ERROR = "Configuration error";
    String REGISTER_ERROR = "Register error";
    String MAX_SIZE_REACHED = "Max size reached";

    String LIQUIBASE_ERROR = "Liquibase error";
    String DATABASE_ACCESS_ERROR = "Database access error";
    String BAD_REQUEST = "Bad request";
    String UNAUTHORIZED = "Unauthorized";
    String FORBIDDEN = "Access forbidden";
}
