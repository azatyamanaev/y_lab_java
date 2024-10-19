package ru.ylab.repositories;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ylab.forms.HabitSearchForm;
import ru.ylab.models.Habit;
import ru.ylab.repositories.impl.HabitRepositoryImpl;

public class HabitRepositoryTest extends AbstractRepositoryTest {

    private HabitRepository habitRepository;

    @BeforeEach
    public void setUp(){
        habitRepository = new HabitRepositoryImpl(connectionPool);
    }

    @Test
    public void testGetByName() {
        Habit habit = habitRepository.getByName("habit1");
        Assertions.assertEquals("habit1", habit.getName());
    }

    @Test
    public void testGetByNameFail() {
        Habit habit = habitRepository.getByName("hhh");
        Assertions.assertNull(habit);
    }

    @Test
    public void testExistsByName() {
        Assertions.assertTrue(habitRepository.existsByName("habit1"));
    }

    @Test
    public void testExistsByNameFail() {
        Assertions.assertFalse(habitRepository.existsByName("hhh"));
    }

    @Test
    public void testGetAll() {
        Assertions.assertEquals(4, habitRepository.getAll().size());
    }

    @Test
    public void testSearchByName() {
        HabitSearchForm form = new HabitSearchForm();
        form.setName("hab");
        List<Habit> habits = habitRepository.search(form);
        Assertions.assertEquals(2, habits.size());
        Assertions.assertTrue(habits.get(0).getName().startsWith("hab"));
        Assertions.assertTrue(habits.get(1).getName().startsWith("hab"));
    }

    @Test
    public void testSearchByFrequency() {
        HabitSearchForm form = new HabitSearchForm();
        form.setFrequency(Habit.Frequency.WEEKLY.name());
        List<Habit> habits = habitRepository.search(form);
        Assertions.assertEquals(2, habits.size());
        Assertions.assertEquals(Habit.Frequency.WEEKLY, habits.get(0).getFrequency());
        Assertions.assertEquals(Habit.Frequency.WEEKLY, habits.get(1).getFrequency());
    }

    @Test
    public void testGetAllForUser() {
        List<Habit> habits = habitRepository.getAllForUser(-2L);
        Assertions.assertEquals(2, habits.size());
        Assertions.assertEquals(-2L, (long) habits.get(0).getUserId());
        Assertions.assertEquals(-2L, (long) habits.get(1).getUserId());
    }

    @Test
    public void testSearchForUser() {
        HabitSearchForm form = new HabitSearchForm();
        form.setFrequency(Habit.Frequency.DAILY.name());
        List<Habit> habits = habitRepository.searchForUser(-1L, form);
        Assertions.assertEquals(1, habits.size());
        Assertions.assertEquals(Habit.Frequency.WEEKLY, habits.get(0).getFrequency());
    }

    @Test
    public void testSearchForUserEmpty() {
        HabitSearchForm form = new HabitSearchForm();
        form.setName("hab");
        List<Habit> habits = habitRepository.searchForUser(3L, form);
        Assertions.assertEquals(0, habits.size());
    }

    @Test
    public void testSave() {
        habitRepository.save(new Habit(10L, "habit10", "desc10", Habit.Frequency.DAILY, -2L));
        Habit habit = habitRepository.getByName("habit10");
        Assertions.assertEquals("habit10", habit.getName());
    }

    @Test
    public void testUpdate() {
        habitRepository.update(new Habit(-1L, "habit1", "desc11", Habit.Frequency.MONTHLY, -1L));
        Habit habit = habitRepository.getByName("habit1");
        Assertions.assertEquals("habit1", habit.getName());
        Assertions.assertEquals("desc11", habit.getDescription());
        Assertions.assertEquals(Habit.Frequency.MONTHLY, habit.getFrequency());
    }

    @Test
    public void testDelete() {
        Assertions.assertTrue(habitRepository.delete(-1L,
                new Habit(-1L, "habit1", "desc1", Habit.Frequency.DAILY, -1L)));
    }

    @Test
    public void testDeleteFailUserNotAuthor() {
        Assertions.assertFalse(habitRepository.delete(5L,
                new Habit(1L, "habit1", "desc1", Habit.Frequency.DAILY, 1L)));
    }

    @Test
    public void testDeleteFail() {
        Assertions.assertFalse(habitRepository.delete(1L,
                new Habit(10L, "habit1", "desc1", Habit.Frequency.DAILY, 1L)));
    }
}
