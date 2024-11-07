package ru.ylab.core.testcontainers.config;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.ylab.core.WebApplication;
import ru.ylab.core.services.datasource.CPDataSource;
import ru.ylab.core.utils.constants.AppConstants;

@ActiveProfiles(AppConstants.TEST_PROFILE)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {TestAppConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public abstract class AbstractSpringTest {

    private static PostgreSQLContainer<?> postgres;

    @BeforeAll
    public static void initDb() {
        postgres = TestConfigurer.createPostgresContainer();
        postgres.start();
    }

    @AfterAll
    public static void closeDb() {
        postgres.stop();
    }

    @Autowired
    private WebApplicationContext appContext;
    protected MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws SQLException, FileNotFoundException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(appContext).build();

        TestConfigurer.executeScript(this.appContext.getBean(CPDataSource.class),
                "./src/test/resources/db/delete_test_data.sql");
        TestConfigurer.executeScript(this.appContext.getBean(CPDataSource.class),
                "./src/test/resources/db/insert_test_data.sql");
    }
}
