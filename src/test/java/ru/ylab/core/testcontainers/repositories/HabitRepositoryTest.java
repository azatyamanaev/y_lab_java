package ru.ylab.core.testcontainers.repositories;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ylab.core.dto.in.HabitSearchForm;
import ru.ylab.core.models.Habit;
import ru.ylab.core.repositories.HabitRepository;
import ru.ylab.core.testcontainers.config.AbstractSpringTest;

import static org.assertj.core.api.Assertions.assertThat;

public class HabitRepositoryTest extends AbstractSpringTest {

    @Autowired
    private HabitRepository habitRepository;

    @DisplayName("Test(repository): find habit by id")
    @Test
    public void testFindById() {
        assertThat(habitRepository.find(-1L)).isPresent();
    }

    @DisplayName("Test(repository): fail to find habit by id, habit does not exist")
    @Test
    public void testFindByIdFail() {
        assertThat(habitRepository.find(-10L)).isNotPresent();
    }

    @DisplayName("Test(repository): search habits by name")
    @Test
    public void testSearchByName() {
        HabitSearchForm form = new HabitSearchForm();
        form.setName("h1");
        List<Habit> habits = habitRepository.search(form);
        assertThat(habits).size().isEqualTo(1);
        assertThat(habits.get(0).getName()).startsWith("h1");
    }

    @DisplayName("Test(repository): search habits by frequency")
    @Test
    public void testSearchByFrequency() {
        HabitSearchForm form = new HabitSearchForm();
        form.setFrequency(Habit.Frequency.WEEKLY);
        List<Habit> habits = habitRepository.search(form);
        assertThat(habits).size().isEqualTo(2);
        assertThat(habits.get(0).getFrequency()).isEqualTo(Habit.Frequency.WEEKLY);
        assertThat(habits.get(1).getFrequency()).isEqualTo(Habit.Frequency.WEEKLY);
    }

    @DisplayName("Test(repository): get all habits for user")
    @Test
    public void testGetAllForUser() {
        List<Habit> habits = habitRepository.getAllForUser(-1L);
        assertThat(habits).size().isEqualTo(3);
        assertThat((long) habits.get(0).getUserId()).isEqualTo(-1L);
        assertThat((long) habits.get(1).getUserId()).isEqualTo(-1L);
        assertThat((long) habits.get(2).getUserId()).isEqualTo(-1L);
    }

    @DisplayName("Test(repository): search habits for user by frequency")
    @Test
    public void testSearchForUserByFrequency() {
        HabitSearchForm form = new HabitSearchForm();
        form.setFrequency(Habit.Frequency.WEEKLY);
        List<Habit> habits = habitRepository.searchForUser(-1L, form);
        assertThat(habits).size().isEqualTo(1);
        assertThat(habits.get(0).getFrequency()).isEqualTo(Habit.Frequency.WEEKLY);
    }

    @DisplayName("Test(repository): search habits for user by name returns empty list")
    @Test
    public void testSearchForUserEmpty() {
        HabitSearchForm form = new HabitSearchForm();
        form.setName("hab");
        List<Habit> habits = habitRepository.searchForUser(-2L, form);
        assertThat(habits).size().isEqualTo(0);
    }

    @DisplayName("Test(repository): save habit")
    @Test
    public void testSave() {
        assertThat(habitRepository.save(Habit.builder()
                                             .name("habit10")
                                             .description("desc10")
                                             .frequency(Habit.Frequency.DAILY)
                                             .userId(-1L)
                                             .created(LocalDate.now())
                                             .build())).isTrue();
    }

    @DisplayName("Test(repository): update habit")
    @Test
    public void testUpdate() {
        assertThat(habitRepository.update(new Habit(-1L, "habit1",
                "desc11", Habit.Frequency.MONTHLY, -1L))).isTrue();
    }

    @DisplayName("Test(repository): delete habit")
    @Test
    public void testDelete() {
        assertThat(habitRepository.delete(-2L, -4L)).isTrue();
    }

    @DisplayName("Test(repository): fail to delete habit, user not author")
    @Test
    public void testDeleteFailUserNotAuthor() {
        assertThat(habitRepository.delete(-1L, -4L)).isFalse();
    }

    @DisplayName("Test(repository): fail to delete habit, habit does not exist")
    @Test
    public void testDeleteFail() {
        assertThat(habitRepository.delete(-1L, -10L)).isFalse();
    }
}
