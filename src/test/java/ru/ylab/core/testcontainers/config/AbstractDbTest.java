package ru.ylab.core.testcontainers.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.ylab.core.utils.constants.AppConstants;

@ActiveProfiles(AppConstants.TEST_PROFILE)
@SpringBootTest(classes = TestDbConfig.class)
@SqlConfig(dataSource = "customDataSource")
@Sql(scripts = {"classpath:db/delete_test_data.sql", "classpath:db/insert_test_data.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public abstract class AbstractDbTest {
}
