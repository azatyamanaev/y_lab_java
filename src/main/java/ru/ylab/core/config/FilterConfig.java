package ru.ylab.core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ylab.core.services.auth.JwtService;
import ru.ylab.core.services.entities.UserService;
import ru.ylab.core.web.filters.AuthFilter;

import static ru.ylab.core.utils.constants.WebConstants.ADMIN_URL;
import static ru.ylab.core.utils.constants.WebConstants.USER_URL;

/**
 * Class containing filter configuration.
 *
 * @author azatyamanaev
 */
@Configuration
public class FilterConfig {

    /**
     * Adds {@link AuthFilter} for reading Authorization header.
     *
     * @param jwtService {@link JwtService} instance
     * @param userService {@link UserService} instance
     * @return {@link FilterRegistrationBean} instance configured for {@link AuthFilter}
     */
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilter(JwtService jwtService, UserService userService) {
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AuthFilter(jwtService, userService));
        registration.addUrlPatterns(ADMIN_URL + "/*", USER_URL + "/*");
        registration.setName("authFilter");
        registration.setOrder(1);
        return registration;
    }
}
