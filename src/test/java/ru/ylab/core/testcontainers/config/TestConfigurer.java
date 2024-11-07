package ru.ylab.core.testcontainers.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.ylab.core.models.User;
import ru.ylab.core.services.datasource.CPDataSource;

public class TestConfigurer {

    private static final String POSTGRES = "postgres:17-alpine";
    private static final String DATABASE = "habits_app_db";
    private static final String USERNAME = "liquibase";
    private static final String PASSWORD = "liquibase";
    private static final int DB_CONTAINER_PORT = 5432;
    private static final int DB_LOCAL_PORT = 54322;

    public static PostgreSQLContainer<?> createPostgresContainer() {
        return new PostgreSQLContainer<>(POSTGRES)
                .withDatabaseName(DATABASE)
                .withUsername(USERNAME)
                .withPassword(PASSWORD)
                .withCreateContainerCmdModifier(
                        cmd -> cmd.withHostConfig(new HostConfig().withPortBindings(
                                new PortBinding(Ports.Binding.bindPort(DB_LOCAL_PORT), new ExposedPort(DB_CONTAINER_PORT)))))
                .withInitScript("db/init.sql");
    }

    public static void executeScript(CPDataSource dataSource, String path) throws SQLException, FileNotFoundException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setSendFullScript(false);
            runner.setStopOnError(true);
            runner.runScript(new FileReader(path));
            conn.commit();
        }
    }

    public static User getTestAdmin() {
        return User.builder()
                   .id(0L)
                   .name("admin_test")
                   .email("admin_test@mail.ru")
                   .password("admin")
                   .role(User.Role.ADMIN)
                   .build();
    }

    public static User getTestUser() {
        return User.builder()
                   .id(-1L)
                   .name("user1")
                   .email("a_test@mail.ru")
                   .password("123")
                   .role(User.Role.USER)
                   .build();
    }
}
