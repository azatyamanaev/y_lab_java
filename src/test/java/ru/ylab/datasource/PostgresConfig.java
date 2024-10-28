package ru.ylab.datasource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.ylab.config.AppContext;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.utils.constants.AppConstants;

public abstract class PostgresConfig {

    private static final String IMAGE = "postgres:17-alpine";
    private static final String DATABASE = "habits_app_db";
    private static final String USERNAME = "liquibase";
    private static final String PASSWORD = "liquibase";
    private static final int CONTAINER_PORT = 5432;
    private static final int LOCAL_PORT = 54322;
    private static PostgreSQLContainer<?> postgres;
    protected static CPDataSource dataSource;
    protected static AppContext appContext;

    @BeforeAll
    public static void beforeAll() throws SQLException, FileNotFoundException {
        postgres = new PostgreSQLContainer<>(IMAGE)
                .withDatabaseName(DATABASE)
                .withUsername(USERNAME)
                .withPassword(PASSWORD)
                .withCreateContainerCmdModifier(
                        cmd -> cmd.withHostConfig(new HostConfig().withPortBindings(
                                new PortBinding(Ports.Binding.bindPort(LOCAL_PORT), new ExposedPort(CONTAINER_PORT)))))
                .withInitScript("db/init.sql");
        postgres.start();

        appContext = AppContext.createContext(AppConstants.TEST_PROFILE);
        appContext.getServicesConfig().getLiquibaseService().migrate();
        dataSource = appContext.getDataSourceConfig().getDataSource();
        insertTestData();
    }

    public static void insertTestData() throws SQLException, FileNotFoundException {
        Connection conn = dataSource.getConnection();
        ScriptRunner runner = new ScriptRunner(conn);
        runner.setSendFullScript(false);
        runner.setStopOnError(true);
        runner.runScript(new FileReader("./src/test/resources/db/insert_test_data.sql"));
    }

    public static void deleteTestData() throws SQLException, FileNotFoundException {
        Connection conn = dataSource.getConnection();
        ScriptRunner runner = new ScriptRunner(conn);
        runner.setSendFullScript(false);
        runner.setStopOnError(true);
        runner.runScript(new FileReader("./src/test/resources/db/delete_test_data.sql"));
    }

    @AfterAll
    public static void afterAll() throws SQLException, FileNotFoundException {
        deleteTestData();
        dataSource = null;
        appContext.getDataSourceConfig().getConnectionPool().shutdown();
        postgres.stop();
    }
}
