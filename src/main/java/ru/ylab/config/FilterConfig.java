package ru.ylab.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ylab.services.auth.JwtService;
import ru.ylab.services.entities.UserService;
import ru.ylab.web.filters.AuthFilter;

import static ru.ylab.utils.constants.WebConstants.ADMIN_URL;
import static ru.ylab.utils.constants.WebConstants.USER_URL;

@Configuration
public class FilterConfig {

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
