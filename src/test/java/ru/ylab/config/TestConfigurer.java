package ru.ylab.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.ylab.services.datasource.CPDataSource;

public class TestConfigurer {

    private static final String POSTGRES = "postgres:17-alpine";
    private static final String DATABASE = "habits_app_db";
    private static final String USERNAME = "liquibase";
    private static final String PASSWORD = "liquibase";
    private static final int DB_CONTAINER_PORT = 5432;
    private static final int DB_LOCAL_PORT = 54322;

    public static PostgreSQLContainer<?> createPostgresContainer(Network network) {
        return new PostgreSQLContainer<>(POSTGRES)
                .withDatabaseName(DATABASE)
                .withUsername(USERNAME)
                .withPassword(PASSWORD)
                .withCreateContainerCmdModifier(
                        cmd -> cmd.withHostConfig(new HostConfig().withPortBindings(
                                new PortBinding(Ports.Binding.bindPort(DB_LOCAL_PORT), new ExposedPort(DB_CONTAINER_PORT)))))
                .withNetwork(network)
                .withNetworkAliases("ylab")
                .withInitScript("db/init.sql");
    }

    public static void insertTestData(CPDataSource dataSource) throws SQLException, FileNotFoundException {
        Connection conn = dataSource.getConnection();
        ScriptRunner runner = new ScriptRunner(conn);
        runner.setSendFullScript(false);
        runner.setStopOnError(true);
        runner.runScript(new FileReader("./src/test/resources/db/insert_test_data.sql"));
    }

    public static void deleteTestData(CPDataSource dataSource) throws SQLException, FileNotFoundException {
        Connection conn = dataSource.getConnection();
        ScriptRunner runner = new ScriptRunner(conn);
        runner.setSendFullScript(false);
        runner.setStopOnError(true);
        runner.runScript(new FileReader("./src/test/resources/db/delete_test_data.sql"));
    }
}
