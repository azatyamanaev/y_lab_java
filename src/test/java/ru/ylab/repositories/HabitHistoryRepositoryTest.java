package ru.ylab.repositories;

public class HabitHistoryRepositoryTest {

//    private Storage storage;
//    private HabitHistoryRepository historyRepository;
//
//    @BeforeEach
//    public void setUp() {
//        storage = new Storage();
//        historyRepository = new HabitHistoryRepositoryImpl(storage);
//        historyRepository.save(new HabitHistory(1L, 1L, Set.of()));
//        historyRepository.save(new HabitHistory(1L, 2L, Set.of(LocalDate.parse("2024-10-12"))));
//        historyRepository.save(new HabitHistory(2L, 3L, Set.of(LocalDate.parse("2024-10-12"), LocalDate.parse("2024-10-05"))));
//    }
//
//    @Test
//    public void testGetByHabitId() {
//        HabitHistory history = historyRepository.getByHabitId(2L);
//        Assertions.assertEquals(1, history.getDays().size());
//    }
//
//    @Test
//    public void testGetByHabitIdFail() {
//        Assertions.assertNull(historyRepository.getByHabitId(0L));
//    }
//
//    @Test
//    public void testSave() {
//        HabitHistory history = historyRepository.save(new HabitHistory(2L, 4L, Set.of()));
//        HabitHistory saved = storage.getHabitHistory().get(4L);
//        Assertions.assertEquals(history.getUserId(), saved.getUserId());
//    }
//
//    @Test
//    public void testDeleteByHabitId() {
//        Assertions.assertTrue(historyRepository.deleteByHabitId(1L));
//    }
//
//    @Test
//    public void testDeleteByHabitIdFail() {
//        Assertions.assertFalse(historyRepository.deleteByHabitId(0L));
//    }
}
