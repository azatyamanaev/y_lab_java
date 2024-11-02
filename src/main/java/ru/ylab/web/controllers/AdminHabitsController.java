package ru.ylab.web.controllers;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ylab.aspects.LogRequest;
import ru.ylab.dto.in.HabitSearchForm;
import ru.ylab.dto.mappers.HabitMapper;
import ru.ylab.dto.out.HabitDto;
import ru.ylab.services.entities.HabitService;

import static ru.ylab.utils.constants.WebConstants.ADMIN_URL;
import static ru.ylab.utils.constants.WebConstants.HABITS_URL;
import static ru.ylab.utils.constants.WebConstants.ID_URL;
import static ru.ylab.utils.constants.WebConstants.SEARCH_URL;

/**
 * Controller for handling habits HTTP requests for admin.
 *
 * @author azatyamanaev
 */
@LogRequest
@RequiredArgsConstructor
@RestController
@RequestMapping(ADMIN_URL + HABITS_URL)
public class AdminHabitsController {

    /**
     * Instance of a {@link HabitMapper}.
     */
    private final HabitMapper habitMapper;

    /**
     * Instance of a {@link HabitService}.
     */
    private final HabitService habitService;

    /**
     * Gets habit for admin and writes it to response.
     */
    @GetMapping(ID_URL)
    public ResponseEntity<HabitDto> getHabit(@PathVariable("id") Long id) {
        HabitDto habit = habitMapper.mapToDto(habitService.get(id));
        return ResponseEntity.ok(habit);
    }

    /**
     * Gets habits for admin and writes them to response.
     */
    @GetMapping
    public ResponseEntity<List<HabitDto>> getHabits() {
        List<HabitDto> habits = habitMapper.mapToDto(habitService.getAll());
        return ResponseEntity.ok(habits);
    }

    /**
     * Searches habits for admin and writes them to response.
     */
    @GetMapping(SEARCH_URL)
    public ResponseEntity<List<HabitDto>> searchHabits(HabitSearchForm form) {
        List<HabitDto> habits = habitMapper.mapToDto(habitService.search(form));
        return ResponseEntity.ok(habits);
    }
}
