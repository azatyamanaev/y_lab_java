package ru.ylab.repositories;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.ylab.forms.HabitSearchForm;
import ru.ylab.models.Habit;

public class HabitRepositoryTest {

    private Storage storage;
    private HabitRepository habitRepository;

    @Before
    public void setUp() {
        storage = new Storage();
        habitRepository = new HabitRepository(storage);
        habitRepository.save(new Habit(1L, "habit1", "desc1", Habit.Frequency.DAILY, 1L));
        habitRepository.save(new Habit(2L, "habit2", "desc1", Habit.Frequency.WEEKLY, 1L));
        habitRepository.save(new Habit(3L, "hb1", "d1", Habit.Frequency.WEEKLY, 2L));
        habitRepository.save(new Habit(4L, "hb2", "d2", Habit.Frequency.MONTHLY, 2L));
    }

    @Test
    public void testGetByName() {
        Habit habit = habitRepository.getByName("habit1");
        Assert.assertEquals("habit1", habit.getName());
    }

    @Test
    public void testGetByNameFail() {
        Habit habit = habitRepository.getByName("hhh");
        Assert.assertNull(habit);
    }

    @Test
    public void testExistsByName() {
        Assert.assertTrue(habitRepository.existsByName("habit1"));
    }

    @Test
    public void testExistsByNameFail() {
        Assert.assertFalse(habitRepository.existsByName("hhh"));
    }

    @Test
    public void testGetAll() {
        Assert.assertEquals(4, habitRepository.getAll().size());
    }

    @Test
    public void testSearchByName() {
        HabitSearchForm form = new HabitSearchForm();
        form.setName("hab");
        List<Habit> habits = habitRepository.search(form);
        Assert.assertEquals(2, habits.size());
        Assert.assertTrue(habits.get(0).getName().startsWith("hab"));
        Assert.assertTrue(habits.get(1).getName().startsWith("hab"));
    }

    @Test
    public void testSearchByFrequency() {
        HabitSearchForm form = new HabitSearchForm();
        form.setFrequency(Habit.Frequency.WEEKLY.name());
        List<Habit> habits = habitRepository.search(form);
        Assert.assertEquals(2, habits.size());
        Assert.assertEquals(Habit.Frequency.WEEKLY, habits.get(0).getFrequency());
        Assert.assertEquals(Habit.Frequency.WEEKLY, habits.get(1).getFrequency());
    }

    @Test
    public void testGetAllForUser() {
        List<Habit> habits = habitRepository.getAllForUser(1L);
        Assert.assertEquals(2, habits.size());
        Assert.assertEquals(1L, (long) habits.get(0).getUserId());
        Assert.assertEquals(1L, (long) habits.get(1).getUserId());
    }

    @Test
    public void testSearchForUser() {
        HabitSearchForm form = new HabitSearchForm();
        form.setFrequency(Habit.Frequency.WEEKLY.name());
        List<Habit> habits = habitRepository.searchForUser(1L, form);
        Assert.assertEquals(1, habits.size());
        Assert.assertEquals(Habit.Frequency.WEEKLY, habits.get(0).getFrequency());
    }

    @Test
    public void testSearchForUserEmpty() {
        HabitSearchForm form = new HabitSearchForm();
        form.setName("hab");
        List<Habit> habits = habitRepository.searchForUser(3L, form);
        Assert.assertEquals(0, habits.size());
    }

    @Test
    public void testSave() {
        habitRepository.save(new Habit(10L, "habit10", "desc10", Habit.Frequency.DAILY, 3L));
        Habit habit = storage.getHabits().get(10L);
        Assert.assertEquals("habit10", habit.getName());

    }

    @Test
    public void testUpdate() {
        habitRepository.update(new Habit(1L, "habit11", "desc11", Habit.Frequency.MONTHLY, 1L));
        Habit habit = storage.getHabits().get(1L);
        Assert.assertEquals("habit11", habit.getName());
        Assert.assertEquals("desc11", habit.getDescription());
        Assert.assertEquals(Habit.Frequency.MONTHLY, habit.getFrequency());
    }

    @Test
    public void testDelete() {
        Assert.assertTrue(habitRepository.delete(1L,
                new Habit(1L, "habit1", "desc1", Habit.Frequency.DAILY, 1L)));
    }


    @Test
    public void testDeleteFailUserNotAuthor() {
        Assert.assertFalse(habitRepository.delete(5L,
                new Habit(1L, "habit1", "desc1", Habit.Frequency.DAILY, 1L)));
    }

    @Test
    public void testDeleteFail() {
        Assert.assertFalse(habitRepository.delete(1L,
                new Habit(10L, "habit1", "desc1", Habit.Frequency.DAILY, 1L)));
    }
}
