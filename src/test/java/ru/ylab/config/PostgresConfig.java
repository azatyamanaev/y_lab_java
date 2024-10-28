package ru.ylab.config;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.utils.constants.AppConstants;

public abstract class PostgresConfig {

    private static PostgreSQLContainer<?> postgres;
    protected static CPDataSource dataSource;
    private static AppContext appContext;

    @BeforeAll
    public static void beforeAll() throws SQLException, FileNotFoundException {
        postgres = TestConfigurer.createPostgresContainer(Network.SHARED);
        postgres.start();

        appContext = AppContext.createContext(AppConstants.TEST_PROFILE);
        appContext.getServicesConfig().getLiquibaseService().migrate();
        dataSource = appContext.getDataSourceConfig().getDataSource();
        TestConfigurer.insertTestData(dataSource);
    }

    @AfterAll
    public static void afterAll() throws SQLException, FileNotFoundException {
        TestConfigurer.deleteTestData(dataSource);
        dataSource = null;
        appContext.getDataSourceConfig().getConnectionPool().shutdown();
        postgres.stop();
    }
}
