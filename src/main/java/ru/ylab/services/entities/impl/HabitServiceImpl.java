package ru.ylab.services.entities.impl;

import java.time.LocalDate;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import ru.ylab.dto.in.HabitForm;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.models.Habit;
import ru.ylab.repositories.HabitHistoryRepository;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.services.entities.HabitService;

/**
 * Service implementing {@link HabitService}.
 *
 * @author azatyamanaev
 */
@Slf4j
public class HabitServiceImpl implements HabitService {

    /**
     * Instance of a {@link HabitRepository}
     */
    private final HabitRepository habitRepository;

    /**
     * Instance of a {@link HabitHistoryRepository}
     */
    private final HabitHistoryRepository habitHistoryRepository;

    /**
     * Creates new HabitServiceImpl.
     *
     * @param habitRepository        HabitRepository instance
     * @param habitHistoryRepository HabitHistoryRepository instance
     */
    public HabitServiceImpl(HabitRepository habitRepository, HabitHistoryRepository habitHistoryRepository) {
        this.habitRepository = habitRepository;
        this.habitHistoryRepository = habitHistoryRepository;
    }

    @Override
    public List<Habit> getHabitsForUser(Long userId) {
        return habitRepository.getAllForUser(userId);
    }

    @Override
    public List<Habit> searchHabitsForUser(Long userId, HabitSearchForm form) {
        return habitRepository.searchForUser(userId, form);
    }

    @Override
    public Habit create(Long userId, HabitForm form) {
        return habitRepository.save(Habit.builder()
                                         .name(form.getName())
                                         .description(form.getDescription())
                                         .frequency(Habit.Frequency.valueOf(form.getFrequency()))
                                         .created(LocalDate.now())
                                         .userId(userId)
                                         .build());
    }

    @Override
    public Habit update(String name, HabitForm form) {
        Habit habit = habitRepository.getByName(name);
        habit.setName(form.getName());
        habit.setDescription(form.getDescription());
        habit.setFrequency(Habit.Frequency.valueOf(form.getFrequency()));
        return habitRepository.update(habit);
    }

    @Override
    public boolean deleteByName(Long userId, String name) {
        Habit habit = habitRepository.getByName(name);
        return habitRepository.delete(userId, habit);
    }
}
