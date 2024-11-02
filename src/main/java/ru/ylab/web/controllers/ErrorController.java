package ru.ylab.web.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ylab.utils.constants.ErrorConstants;

import static jakarta.servlet.RequestDispatcher.ERROR_EXCEPTION;

/**
 * Controller for tomcat HTTP request errors.
 *
 * @author azatyamanaev
 */
@RestController
public class ErrorController {

    @RequestMapping(ErrorConstants.ERROR_HANDLER_URL)
    public void handleError(HttpServletRequest request) throws Throwable {
        if (request.getAttribute(ERROR_EXCEPTION) != null) {
            throw (Throwable) request.getAttribute(ERROR_EXCEPTION);
        }
    }
}
