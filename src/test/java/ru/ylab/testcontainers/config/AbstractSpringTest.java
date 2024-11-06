package ru.ylab.testcontainers.config;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.utils.constants.AppConstants;

@ActiveProfiles(AppConstants.TEST_PROFILE)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppConfig.class})
@WebAppConfiguration
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
    protected WebApplicationContext appContext;
    protected MockMvc mockMvc;

    @BeforeEach
    public void insertData() throws SQLException, FileNotFoundException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.appContext).build();
        TestConfigurer.executeScript(this.appContext.getBean(CPDataSource.class),
                "./src/test/resources/db/delete_test_data.sql");
        TestConfigurer.executeScript(this.appContext.getBean(CPDataSource.class),
                "./src/test/resources/db/insert_test_data.sql");
    }
}
