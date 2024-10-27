package ru.ylab.web.filters;

import java.io.IOException;
import java.time.Instant;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.ylab.config.AppContext;
import ru.ylab.exception.HttpException;
import ru.ylab.models.User;
import ru.ylab.services.auth.JWToken;
import ru.ylab.services.auth.JwtService;
import ru.ylab.services.entities.UserService;
import ru.ylab.utils.StringUtil;
import ru.ylab.utils.constants.ErrorConstants;
import ru.ylab.utils.constants.WebConstants;

import static ru.ylab.utils.constants.WebConstants.ADMIN_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

/**
 * Filter for checking Authorization header.
 *
 * @author azatyamanaev
 */
@Slf4j
@WebFilter(filterName = "authFilter",
        urlPatterns = {USER_URL + "/*", ADMIN_URL + "/*"})
public class AuthFilter implements Filter {

    /**
     * Instance of a {@link JwtService}.
     */
    private JwtService jwtService;

    /**
     * Instance of an {@link UserService}.
     */
    private UserService userService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        AppContext appContext = (AppContext) context.getAttribute("appContext");

        this.jwtService = appContext.getServicesConfig().getJwtService();
        this.userService = appContext.getServicesConfig().getUserService();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        uri = uri.substring(uri.lastIndexOf(WebConstants.APP_CONTEXT_PATH) + WebConstants.APP_CONTEXT_PATH.length());

        log.info("Checking authorization header");

        HttpServletResponse response = (HttpServletResponse) resp;
        String authorization = request.getHeader("Authorization");
        if (StringUtil.isEmpty(authorization)) {
            throw HttpException.unauthorized();
        } else {
            JWToken token = jwtService.parse(authorization);
            if (!token.getExpires().isAfter(Instant.now())) {
                throw HttpException.badRequest().addDetail(ErrorConstants.TOKEN_EXPIRED, "access token");
            } else {
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
        }
    }
}