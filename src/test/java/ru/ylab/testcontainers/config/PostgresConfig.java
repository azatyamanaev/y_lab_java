package ru.ylab.testcontainers.config;

public abstract class PostgresConfig {

//    private static PostgreSQLContainer<?> postgres;
//    protected static CPDataSource dataSource;
//    protected static AppContext appContext;
//
//    @BeforeAll
//    public static void beforeAll() throws SQLException, FileNotFoundException {
//        postgres = TestConfigurer.createPostgresContainer(Network.SHARED);
//        postgres.start();
//
//        appContext = AppContext.createContext(AppConstants.TEST_PROFILE);
//        appContext.getServicesConfig().getLiquibaseService().migrate();
//        dataSource = appContext.getDataSourceConfig().getDataSource();
//        TestConfigurer.insertTestData(dataSource);
//    }
//
//    @AfterAll
//    public static void afterAll() throws SQLException, FileNotFoundException {
//        TestConfigurer.deleteTestData(dataSource);
//        dataSource = null;
//        appContext.getDataSourceConfig().getConnectionPool().shutdown();
//        postgres.stop();
//    }
}
