package ru.ylab.services.entities.impl;

import java.time.LocalDate;
import java.util.List;

import ru.ylab.dto.in.HabitForm;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.exception.HttpException;
import ru.ylab.models.Habit;
import ru.ylab.repositories.HabitRepository;
import ru.ylab.services.entities.HabitService;
import ru.ylab.services.validation.Validator;
import ru.ylab.utils.constants.ErrorConstants;

/**
 * Service implementing {@link HabitService}.
 *
 * @author azatyamanaev
 */
public class HabitServiceImpl implements HabitService {

    /**
     * Instance of a {@link HabitRepository}
     */
    private final HabitRepository habitRepository;

    /**
     * Instance of a {@link Validator<HabitForm>}
     */
    private final Validator<HabitForm> habitFormValidator;

    /**
     * Instance of a {@link Validator<HabitSearchForm>}
     */
    private final Validator<HabitSearchForm> habitSearchFormValidator;

    /**
     * Creates new HabitServiceImpl.
     *
     * @param habitRepository        HabitRepository instance
     * @param habitFormValidator Validator<HabitForm> instance
     * @param habitSearchFormValidator Validator<HabitSearchForm> instance
     */
    public HabitServiceImpl(HabitRepository habitRepository, Validator<HabitForm> habitFormValidator,
                            Validator<HabitSearchForm> habitSearchFormValidator) {
        this.habitRepository = habitRepository;
        this.habitFormValidator = habitFormValidator;
        this.habitSearchFormValidator = habitSearchFormValidator;
    }

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
        habitSearchFormValidator.validate(form);
        return habitRepository.search(form);
    }

    @Override
    public List<Habit> getHabitsForUser(Long userId) {
        return habitRepository.getAllForUser(userId);
    }

    @Override
    public List<Habit> searchHabitsForUser(Long userId, HabitSearchForm form) {
        habitSearchFormValidator.validate(form);
        return habitRepository.searchForUser(userId, form);
    }

    @Override
    public void create(Long userId, HabitForm form) {
        habitFormValidator.validate(form);
        habitRepository.save(Habit.builder()
                                         .name(form.getName())
                                         .description(form.getDescription())
                                         .frequency(Habit.Frequency.valueOf(form.getFrequency()))
                                         .created(LocalDate.now())
                                         .userId(userId)
                                         .build());
    }

    @Override
    public void update(Long userId, Long habitId, HabitForm form) {
        habitFormValidator.validate(form);
        Habit habit = get(habitId);
        if (!habit.getUserId().equals(userId)) {
            throw HttpException.badRequest().addDetail(ErrorConstants.NOT_AUTHOR, "user");
        }
        habit.setName(form.getName());
        habit.setDescription(form.getDescription());
        habit.setFrequency(Habit.Frequency.valueOf(form.getFrequency()));
        habitRepository.update(habit);
    }

    @Override
    public void delete(Long userId, Long habitId) {
        Habit habit = get(habitId);
        if (!habit.getUserId().equals(userId)) {
            throw HttpException.badRequest().addDetail(ErrorConstants.NOT_AUTHOR, "user");
        }
        habitRepository.delete(userId, habit.getId());
    }
}
