package ru.ylab.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.ylab.services.datasource.impl.BasicCPDataSource;
import ru.ylab.services.datasource.CPDataSource;
import ru.ylab.config.datasource.LiquibaseConfig;
import ru.ylab.services.datasource.ConnectionPool;
import ru.ylab.services.datasource.impl.BasicConnectionPool;
import ru.ylab.settings.DbSettings;
import ru.ylab.utils.ConfigParser;

public abstract class AbstractRepositoryTest {

    protected static final String IMAGE = "postgres:17-alpine";
    protected static final String DATABASE = "habits_app_db";
    protected static final String USERNAME = "liquibase";
    protected static final String PASSWORD = "password";
    protected static PostgreSQLContainer<?> postgres;
    private static ConnectionPool connectionPool;
    protected static CPDataSource dataSource;
    protected static Liquibase liquibase;

    @BeforeAll
    public static void beforeAll() throws LiquibaseException, SQLException {
        postgres = new PostgreSQLContainer<>(IMAGE)
                .withDatabaseName(DATABASE)
                .withUsername(USERNAME)
                .withPassword(PASSWORD)
                .withInitScript("db/init.sql");
        postgres.start();

        connectionPool = new BasicConnectionPool(new DbSettings(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword()));
        LiquibaseConfig config = new LiquibaseConfig(ConfigParser.parseLiquibaseSettings());
        liquibase = config.liquibase(connectionPool);
        liquibase.update();
        dataSource = new BasicCPDataSource(connectionPool);
        dataSource.setAutoCommit(false);
        insertTestData();
    }

    @AfterAll
    public static void afterAll() throws SQLException {
        dataSource = null;
        connectionPool.shutdown();
        postgres.stop();
    }


    private static void insertTestData() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("delete from entity.habit_history;");
        statement.executeUpdate("delete from entity.habits;");
        statement.executeUpdate("delete from entity.users;");
        statement.executeUpdate("insert into entity.users(id, name, email, password, role) values " +
                "(0, 'admin', 'admin@mail.ru', 'admin', 'ADMIN')," +
                "(-1, 'user1', 'user1@mail.ru', 'pass1', 'USER')," +
                "(-2, 'user2', 'user2@mail.ru', 'pass2', 'USER');");

        statement.executeUpdate("insert into entity.habits(id, name, description, frequency, created, user_id) values " +
                "(-1, 'habit1', 'desc1', 'DAILY', now(), -1)," +
                "(-2, 'habit2', 'desc2', 'DAILY', now(), -1)," +
                "(-3, 'habit3', 'desc3', 'WEEKLY', now(), -1)," +
                "(-4, 'hb1', 'd1', 'WEEKLY', '2024-09-15', -2)," +
                "(-5, 'hb2', 'd2', 'MONTHLY', '2024-09-01', -2);");

        statement.executeUpdate("insert into entity.habit_history(user_id, habit_id, completed_on) values " +
                "(-1, -1, '2024-10-01')," +
                "(-1, -3, '2024-10-05')," +
                "(-1, -3, '2024-10-15')," +
                "(-2, -4, '2024-09-10')," +
                "(-2, -4, '2024-09-17')");
        connection.commit();

        statement.close();
        connectionPool.releaseConnection(connection);
    }
}
