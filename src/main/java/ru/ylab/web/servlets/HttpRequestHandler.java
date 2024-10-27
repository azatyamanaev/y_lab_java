package ru.ylab.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Interface describing basic methods for handling HTTP requests.
 *
 * @author azatyamanaev
 */
public interface HttpRequestHandler {

    /**
     * Sets response content for servlet response.
     *
     * @param resp http response
     * @param status response status
     * @param value response value
     * @throws IOException if getting PrintWriter throws an exception
     */
    default void setResponse(HttpServletResponse resp, int status, String value) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(value);
        out.flush();
    }
}
