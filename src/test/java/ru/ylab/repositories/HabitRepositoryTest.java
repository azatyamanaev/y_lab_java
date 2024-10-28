package ru.ylab.repositories;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.models.Habit;
import ru.ylab.repositories.impl.HabitRepositoryImpl;

public class HabitRepositoryTest extends PostgresConfig {

    private HabitRepository habitRepository;

    @BeforeEach
    public void setUp() {
        habitRepository = new HabitRepositoryImpl(dataSource);
    }

    @DisplayName("Test: find habit by id")
    @Test
    public void testFindById() {
        Assertions.assertTrue(habitRepository.find(-10L).isPresent());
    }

    @DisplayName("Test: fail to find habit by id, habit does not exist")
    @Test
    public void testFindByIdFail() {
        Assertions.assertTrue(habitRepository.find(-100L).isEmpty());
    }

    @DisplayName("Test: search habits by name")
    @Test
    public void testSearchByName() {
        HabitSearchForm form = new HabitSearchForm();
        form.setName("hb");
        List<Habit> habits = habitRepository.search(form);
        Assertions.assertEquals(2, habits.size());
        Assertions.assertTrue(habits.get(0).getName().startsWith("hb"));
        Assertions.assertTrue(habits.get(1).getName().startsWith("hb"));
    }

    @DisplayName("Test: search habits by frequency")
    @Test
    public void testSearchByFrequency() {
        HabitSearchForm form = new HabitSearchForm();
        form.setFrequency(Habit.Frequency.WEEKLY.name());
        form.setName("test");
        List<Habit> habits = habitRepository.search(form);
        Assertions.assertEquals(2, habits.size());
        Assertions.assertEquals(Habit.Frequency.WEEKLY, habits.get(0).getFrequency());
        Assertions.assertEquals(Habit.Frequency.WEEKLY, habits.get(1).getFrequency());
    }

    @DisplayName("Test: get all habits for user")
    @Test
    public void testGetAllForUser() {
        List<Habit> habits = habitRepository.getAllForUser(-20L);
        Assertions.assertEquals(3, habits.size());
        Assertions.assertEquals(-20L, (long) habits.get(0).getUserId());
        Assertions.assertEquals(-20L, (long) habits.get(1).getUserId());
        Assertions.assertEquals(-20L, (long) habits.get(2).getUserId());
    }

    @DisplayName("Test: search habits for user by frequency")
    @Test
    public void testSearchForUserByFrequency() {
        HabitSearchForm form = new HabitSearchForm();
        form.setFrequency(Habit.Frequency.WEEKLY.name());
        List<Habit> habits = habitRepository.searchForUser(-20L, form);
        Assertions.assertEquals(1, habits.size());
        Assertions.assertEquals(Habit.Frequency.WEEKLY, habits.get(0).getFrequency());
    }

    @DisplayName("Test: search habits for user by name returns empty list")
    @Test
    public void testSearchForUserEmpty() {
        HabitSearchForm form = new HabitSearchForm();
        form.setName("hab");
        List<Habit> habits = habitRepository.searchForUser(-30L, form);
        Assertions.assertEquals(0, habits.size());
    }

    @DisplayName("Test: save habit")
    @Test
    public void testSave() {
        Assertions.assertTrue(habitRepository.save(Habit.builder()
                                                        .name("habit10")
                                                        .description("desc10")
                                                        .frequency(Habit.Frequency.DAILY)
                                                        .userId(-20L)
                                                        .created(LocalDate.now())
                                                        .build()));
    }

    @DisplayName("Test: update habit")
    @Test
    public void testUpdate() {
        Assertions.assertTrue(habitRepository.update(new Habit(-10L, "habit1",
                "desc11", Habit.Frequency.MONTHLY, -20L)));
    }

    @DisplayName("Test: delete habit")
    @Test
    public void testDelete() {
        Assertions.assertTrue(habitRepository.delete(-30L, -40L));
    }

    @DisplayName("Test: fail to delete habit, user not author")
    @Test
    public void testDeleteFailUserNotAuthor() {
        Assertions.assertFalse(habitRepository.delete(-20L, -50L));
    }

    @DisplayName("Test: fail to delete habit, habit does not exist")
    @Test
    public void testDeleteFail() {
        Assertions.assertFalse(habitRepository.delete(-20L, -100L));
    }
}
