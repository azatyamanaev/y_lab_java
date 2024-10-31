package ru.ylab.testcontainers.repositories;

import ru.ylab.testcontainers.config.PostgresConfig;

public class HabitHistoryRepositoryTest extends PostgresConfig {

//    private HabitHistoryRepository historyRepository;
//
//    @BeforeEach
//    public void setUp() {
//        historyRepository = new HabitHistoryRepositoryImpl(dataSource);
//    }
//
//    @DisplayName("Test(repository): get habit history by habit id")
//    @Test
//    public void testGetByHabitId() {
//        HabitHistoryProjection history = historyRepository.getByHabitId(-10L);
//        Assertions.assertEquals(3, history.getDays().size());
//        Assertions.assertTrue(history.getDays().contains(LocalDate.parse("2024-10-01")));
//    }
//
//    @DisplayName("Test(repository): fail to get empty habit history by habit id")
//    @Test
//    public void testGetByHabitIdEmpty() {
//        Assertions.assertEquals(0, historyRepository.getByHabitId(-60L).getDays().size());
//    }
//
//    @DisplayName("Test(repository): save habit history")
//    @Test
//    public void testSave() {
//        historyRepository.save(new HabitHistory(-10L, -20L, LocalDate.parse("2024-10-15")));
//        HabitHistoryProjection saved = historyRepository.getByHabitId(-20L);
//        Assertions.assertTrue(saved.getDays().contains(LocalDate.parse("2024-10-15")));
//    }
//
//    @DisplayName("Test(repository): delete habit history by habit id")
//    @Test
//    public void testDeleteByHabitId() {
//        Assertions.assertTrue(historyRepository.deleteByHabitId(-40L));
//    }
//
//    @DisplayName("Test(repository): fail to delete empty habit history by habit id")
//    @Test
//    public void testDeleteByHabitIdFail() {
//        Assertions.assertFalse(historyRepository.deleteByHabitId(-60L));
//    }
}
