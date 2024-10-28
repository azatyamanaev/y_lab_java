package ru.ylab.web.servlets;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.ylab.config.AppContext;
import ru.ylab.exception.Error;
import ru.ylab.exception.HttpException;
import ru.ylab.utils.constants.ErrorConstants;

import static jakarta.servlet.RequestDispatcher.ERROR_EXCEPTION;

/**
 * Servlet for sending error responses.
 *
 * @author azatyamanaev
 */
@Slf4j
@WebServlet(name = ErrorConstants.ERROR_HANDLER_NAME, urlPatterns = ErrorConstants.ERROR_HANDLER_URL)
public class ErrorHandler extends HttpServlet implements HttpRequestHandler {

    /**
     * Instance of an {@link ObjectMapper}.
     */
    private ObjectMapper mapper;

    @Override
    public void init() {
        ServletContext context = this.getServletContext();
        AppContext appContext = (AppContext) context.getAttribute("appContext");

        mapper = appContext.getMappersConfig().getMapper();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Error error = new Error();

        int status;
        Exception exception = (Exception) req.getAttribute(ERROR_EXCEPTION);
        if (exception != null) {
            if (exception instanceof HttpException) {
                HttpException httpException = (HttpException) exception;
                error = httpException.getError();
                status = httpException.getStatus();
            } else {
                error.setMessage(exception.getMessage());
                status = resp.getStatus();
            }
        } else {
            status = resp.getStatus();
            switch (status) {
                case HttpServletResponse.SC_NOT_FOUND:
                    error.setMessage(ErrorConstants.NOT_FOUND);
                    break;
                case HttpServletResponse.SC_METHOD_NOT_ALLOWED:
                    error.setMessage(ErrorConstants.METHOD_NOT_ALLOWED);
                    break;
                default:
                    error.setMessage(ErrorConstants.INTERNAL_SERVER_ERROR);

            }

        }

        log.error("Error with status {} and message {}", status, error.getMessage());
        setResponse(resp, status, mapper.writeValueAsString(error));
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
