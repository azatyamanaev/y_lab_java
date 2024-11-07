package ru.ylab.testcontainers.config;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import ru.ylab.models.User;
import ru.ylab.repositories.RefreshTokenRepository;
import ru.ylab.services.auth.JWToken;
import ru.ylab.services.auth.JwtService;
import ru.ylab.services.auth.impl.JwtServiceImpl;
import ru.ylab.services.entities.UserService;
import ru.ylab.settings.JwtSettings;
import ru.ylab.utils.constants.AppConstants;
import ru.ylab.utils.constants.WebConstants;

import static org.mockito.Mockito.when;

@Profile(AppConstants.TEST_PROFILE)
@Configuration
public class TestAppConfig {

    @Bean("mapper")
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

    @Bean
    @Primary
    public JwtService jwtService(RefreshTokenRepository refreshTokenRepository,
                                 UserService userService, JwtSettings jwtSettings) {
        JwtService jwtService = Mockito.spy(JwtService.class);

        User user = TestConfigurer.getTestUser();
        when(jwtService.parse(WebConstants.JWTOKEN_USER_ACCESS))
                .thenReturn(new JWToken(UUID.randomUUID().toString(),
                        user.getEmail(),
                        user.getRole(),
                        Instant.now().minus(1, ChronoUnit.DAYS),
                        Instant.now().plus(1, ChronoUnit.DAYS)));

        User admin = TestConfigurer.getTestUser();
        when(jwtService.parse(WebConstants.JWTOKEN_ADMIN_ACCESS))
                .thenReturn(new JWToken(UUID.randomUUID().toString(),
                        admin.getEmail(),
                        admin.getRole(),
                        Instant.now().minus(1, ChronoUnit.DAYS),
                        Instant.now().plus(1, ChronoUnit.DAYS)));

        return new JwtServiceImpl(refreshTokenRepository, userService, jwtSettings);
    }
}
