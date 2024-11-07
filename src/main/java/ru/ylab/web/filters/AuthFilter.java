package ru.ylab.web.filters;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import ru.ylab.exception.HttpException;
import ru.ylab.models.User;
import ru.ylab.services.auth.JWToken;
import ru.ylab.services.auth.JwtService;
import ru.ylab.services.entities.UserService;
import ru.ylab.utils.constants.ErrorConstants;
import ru.ylab.utils.constants.WebConstants;

import static jakarta.servlet.RequestDispatcher.ERROR_EXCEPTION;
import static ru.ylab.utils.constants.WebConstants.ADMIN_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

/**
 * Filter for checking Authorization header.
 *
 * @author azatyamanaev
 */
@Log4j2
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            String uri = request.getRequestURI();
            uri = uri.substring(uri.lastIndexOf(WebConstants.APP_CONTEXT_PATH) + WebConstants.APP_CONTEXT_PATH.length());

            HttpServletResponse response = (HttpServletResponse) resp;
            String authorization = request.getHeader("Authorization");

            if (StringUtils.isBlank(authorization)) {
                throw HttpException.unauthorized().addDetail(ErrorConstants.EMPTY_PARAM, "Authorization Header");
            } else if (!authorization.startsWith(WebConstants.JWTOKEN_PREFIX)) {
                throw HttpException.unauthorized().addDetail(ErrorConstants.INVALID_PARAM, "Authorization Header");
            } else {
                authorization = authorization.substring(WebConstants.JWTOKEN_PREFIX.length());
                JWToken token = jwtService.parse(authorization);
                User.Role role = token.getRole();

                if (uri.startsWith(ADMIN_URL) && role.equals(User.Role.USER) ||
                        uri.startsWith(USER_URL) && role.equals(User.Role.ADMIN)) {
                    throw HttpException.forbidden();
                } else {
                    User user = userService.getByEmail(token.getUsername());
                    if (user == null) {
                        throw HttpException.badRequest().addDetail(ErrorConstants.NOT_FOUND, "user");
                    } else {
                        request.setAttribute("currentUser", user);
                        log.info("User {} authorized", token.getUsername());
                        chain.doFilter(req, resp);
                    }
                }
            }
        } catch (Exception e) {
            req.setAttribute(ERROR_EXCEPTION, e);
            req.getRequestDispatcher(ErrorConstants.ERROR_HANDLER_URL).forward(req, resp);
        }
    }
}
