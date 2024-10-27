package ru.ylab.repositories;

public class HabitHistoryRepositoryTest extends AbstractRepositoryTest {

//    private HabitHistoryRepository historyRepository;
//
//    @BeforeEach
//    public void setUp() {
//        historyRepository = new HabitHistoryRepositoryImpl(dataSource);
//    }
//
//    @DisplayName("Test: get habit history by habit id")
//    @Test
//    public void testGetByHabitId() {
//        HabitHistory history = historyRepository.getByHabitId(-3L);
//        Assertions.assertEquals(2, history.getDays().size());
//    }
//
//    @DisplayName("Test: fail to get empty habit history by habit id")
//    @Test
//    public void testGetByHabitIdEmpty() {
//        Assertions.assertEquals(0, historyRepository.getByHabitId(-5L).getDays().size());
//    }
//
//    @DisplayName("Test: save habit history")
//    @Test
//    public void testSave() {
//        HabitHistory history = historyRepository.save(new HabitHistory(-1L, -2L, LocalDate.parse("2024-10-15")));
//        HabitHistory saved = historyRepository.getByHabitId(-2L);
//        Assertions.assertEquals(history.getUserId(), saved.getUserId());
//    }
//
//    @DisplayName("Test: delete habit history by habit id")
//    @Test
//    public void testDeleteByHabitId() {
//        Assertions.assertTrue(historyRepository.deleteByHabitId(-1L));
//    }
//
//    @DisplayName("Test: fail to delete empty habit history by habit id")
//    @Test
//    public void testDeleteByHabitIdFail() {
//        Assertions.assertFalse(historyRepository.deleteByHabitId(0L));
//    }
}
