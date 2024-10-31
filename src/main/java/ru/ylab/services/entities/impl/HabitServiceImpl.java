package ru.ylab.services.entities.impl;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ylab.dto.in.HabitForm;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.exception.HttpException;
import ru.ylab.models.Habit;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.services.entities.HabitService;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Service implementing {@link HabitService}.
 *
 * @author azatyamanaev
 */
@RequiredArgsConstructor
@Service
public class HabitServiceImpl implements HabitService {

    /**
     * Instance of a {@link HabitRepository}
     */
    private final HabitRepository habitRepository;

    @Override
    public Habit get(Long id) {
        return habitRepository.find(id)
                .orElseThrow(() -> HttpException.badRequest().addDetail(ErrorConstants.NOT_FOUND, "habit"));
    }

    @Override
    public Habit getForUser(Long userId, Long habitId) {
        Habit habit = get(habitId);
        if (!habit.getUserId().equals(userId)) {
            throw HttpException.badRequest().addDetail(ErrorConstants.NOT_AUTHOR, "user");
        }
        return habit;
    }

    @Override
    public List<Habit> getAll() {
        return habitRepository.getAll();
    }

    @Override
    public List<Habit> search(HabitSearchForm form) {
        return habitRepository.search(form);
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
    public void create(Long userId, HabitForm form) {
        habitRepository.save(Habit.builder()
                                         .name(form.getName())
                                         .description(form.getDescription())
                                         .frequency(form.getFrequency())
                                         .created(LocalDate.now())
                                         .userId(userId)
                                         .build());
    }

    @Override
    public void updateForUser(Long userId, Long habitId, HabitForm form) {
        Habit habit = getForUser(userId, habitId);
        habit.setName(form.getName());
        habit.setDescription(form.getDescription());
        habit.setFrequency(form.getFrequency());
        habitRepository.update(habit);
    }

    @Override
    public void deleteForUser(Long userId, Long habitId) {
        Habit habit = getForUser(userId, habitId);
        habitRepository.delete(userId, habit.getId());
    }
}
