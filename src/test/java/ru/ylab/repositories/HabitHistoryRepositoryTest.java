package ru.ylab.repositories;

import java.time.LocalDate;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.ylab.models.HabitHistory;
import ru.ylab.repositories.impl.HabitHistoryRepositoryImpl;

public class HabitHistoryRepositoryTest {

    private Storage storage;
    private HabitHistoryRepository historyRepository;

    @Before
    public void setUp() {
        storage = new Storage();
        historyRepository = new HabitHistoryRepositoryImpl(storage);
        historyRepository.save(new HabitHistory(1L, 1L, Set.of()));
        historyRepository.save(new HabitHistory(1L, 2L, Set.of(LocalDate.parse("2024-10-12"))));
        historyRepository.save(new HabitHistory(2L, 3L, Set.of(LocalDate.parse("2024-10-12"), LocalDate.parse("2024-10-05"))));
    }

    @Test
    public void testGetByHabitId() {
        HabitHistory history = historyRepository.getByHabitId(2L);
        Assert.assertEquals(1, history.getDays().size());
    }

    @Test
    public void testGetByHabitIdFail() {
        Assert.assertNull(historyRepository.getByHabitId(0L));
    }

    @Test
    public void testSave() {
        HabitHistory history = historyRepository.save(new HabitHistory(2L, 4L, Set.of()));
        HabitHistory saved = storage.getHabitHistory().get(4L);
        Assert.assertEquals(history.getUserId(), saved.getUserId());
    }

    @Test
    public void testDeleteByHabitId() {
        Assert.assertTrue(historyRepository.deleteByHabitId(1L));
    }

    @Test
    public void testDeleteByHabitIdFail() {
        Assert.assertFalse(historyRepository.deleteByHabitId(0L));
    }
}
