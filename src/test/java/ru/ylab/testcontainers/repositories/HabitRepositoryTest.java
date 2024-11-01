package ru.ylab.testcontainers.repositories;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.models.Habit;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.testcontainers.config.AbstractSpringTest;

public class HabitRepositoryTest extends AbstractSpringTest {

    private HabitRepository habitRepository;

    @BeforeEach
    public void setup() {
        habitRepository = this.appContext.getBean(HabitRepository.class);
    }

    @DisplayName("Test(repository): find habit by id")
    @Test
    public void testFindById() {
        Assertions.assertTrue(habitRepository.find(-1L).isPresent());
    }

    @DisplayName("Test(repository): fail to find habit by id, habit does not exist")
    @Test
    public void testFindByIdFail() {
        Assertions.assertTrue(habitRepository.find(-10L).isEmpty());
    }

    @DisplayName("Test(repository): search habits by name")
    @Test
    public void testSearchByName() {
        HabitSearchForm form = new HabitSearchForm();
        form.setName("h1");
        List<Habit> habits = habitRepository.search(form);
        Assertions.assertEquals(1, habits.size());
        Assertions.assertTrue(habits.get(0).getName().startsWith("h1"));
    }

    @DisplayName("Test(repository): search habits by frequency")
    @Test
    public void testSearchByFrequency() {
        HabitSearchForm form = new HabitSearchForm();
        form.setFrequency(Habit.Frequency.WEEKLY);
        List<Habit> habits = habitRepository.search(form);
        Assertions.assertEquals(2, habits.size());
        Assertions.assertEquals(Habit.Frequency.WEEKLY, habits.get(0).getFrequency());
        Assertions.assertEquals(Habit.Frequency.WEEKLY, habits.get(1).getFrequency());
    }

    @DisplayName("Test(repository): get all habits for user")
    @Test
    public void testGetAllForUser() {
        List<Habit> habits = habitRepository.getAllForUser(-1L);
        Assertions.assertEquals(3, habits.size());
        Assertions.assertEquals(-1L, (long) habits.get(0).getUserId());
        Assertions.assertEquals(-1L, (long) habits.get(1).getUserId());
        Assertions.assertEquals(-1L, (long) habits.get(2).getUserId());
    }

    @DisplayName("Test(repository): search habits for user by frequency")
    @Test
    public void testSearchForUserByFrequency() {
        HabitSearchForm form = new HabitSearchForm();
        form.setFrequency(Habit.Frequency.WEEKLY);
        List<Habit> habits = habitRepository.searchForUser(-1L, form);
        Assertions.assertEquals(1, habits.size());
        Assertions.assertEquals(Habit.Frequency.WEEKLY, habits.get(0).getFrequency());
    }

    @DisplayName("Test(repository): search habits for user by name returns empty list")
    @Test
    public void testSearchForUserEmpty() {
        HabitSearchForm form = new HabitSearchForm();
        form.setName("hab");
        List<Habit> habits = habitRepository.searchForUser(-2L, form);
        Assertions.assertEquals(0, habits.size());
    }

    @DisplayName("Test(repository): save habit")
    @Test
    public void testSave() {
        Assertions.assertTrue(habitRepository.save(Habit.builder()
                                                        .name("habit10")
                                                        .description("desc10")
                                                        .frequency(Habit.Frequency.DAILY)
                                                        .userId(-1L)
                                                        .created(LocalDate.now())
                                                        .build()));
    }

    @DisplayName("Test(repository): update habit")
    @Test
    public void testUpdate() {
        Assertions.assertTrue(habitRepository.update(new Habit(-1L, "habit1",
                "desc11", Habit.Frequency.MONTHLY, -1L)));
    }

    @DisplayName("Test(repository): delete habit")
    @Test
    public void testDelete() {
        Assertions.assertTrue(habitRepository.delete(-2L, -4L));
    }

    @DisplayName("Test(repository): fail to delete habit, user not author")
    @Test
    public void testDeleteFailUserNotAuthor() {
        Assertions.assertFalse(habitRepository.delete(-1L, -4L));
    }

    @DisplayName("Test(repository): fail to delete habit, habit does not exist")
    @Test
    public void testDeleteFail() {
        Assertions.assertFalse(habitRepository.delete(-1L, -10L));
    }
}
