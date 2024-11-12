package ru.ylab.mockito.config;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.ylab.repositories.RefreshTokenRepository;
import ru.ylab.repositories.UserRepository;
import ru.ylab.services.auth.PasswordService;
import ru.ylab.services.entities.HabitHistoryService;
import ru.ylab.services.entities.HabitService;
import ru.ylab.services.entities.UserService;
import ru.ylab.utils.constants.AppConstants;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;

@ActiveProfiles(AppConstants.TEST_PROFILE)
@WebMvcTest
@ContextConfiguration(classes = {TestWebConfig.class})
public abstract class AbstractWebTest {

    protected MockMvc mockMvc;

    @MockBean
    protected PasswordService passwordService;

    @MockBean
    protected UserService userService;

    @MockBean
    protected HabitService habitService;

    @MockBean
    protected HabitHistoryService habitHistoryService;

    @MockBean
    protected RefreshTokenRepository refreshTokenRepository;

    @MockBean
    protected UserRepository userRepository;

    @BeforeEach
    public void setUp(WebApplicationContext applicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();

        doAnswer(invocation -> invocation.<String>getArgument(0))
                .when(passwordService).hashPassword(anyString());
        doAnswer(invocation -> invocation.<String>getArgument(0).equals(invocation.<String>getArgument(1)))
                .when(passwordService).verifyPassword(anyString(), anyString());
    }
}
